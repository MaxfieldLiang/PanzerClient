package panzer.module.movement;

import net.minecraft.util.math.Vec3d;
import panzer.event.events.EventUpdate;
import panzer.module.Module;

public class FastLadder extends Module {
    public FastLadder() {
        super("FastLadder", Category.MOVEMENT);
    }

    @Override
    public void onUpdate(EventUpdate event) {
        if(!mc.player.isClimbing() || !mc.player.horizontalCollision)
            return;

        if(mc.player.input.movementForward == 0
                && mc.player.input.movementSideways == 0)
            return;

        Vec3d velocity = mc.player.getVelocity();
        mc.player.setVelocity(velocity.x, 0.2872, velocity.z);

        super.onUpdate(event);
    }
}
