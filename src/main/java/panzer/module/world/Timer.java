package panzer.module.world;

import panzer.module.Module;
import panzer.setting.Setting;

public class Timer extends Module {
    public Setting speed = new Setting("Speed", 2.0d, 0.1d, 20.0d, 0.1d, this);
    public Timer() {
        super("Timer", Category.WORLD);
        settingManager.addSetting(speed);
    }
}
