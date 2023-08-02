package panzer.injection.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import panzer.Client;
import panzer.module.render.Nametag;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    @Shadow
    @Final
    protected EntityRenderDispatcher dispatcher;

    @Inject(at = @At("HEAD"),
            method = "renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            cancellable = true)
    private void onRenderLabelIfPresent(T entity, Text text,
                                        MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider,
                                        int i, CallbackInfo ci) {
        // add HealthTags info
        if(entity instanceof LivingEntity)
            text = addHealth((LivingEntity)entity, text);

        // do NameTags adjustments
        wurstRenderLabelIfPresent(entity, text, matrixStack,
                vertexConsumerProvider, i);
        ci.cancel();
    }

    protected void wurstRenderLabelIfPresent(T entity, Text text,
                                             MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light)
    {

        // disable distance limit if configured in NameTags
        double distanceSq = dispatcher.getSquaredDistanceToCamera(entity);
//        if(distanceSq > 4096 && !nameTags.isUnlimitedRange())
//            return;

        // disable sneaking changes if NameTags is enabled
        boolean notSneaky = !entity.isSneaky() || Client.moduleManager.getModule(Nametag.class).isToggle();

        float matrixY = entity.getHeight() + 0.5F;
        int labelY = "deadmau5".equals(text.getString()) ? -10 : 0;

        matrices.push();
        matrices.translate(0, matrixY, 0);
        matrices.multiply(dispatcher.getRotation());

        // adjust scale if NameTags is enabled
        float scale = 0.025F;
        if(Client.moduleManager.getModule(Nametag.class).isToggle())
        {
            double distance = MinecraftClient.getInstance().player.distanceTo(entity);
            if(distance > 10)
                scale *= distance / 10;
        }
        matrices.scale(-scale, -scale, scale);

        Matrix4f matrix = matrices.peek().getPositionMatrix();
        float bgOpacity =
                MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
        int bgColor = (int)(bgOpacity * 255F) << 24;
        TextRenderer tr = getTextRenderer();
        float labelX = -tr.getWidth(text) / 2;

        // draw background
        tr.draw(text, labelX, labelY, 0x20FFFFFF, false, matrix,
                vertexConsumers,
                notSneaky ? TextLayerType.SEE_THROUGH : TextLayerType.NORMAL,
                bgColor, light);

        // use the see-through layer for text if configured in NameTags
        TextLayerType textLayer = TextLayerType.SEE_THROUGH;

        // draw text
        if(notSneaky)
            tr.draw(text, labelX, labelY, 0xFFFFFFFF, false, matrix,
                    vertexConsumers, textLayer, 0, light);

        matrices.pop();
    }

    @Shadow
    public TextRenderer getTextRenderer()
    {
        return null;
    }

    public Text addHealth(LivingEntity entity, Text nametag)
    {
        int health = (int)entity.getHealth();

        MutableText formattedHealth = Text.literal(" ")
                .append(Integer.toString(health)).formatted(getColor(health));
        return ((MutableText)nametag).append(formattedHealth);
    }

    private Formatting getColor(int health)
    {
        if(health <= 5)
            return Formatting.DARK_RED;

        if(health <= 10)
            return Formatting.GOLD;

        if(health <= 15)
            return Formatting.YELLOW;

        return Formatting.GREEN;
    }
}
