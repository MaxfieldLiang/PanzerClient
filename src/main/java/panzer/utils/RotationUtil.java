package panzer.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class RotationUtil {
    public static float[] getRotations(Vec3d target) {
        Vec3d eyePos = MinecraftClient.getInstance().player.getEyePos();
        double differentX = target.getX() - eyePos.getX();
        double differentY = target.getY() - eyePos.getY();
        double differentZ = target.getZ() - eyePos.getZ();

        double hypotenuse = Math.sqrt(differentX * differentX + differentZ * differentZ);
        float yaw = (float) Math.toDegrees(Math.atan2(differentZ, differentX)) - 90F;
        float pitch = (float) -Math.toDegrees(Math.atan2(differentY, hypotenuse));
        //simple math
        return new float[]{yaw, pitch};
    }
}
