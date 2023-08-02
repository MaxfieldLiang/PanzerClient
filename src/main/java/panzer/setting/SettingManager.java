package panzer.setting;

import panzer.module.Module;

import java.util.ArrayList;

public class SettingManager {
    private final ArrayList<Setting> settings = new ArrayList<>();

    public void addSetting(Setting setting) {
        this.settings.add(setting);
    }

    public ArrayList<Setting> getSettingByModule(Module module) {
        ArrayList<Setting> rsettings = new ArrayList<>();
        for (Setting setting : settings) {
            if (setting.getModule().equals(module)) {
                rsettings.add(setting);
            }
        }
        if (rsettings.isEmpty()) {
            return null;
        }

        return rsettings;
    }

    public Setting getSettingByName(String name, Module module) {
        ArrayList<Setting> rsettings = new ArrayList<>();
        for (Setting setting : settings) {
            if (setting.getModule().equals(module) && setting.getSettingName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }
}
