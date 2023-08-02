package panzer.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.MinecraftClient;
import panzer.file.files.ClickGuiFile;
import panzer.file.files.ModuleFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    private final ArrayList<Files> files = new ArrayList<Files>();

    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private final File directory = new File(MinecraftClient.getInstance().runDirectory.toString() + "/" + "Panzer");


    public FileManager() {
        makeDirectory();
        registerFiles();
    }

    private void makeDirectory() {
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    private void registerFiles() {
        files.add(new ModuleFile(gson, null));
        files.add(new ClickGuiFile(gson, null));
    }


    public void loadFiles() {

        for (Files file : files) {
            try {
                file.loadFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void saveFiles() {

        for (Files file : files) {
            try {
                file.saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
