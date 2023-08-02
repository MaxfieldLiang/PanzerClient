package panzer.file.files;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import panzer.Client;
import panzer.file.Files;
import panzer.gui.clickgui.TitleButton;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ClickGuiFile extends Files {
    private final File directory = new File(MinecraftClient.getInstance().runDirectory.toString() + "/" + "Panzer" + "/" + "ClickGuis");

    public ClickGuiFile(Gson gson, File file) {
        super(gson, file);
    }

    @Override
    public void loadFile() throws IOException {

        makeDirecotry();


        for (TitleButton titleButton : Client.clickGuiHook.getClickGui().getTitleButtons()) {

            makeTitleButtonFile(titleButton);

            FileReader fr = new FileReader(getFile(titleButton));

            JsonObject jsonObject = getGson().fromJson(fr, JsonObject.class);

            if (jsonObject == null) {
                fr.close();
                return;
            }

            if (jsonObject.has("x")) {
                titleButton.setX(Double.parseDouble(jsonObject.get("x").getAsString()));
            }
            if (jsonObject.has("y")) {
                titleButton.setY(Double.parseDouble(jsonObject.get("y").getAsString()));
            }


            fr.close();


        }


    }

    @Override
    public void saveFile() throws IOException {
        makeDirecotry();


        for (TitleButton titleButton : Client.clickGuiHook.getClickGui().getTitleButtons()) {

            makeTitleButtonFile(titleButton);

            FileWriter fw = new FileWriter(getFile(titleButton));


            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("x", titleButton.getX());
            jsonObject.addProperty("y", titleButton.getY());


            fw.write(getGson().toJson(jsonObject));
            fw.close();

        }


    }

    private void makeDirecotry() {
        if (!directory.exists())
            directory.mkdir();
    }

    private void makeTitleButtonFile(TitleButton titleButton) throws IOException {
        if (!getFile(titleButton).exists())
            getFile(titleButton).createNewFile();
    }


    private File getFile(TitleButton titleButton) {
        return new File(directory, titleButton.getName() + ".jCson");
    }
}
