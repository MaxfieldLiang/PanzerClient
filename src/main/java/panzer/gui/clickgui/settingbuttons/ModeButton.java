package panzer.gui.clickgui.settingbuttons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import panzer.Client;
import panzer.gui.clickgui.ModuleButton;
import panzer.gui.clickgui.SettingButton;
import panzer.setting.Setting;
import panzer.utils.RenderUtil;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ModeButton extends SettingButton {
    public ModeButton(ModuleButton moduleButton, Setting setting) {
        this.moduleButton = moduleButton;
        this.setting = setting;
    }

    @Override
    public void drawScreen(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        drawContext.fill((int) x, (int) y, (int) (x + width), (int) (y + height), Color.WHITE.getRGB());
        RenderUtil.drawString(drawContext, setting.getSettingName(), (float) x + 2, (float) (y + height / 2 - 4), Color.BLACK.getRGB(), false);
        RenderUtil.drawString(drawContext, setting.getMode(), (float) (x + width - MinecraftClient.getInstance().textRenderer.getWidth(setting.getMode()))
                , (float) (y + height / 2 - 4) , Color.BLACK.getRGB(), false);
        super.drawScreen(drawContext, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            ArrayList<String> modes = setting.getModes();
            int allIndex = modes.size() - 1;
            int currentIndex = modes.indexOf(setting.getMode());
            if (currentIndex >= allIndex) {
                setting.setMode(modes.get(0));
            } else if (currentIndex < allIndex) {
                setting.setMode(modes.get(currentIndex + 1));
            }
            MinecraftClient.getInstance().player.playSound(SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS.ordinal(), MinecraftClient.getInstance().player.getPitch());
        } else if (isHovered(mouseX, mouseY) && mouseButton == 1) {
            ArrayList<String> modes = setting.getModes();
            int allIndex = modes.size() - 1;
            int currentIndex = modes.indexOf(setting.getMode());
            if (currentIndex <= 0) {
                setting.setMode(modes.get(allIndex));
            } else if (currentIndex <= allIndex) {
                setting.setMode(modes.get(currentIndex - 1));
            }
            MinecraftClient.getInstance().player.playSound(SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS.ordinal(), MinecraftClient.getInstance().player.getPitch());
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
