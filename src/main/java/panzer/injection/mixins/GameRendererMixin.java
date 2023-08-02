package panzer.injection.mixins;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import panzer.Client;
import panzer.event.events.EventRender;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
                    opcode = Opcodes.GETFIELD,
                    ordinal = 0),
            method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V")
    private void onRenderWorld(float partialTicks, long finishTimeNano,
                               MatrixStack matrixStack, CallbackInfo ci)
    {
        Client.eventManager.onRender(new EventRender(partialTicks, matrixStack));
    }
}
