package panzer.module.movement;

import net.minecraft.util.math.Vec3d;
import panzer.event.events.EventUpdate;
import panzer.module.Module;

public class LiquidWalk extends Module {
    public LiquidWalk() {
        super("LiquidWalk", Category.MOVEMENT);
    }

    @Override
    public void onUpdate(EventUpdate event) {
        if (!mc.options.sneakKey.isPressed()) {
            if (mc.player.isTouchingWater() || mc.player.isInLava()) {
                Vec3d velocity = mc.player.getVelocity();
                mc.player.setVelocity(velocity.x, 0.11, velocity.z);
                return;
            }
        }
        super.onUpdate(event);
    }
}
