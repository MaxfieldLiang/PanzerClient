package panzer.module.movement;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import panzer.event.events.EventUpdate;
import panzer.module.Module;
import panzer.setting.Setting;

public class BoatFly extends Module {
    public Setting customSpeed = new Setting("CustomSpeed", true, this);
    public Setting upwardSpeed = new Setting("UpwardSpeed", 0.3d, 0.0d, 5.0d, 0.1d, this);
    public Setting forwardSpeed = new Setting("ForwardSpeed", 1.0d, 0.0d, 5.0d, 0.1d, this);

    public BoatFly() {
        super("Boatfly", Category.MOVEMENT);
        settingManager.addSetting(customSpeed);
    }

    @Override
    public void onUpdate(EventUpdate event) {
        // check if riding
        if (!mc.player.hasVehicle())
            return;

        Entity vehicle = mc.player.getVehicle();
        Vec3d velocity = vehicle.getVelocity();

        // default motion
        double motionX = velocity.x;
        double motionY = 0;
        double motionZ = velocity.z;

        // up/down
        if (mc.options.jumpKey.isPressed())
            motionY = upwardSpeed.getValue();
        else if (mc.options.sprintKey.isPressed())
            motionY = velocity.y;

        // forward
        if (mc.options.forwardKey.isPressed() && customSpeed.isEnable()) {
            double speed = forwardSpeed.getValue();
            float yawRad = vehicle.getYaw() * MathHelper.RADIANS_PER_DEGREE;

            motionX = MathHelper.sin(-yawRad) * speed;
            motionZ = MathHelper.cos(yawRad) * speed;
        }

        // apply motion
        vehicle.setVelocity(motionX, motionY, motionZ);
        super.onUpdate(event);
    }
}
