package panzer.injection.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import panzer.Client;
import panzer.injection.interfaces.IClientPlayerEntity;
import panzer.injection.interfaces.IMinecraftClient;
import panzer.module.combat.Criticals;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin implements IMinecraftClient {
    @Shadow
    private ClientPlayerEntity player;

    @Inject(at = @At("HEAD"), method = "close()V")
    public void close(CallbackInfo callbackInfo) {
        Client.shutdown();
    }
    @Inject(at = {@At(value = "FIELD",
            target = "Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;",
            ordinal = 0)}, method = {"doAttack()Z"}, cancellable = true)
    private void onDoAttack(CallbackInfoReturnable<Boolean> cir)
    {
        if (Client.moduleManager.getModule(Criticals.class).isToggle()) {
            if(MinecraftClient.getInstance().crosshairTarget == null
                    || MinecraftClient.getInstance().crosshairTarget.getType() != HitResult.Type.ENTITY
                    || !(((EntityHitResult)MinecraftClient.getInstance().crosshairTarget)
                    .getEntity() instanceof LivingEntity))
                return;
            doPacketJump();

        }
    }
    @Override
    public IClientPlayerEntity getPlayer()
    {
        return (IClientPlayerEntity)player;
    }

    private void doPacketJump()
    {
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();

        sendPos(posX, posY + 0.0625D, posZ, true);
        sendPos(posX, posY, posZ, false);
        sendPos(posX, posY + 1.1E-5D, posZ, false);
        sendPos(posX, posY, posZ, false);
    }

    private void sendPos(double x, double y, double z, boolean onGround)
    {
        player.networkHandler.sendPacket(
                new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, onGround));
    }
}
