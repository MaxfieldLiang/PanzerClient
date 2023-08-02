package panzer.module.combat;

import panzer.event.events.EventUpdate;
import panzer.module.Module;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", Category.COMBAT);
    }

    @Override
    public void onUpdate(EventUpdate event) {
        setInfo("Packet");
        super.onUpdate(event);
    }

}
