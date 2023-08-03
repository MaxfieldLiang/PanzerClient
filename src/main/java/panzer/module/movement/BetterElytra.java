package panzer.module.movement;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.math.Vec3d;
import panzer.event.events.EventUpdate;
import panzer.module.Module;
import panzer.utils.RotationUtil;

public class BetterElytra extends Module {

    public BetterElytra() {
        super("BetterElytra", Category.MOVEMENT);
    }

    @Override
    public void onUpdate(EventUpdate event) {
        ItemStack itemStack = mc.player.getEquippedStack(EquipmentSlot.CHEST);
        if (itemStack.getItem() != Items.ELYTRA) return;
        //Flying
        if (mc.player.isFallFlying()) {
            float yaw = (float) Math.toRadians(mc.player.getYaw());
            Vec3d forwardVec = RotationUtil.getRotationSpeed(yaw, 0.1f);

            Vec3d velocity = mc.player.getVelocity();

            if (mc.options.forwardKey.isPressed()) {
                mc.player.setVelocity(velocity.add(forwardVec));
            }
            if (mc.options.backKey.isPressed()) {
                mc.player.setVelocity(velocity.subtract(forwardVec));
            }

            if (mc.options.jumpKey.isPressed()) {
                mc.player.setVelocity(velocity.x, velocity.y + 0.1, velocity.z);
            }
            if (mc.options.sneakKey.isPressed()) {
                mc.player.setVelocity(velocity.x, velocity.y - 0.05, velocity.z);
            }
            return;
        }
        //Start Flying
        if (ElytraItem.isUsable(itemStack) && mc.options.jumpKey.isPressed()) {
            mc.player.setJumping(false);
            mc.player.setSprinting(true);
            mc.player.jump();
            ClientCommandC2SPacket packet = new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING);
            mc.player.networkHandler.sendPacket(packet);
        }
        super.onUpdate(event);
    }
}
