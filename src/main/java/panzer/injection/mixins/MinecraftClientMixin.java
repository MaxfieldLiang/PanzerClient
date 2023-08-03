package panzer.injection.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import panzer.Client;
import panzer.injection.interfaces.IClientPlayerEntity;
import panzer.injection.interfaces.IMinecraftClient;

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

    }
    @Override
    public IClientPlayerEntity getPlayer()
    {
        return (IClientPlayerEntity)player;
    }

}
