package panzer.module.movement;

import net.minecraft.util.math.Vec3d;
import panzer.Client;
import panzer.event.events.EventOffGroundSpeed;
import panzer.event.events.EventUpdate;
import panzer.module.Module;
import panzer.setting.Setting;

import java.util.ArrayList;

public class Flight extends Module {
    public Setting mode;
    public Setting horizontalSpeed = new Setting("SpeedXZ", 1.0d, 1.0d, 10.0d, 1.0d, this);
    public Setting verticalSpeed = new Setting("SpeedY", 1.0d, 1.0d, 5.0d, 1.0d, this);
    public Flight() {
        super("Flight", Category.MOVEMENT);
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Creative");
        modes.add("Custom");
        settingManager.addSetting(mode = new Setting("Mode", "Creative", modes, this));
        settingManager.addSetting(horizontalSpeed);
        settingManager.addSetting(verticalSpeed);
    }

    @Override
    public void onUpdate(EventUpdate event) {
        if (mode.getMode().equalsIgnoreCase("Creative")) {
            mc.player.getAbilities().allowFlying = true;
        }
        if (mode.getMode().equalsIgnoreCase("Custom")) {
            mc.player.getAbilities().flying = false;

            mc.player.setVelocity(0, 0, 0);
            Vec3d velocity = mc.player.getVelocity();

            if(mc.options.jumpKey.isPressed())
                mc.player.setVelocity(velocity.x, verticalSpeed.getValue(),
                        velocity.z);

            if(mc.options.sneakKey.isPressed())
                mc.player.setVelocity(velocity.x, -verticalSpeed.getValue(),
                        velocity.z);


        }
        setInfo(mode.getMode());
        super.onUpdate(event);
    }

    @Override
    public void onOffGroundSpeed(EventOffGroundSpeed event) {

        super.onOffGroundSpeed(event);
    }
}
