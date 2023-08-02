package panzer.module.movement;

import net.minecraft.util.math.Vec3d;
import panzer.event.events.EventUpdate;
import panzer.injection.interfaces.IMinecraftClient;
import panzer.module.Module;

public class AntiWeb extends Module {
    public AntiWeb() {
        super("AntiWeb", Category.MOVEMENT);
    }

    @Override
    public void onUpdate(EventUpdate event) {
        IMinecraftClient iMinecraftClient = (IMinecraftClient) mc;
        iMinecraftClient.getPlayer().setMovementMultiplier(Vec3d.ZERO);
        super.onUpdate(event);
    }
}
