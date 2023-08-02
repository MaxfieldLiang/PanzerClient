package panzer.gui.clickgui.settingbuttons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.lwjgl.glfw.GLFW;

import panzer.Client;
import panzer.gui.clickgui.ModuleButton;
import panzer.gui.clickgui.SettingButton;
import panzer.utils.RenderUtil;

import java.awt.*;
import java.io.IOException;

public class KeyBindButton extends SettingButton {
    public boolean listener = false;

    public KeyBindButton(ModuleButton moduleButton) {
        this.moduleButton = moduleButton;
    }

    @Override
    public void drawScreen(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        drawContext.fill((int) x, (int) y, (int) (x + width), (int) (y + height), Color.WHITE.getRGB());
        String text =InputUtil.Type.KEYSYM.createFromCode(moduleButton.getModule().getKey()).getLocalizedText().getString();
        if (listener) {
            text = "PRESS";
        }

        RenderUtil.drawString(drawContext, "KEY:" + text, (float) x + 2, (float) (y + height / 2 - 4), Color.BLACK.getRGB(), false);

//        RenderUtil.drawRect((x + width - 12), (float) (y + 3), (x + width - 7) + 5, (float) (y + 8) + 5, Color.GRAY.getRGB());
        super.drawScreen(drawContext, mouseX, mouseY, partialTicks);
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            listener = true;
            MinecraftClient.getInstance().player.playSound(SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS.ordinal(), MinecraftClient.getInstance().player.getPitch());
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (listener) {
            listener = false;
            if (keyCode == GLFW.GLFW_KEY_SPACE) {
                moduleButton.getModule().setKey(-1);
            } else {
                moduleButton.getModule().setKey(keyCode);
            }
        }
        super.keyTyped(typedChar, keyCode);
    }
}
