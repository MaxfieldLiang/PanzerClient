package panzer.injection.mixins;

import panzer.Client;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(at = @At("HEAD"), method = "onKey(JIIII)V")
    private void onOnKey(long windowHandle, int keyCode, int scanCode,
                         int action, int modifiers, CallbackInfo ci) {
        Client.eventManager.onKeyHook(keyCode, action);
    }
}
