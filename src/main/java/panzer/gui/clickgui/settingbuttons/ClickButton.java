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

public class ClickButton extends SettingButton {

    public ClickButton(ModuleButton moduleButton, Setting setting) {
        this.moduleButton = moduleButton;
        this.setting = setting;
    }

    public float animToggle = 0f;

    @Override
    public void drawScreen(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        drawContext.fill((int) x, (int) y, (int) (x + width), (int) (y + height), Color.WHITE.getRGB());
        RenderUtil.drawString(drawContext, setting.getSettingName(), (float) x + 2, (float) (y + height / 2 - 4), Color.BLACK.getRGB(), false);

//        RenderUtil.drawFilledCircle((float) (x + width - 7), (float) (y + 8), 5, Color.BLACK.getRGB());
        drawContext.fill((int) (x + width - 12), (int) (y + 3), (int) (x + width - 2), (int) (y + 8 + 5), Color.BLACK.getRGB());
        if (setting.isEnable()) {
            animToggle = RenderUtil.move(animToggle, 1F, 0.01F, 0.01F);
        } else {
            animToggle = RenderUtil.move(animToggle, 0F, 0.01F, 0.01F);
        }
//        RenderUtil.drawFilledCircle((float) (x + width - 7), (float) (y + 8), animToggle, Color.GREEN.getRGB());

        drawContext.fill((int) (x + width - 12), (int) (y + 3), (int) (x + width - 2), (int) (y + 8 + 5), RenderUtil.reAlpha(Color.GREEN.getRGB(), animToggle));
        super.drawScreen(drawContext, mouseX, mouseY, partialTicks);
    }

    public boolean isHoveredToggleButton(int mouseX, int mouseY) {
        return mouseX >= (x + width - 12) && mouseX <= (x + width - 7) + 5 && mouseY >= (float) (y + 3) && mouseY <= (float) (y + 8) + 5;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHoveredToggleButton(mouseX, mouseY) && mouseButton == 0) {
            setting.setEnable(!setting.isEnable());
            MinecraftClient.getInstance().player.playSound(SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS.ordinal(), MinecraftClient.getInstance().player.getPitch());
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
