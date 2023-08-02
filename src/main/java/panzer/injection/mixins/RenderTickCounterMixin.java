package panzer.injection.mixins;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.RenderTickCounter;
import panzer.Client;
import panzer.module.world.Timer;

@Mixin(RenderTickCounter.class)
public abstract class RenderTickCounterMixin
{
    @Shadow
    private float lastFrameDuration;

    @Inject(at = {@At(value = "FIELD",
            target = "Lnet/minecraft/client/render/RenderTickCounter;prevTimeMillis:J",
            opcode = Opcodes.PUTFIELD,
            ordinal = 0)}, method = {"beginRenderTick(J)I"})
    public void onBeginRenderTick(long long_1,
                                  CallbackInfoReturnable<Integer> cir)
    {
        Timer timer = (Timer) Client.moduleManager.getModule(Timer.class);
        if (timer.isToggle()) {
            lastFrameDuration *= timer.speed.getValue();
        }
    }
}
