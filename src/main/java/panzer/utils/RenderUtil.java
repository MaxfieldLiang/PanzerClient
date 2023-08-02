package panzer.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.ColorHelper;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderUtil {
    public static void drawString(DrawContext context, String renderS, double x, double y, int color, boolean shadow) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        context.drawText(textRenderer, renderS, (int) x, (int) y, color, shadow);
    }

    public static int getStringWidth(String renderS) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        return textRenderer.getWidth(renderS);
    }

    public static int reAlpha(int color, float alpha) {
        Color nColor = new Color(color);
        float red = 0.003921569F * (float) nColor.getRed();
        float green = 0.003921569F * (float) nColor.getGreen();
        float blue = 0.003921569F * (float) nColor.getBlue();
        return (new Color(red, green, blue, alpha)).getRGB();
    }

    public static float move(float current, float end, float smoothSpeed, float minSpeed) {
        if (current != end) {
            float movement = (end - current) * smoothSpeed;

            if (movement > 0) {
                movement = Math.max(minSpeed, movement);
                movement = Math.min(end - current, movement);
            } else if (movement < 0) {
                movement = Math.min(-minSpeed, movement);
                movement = Math.max(end - current, movement);
            }
            return current + movement;
        } else {
            return end;
        }
    }

}
