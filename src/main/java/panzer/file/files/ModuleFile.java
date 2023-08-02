package panzer.file.files;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import panzer.Client;
import panzer.file.Files;
import panzer.module.Module;
import panzer.setting.Setting;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ModuleFile extends Files {
    private final File directory = new File(MinecraftClient.getInstance().runDirectory.toString() + "/" + "Panzer" + "/" + "Modules");

    public ModuleFile(Gson gson, File file) {
        super(gson, file);
    }

    @Override
    public void loadFile() throws IOException {

        makeDirecotry();


        for (Module module : Client.moduleManager.getModules()) {

            makeModuleFile(module);

            FileReader fr = new FileReader(getFile(module));

            JsonObject jsonObject = getGson().fromJson(fr, JsonObject.class);

            if (jsonObject == null) {
                fr.close();
                return;
            }


            if (jsonObject.has("toggled")) {
                if (Boolean.parseBoolean(jsonObject.get("toggled").getAsString())) {
                    module.setToggle(true);
                }
            }

            if (jsonObject.has("key")) {
                module.setKey(Integer.parseInt(jsonObject.get("key").getAsString()));
            }

            ArrayList<Setting> settings = Client.settingManager.getSettingByModule(module);

            if (settings != null && jsonObject.has("settings")) {

                JsonArray jsonArray = (JsonArray) jsonObject.get("settings");

                jsonArray.forEach(jsonElement -> settings.stream().filter(setting -> jsonElement.getAsJsonObject().has(setting.getSettingName()))
                        .forEach(setting -> {
                            if (setting.isBoolean()) {
                                setting.setEnable(jsonElement.getAsJsonObject().get(setting.getSettingName()).getAsBoolean());
                            } else if (setting.isValue()) {
                                setting.setValue(jsonElement.getAsJsonObject().get(setting.getSettingName()).getAsDouble());
                            } else {
                                setting.setMode(jsonElement.getAsJsonObject().get(setting.getSettingName()).getAsString());
                            }

                        }));
            }


            fr.close();


        }


    }

    @Override
    public void saveFile() throws IOException {
        makeDirecotry();


        for (Module module : Client.moduleManager.getModules()) {

            makeModuleFile(module);

            FileWriter fw = new FileWriter(getFile(module));


            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("toggled", module.isToggle());
            jsonObject.addProperty("key", module.getKey());


            ArrayList<Setting> settings = Client.settingManager.getSettingByModule(module);

            if (settings != null) {

                JsonArray jsonArray = new JsonArray();
                JsonObject jsonObject1 = new JsonObject();


                settings.forEach(setting -> {
                    if (setting.isBoolean()) {
                        jsonObject1.addProperty(setting.getSettingName(), setting.isEnable());
                    } else if (setting.isValue()) {
                        jsonObject1.addProperty(setting.getSettingName(), setting.getValue());
                    } else {
                        jsonObject1.addProperty(setting.getSettingName(), setting.getMode());
                    }
                });

                jsonArray.add(jsonObject1);
                jsonObject.add("settings", jsonArray);

            }

            fw.write(getGson().toJson(jsonObject));
            fw.close();

        }


    }

    private void makeDirecotry() {
        if (!directory.exists())
            directory.mkdir();
    }

    private void makeModuleFile(Module module) throws IOException {
        if (!getFile(module).exists())
            getFile(module).createNewFile();
    }


    private File getFile(Module module) {
        return new File(directory, module.getName() + ".json");
    }
}