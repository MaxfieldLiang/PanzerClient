package panzer.module.movement;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import panzer.event.events.EventUpdate;
import panzer.module.Module;

public class BetterElytra extends Module {
    private int jumpTimer;

    public BetterElytra() {
        super("BetterElytra", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        jumpTimer = 0;
        super.onEnable();
    }

    @Override
    public void onUpdate(EventUpdate event) {
        if (jumpTimer > 0)
            jumpTimer--;

        ItemStack chest = mc.player.getEquippedStack(EquipmentSlot.CHEST);
        if (chest.getItem() != Items.ELYTRA)
            return;

        if (mc.player.isFallFlying()) {
            controlSpeed();
            controlHeight();
            return;
        }

        if (ElytraItem.isUsable(chest) && mc.options.jumpKey.isPressed())
            doInstantFly();
        super.onUpdate(event);
    }

    private void controlHeight() {
        Vec3d v = mc.player.getVelocity();

        if (mc.options.jumpKey.isPressed())
            mc.player.setVelocity(v.x, v.y + 0.08, v.z);
        else if (mc.options.sneakKey.isPressed())
            mc.player.setVelocity(v.x, v.y - 0.04, v.z);
    }

    private void controlSpeed() {
        float yaw = (float) Math.toRadians(mc.player.getYaw());
        Vec3d forward = new Vec3d(-MathHelper.sin(yaw) * 0.05, 0,
                MathHelper.cos(yaw) * 0.05);

        Vec3d v = mc.player.getVelocity();

        if (mc.options.forwardKey.isPressed())
            mc.player.setVelocity(v.add(forward));
        else if (mc.options.backKey.isPressed())
            mc.player.setVelocity(v.subtract(forward));
    }

    private void doInstantFly() {
        if (jumpTimer <= 0) {
            jumpTimer = 20;
            mc.player.setJumping(false);
            mc.player.setSprinting(true);
            mc.player.jump();
        }

        sendStartStopPacket();
    }

    private void sendStartStopPacket() {
        ClientCommandC2SPacket packet = new ClientCommandC2SPacket(mc.player,
                ClientCommandC2SPacket.Mode.START_FALL_FLYING);
        mc.player.networkHandler.sendPacket(packet);
    }
}
