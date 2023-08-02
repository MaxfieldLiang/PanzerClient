package panzer.file;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
public abstract class Files {
    private Gson gson;
    private File file;

    public Files(Gson gson, File file) {
        this.gson = gson;
        this.file = file;
        makeDirectory();
    }

    private void makeDirectory() {
        if (file != null && !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
    }

    public abstract void loadFile() throws IOException;

    public abstract void saveFile() throws IOException;

    public File getFile() {
        return file;
    }

    public Gson getGson() {
        return gson;
    }
}
