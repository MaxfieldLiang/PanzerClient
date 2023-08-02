package panzer.module.render;

import panzer.event.events.EventUpdate;
import panzer.module.Module;

public class FullBright extends Module {

    private boolean wasGammaChanged;
    private float nightVisionStrength;
    public FullBright() {
        super("FullBright", Category.RENDER);
    }

    @Override
    public void onUpdate(EventUpdate event) {
        mc.options.getGamma().setValue(16.0);
        super.onUpdate(event);
    }

    @Override
    public void onDisable() {
        mc.options.getGamma().setValue(1.0);
        super.onDisable();
    }
}
