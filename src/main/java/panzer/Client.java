package panzer;

import panzer.event.EventManager;
import panzer.file.FileManager;
import panzer.gui.clickgui.ClickGui;
import panzer.gui.clickgui.ClickGuiHook;
import panzer.module.ModuleManager;
import panzer.setting.SettingManager;

public class Client {
    private static final String version = "1.0.0";
    public static final EventManager eventManager = new EventManager();
    public static final ModuleManager moduleManager = new ModuleManager();
    public static final SettingManager settingManager = new SettingManager();
    public static ClickGuiHook clickGuiHook;
    public static final FileManager fileManager = new FileManager();
    public static void init() {
        moduleManager.init();
        ClickGui clickGui = new ClickGui();
        clickGuiHook = new ClickGuiHook(clickGui);

        fileManager.loadFiles();
    }

    public static void shutdown() {
        fileManager.saveFiles();
    }
}
