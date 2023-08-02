package panzer.module.render;

import panzer.event.events.EventUpdate;
import panzer.injection.interfaces.ISimpleOption;
import panzer.module.Module;

public class FullBright extends Module {
    private double previousValue = 0.0;
    public FullBright() {
        super("FullBright", Category.RENDER);
    }

    @Override
    public void onEnable() {
        this.previousValue = mc.options.getGamma().getValue();
        @SuppressWarnings("unchecked")
        ISimpleOption<Double> gamma =
                (ISimpleOption<Double>)(Object)mc.options.getGamma();
        gamma.forceSetValue(10000.0);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        ISimpleOption<Double> gamma =
                (ISimpleOption<Double>)(Object)mc.options.getGamma();
        gamma.forceSetValue(previousValue);
        super.onDisable();
    }
}
