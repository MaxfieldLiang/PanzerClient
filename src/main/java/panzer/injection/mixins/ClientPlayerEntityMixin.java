package panzer.injection.mixins;

import com.mojang.authlib.GameProfile;
import panzer.Client;
import panzer.event.Event;
import panzer.event.events.EventLivingUpdate;
import panzer.event.events.EventMotionUpdate;
import panzer.event.events.EventUpdate;
import panzer.injection.interfaces.IClientPlayerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity implements IClientPlayerEntity {

    private float cachedRotationPitch;
    private float cachedRotationYaw;
    @Shadow
    private float lastYaw;
    @Shadow
    private float lastPitch;
    @Shadow
    private ClientPlayNetworkHandler networkHandler;
    @Shadow
    @Final
    protected MinecraftClient client;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(at = @At("HEAD"), method = "tickMovement()V")
    public void tickMovement(CallbackInfo callbackInfo) {
        Client.eventManager.onLivingUpdate(new EventLivingUpdate());
    }


    @Inject(at = @At("HEAD"), method = "sendMovementPackets()V")
    private void onSendMovementPacketsHEAD(CallbackInfo callbackInfo) {
        cachedRotationYaw = getYaw();
        cachedRotationPitch = getPitch();

        EventMotionUpdate eventMotionUpdate = new EventMotionUpdate(getYaw(), getPitch());
        eventMotionUpdate.setState(Event.State.PRE);
        Client.eventManager.onMotionUpdate(eventMotionUpdate);

        setYaw(eventMotionUpdate.getYaw());
        setPitch(eventMotionUpdate.getPitch());
    }

    @Inject(at = @At("TAIL"), method = "sendMovementPackets()V")
    private void onSendMovementPacketsTAIL(CallbackInfo callbackInfo) {

        setYaw(cachedRotationYaw);
        setPitch(cachedRotationPitch);

        EventMotionUpdate eventMotionUpdate = new EventMotionUpdate(getYaw(), getPitch());
        eventMotionUpdate.setState(Event.State.POST);
        Client.eventManager.onMotionUpdate(eventMotionUpdate);
    }

    @Inject(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V",
            ordinal = 0), method = "tick()V")
    private void onTick(CallbackInfo ci) {
        Client.eventManager.onUpdate(new EventUpdate());
    }

    @Override
    public boolean isSpectator() {
        return super.isSpectator();
    }

    @Override
    public boolean isTouchingWaterBypass() {
        return super.isTouchingWater();
    }

    @Override
    protected float getJumpVelocity() {
        return super.getJumpVelocity();
    }

    @Override
    protected boolean clipAtLedge() {
        return super.clipAtLedge();
    }

    @Override
    public void setNoClip(boolean noClip) {
        this.noClip = noClip;
    }

    @Override
    public float getLastYaw() {
        return lastYaw;
    }

    @Override
    public float getLastPitch() {
        return lastPitch;
    }

    @Override
    public void setMovementMultiplier(Vec3d movementMultiplier) {
        this.movementMultiplier = movementMultiplier;
    }
}
