package panzer.module.combat;

import panzer.module.Module;
import panzer.setting.Setting;

public class KillAura extends Module {
    public Setting range;
    public Setting fov;
    public Setting player;
    public Setting mob;
    public KillAura() {
        super("KillAura", Category.COMBAT);
    }
}
