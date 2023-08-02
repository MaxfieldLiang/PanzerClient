package panzer.module.combat;

import panzer.event.events.EventKnowBack;
import panzer.module.Module;
import panzer.setting.Setting;

public class AntiKnowback extends Module {
    public Setting vH = new Setting("Vertical", 1.0d, 0.0d, 1.0d, 0.1d, this);
    public Setting hH = new Setting("Horizontal", 1.0d, 0.0d, 1.0d, 0.1d, this);
    public AntiKnowback() {
        super("Antiknowback", Category.COMBAT);
        settingManager.addSetting(hH);
        settingManager.addSetting(vH);
    }

    @Override
    public void onKnowBack(EventKnowBack event) {
        double verticalMultiplier = 1 - vH.getValue();
        double horizontalMultiplier = 1 - hH.getValue();

        event.setX(event.getDefaultX() * horizontalMultiplier);
        event.setY(event.getDefaultY() * verticalMultiplier);
        event.setZ(event.getDefaultZ() * horizontalMultiplier);
        super.onKnowBack(event);
    }
}
