package panzer.module.movement;

import org.lwjgl.glfw.GLFW;
import panzer.event.events.EventLivingUpdate;
import panzer.module.Module;

public class AutoSprint extends Module {
    public AutoSprint() {
        super("AutoSprint", Category.MOVEMENT);
        setKey(GLFW.GLFW_KEY_Z);
    }

    @Override
    public void onLivingUpdate(EventLivingUpdate event) {
        if (mc.player.input.movementForward > 0) {
            mc.player.setSprinting(true);
        } else {
            mc.player.setSprinting(false);
        }
        super.onLivingUpdate(event);
    }

    @Override
    public void onDisable() {
        if (mc.player.isSprinting()) {
            mc.player.setSprinting(false);
        }
        super.onDisable();
    }
}
