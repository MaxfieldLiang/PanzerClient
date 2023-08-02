package panzer.gui.clickgui.settingbuttons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import panzer.Client;
import panzer.gui.clickgui.ModuleButton;
import panzer.gui.clickgui.SettingButton;
import panzer.setting.Setting;
import panzer.utils.RenderUtil;

import java.awt.*;
import java.io.IOException;

public class SlideButton extends SettingButton {
    public boolean drag = false;

    public SlideButton(ModuleButton moduleButton, Setting setting) {
        this.moduleButton = moduleButton;
        this.setting = setting;
    }

    public float anim = 0f;
    public float anim2 = 0f;
    private float lastMouseX;

    @Override
    public void drawScreen(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        //RenderUtil.drawRect(x, y, x + width, y + height, Color.WHITE.getRGB());
        String displayval = "" + Math.round(setting.getValue() * 100D) / 100D;
        boolean hoveredORdragged = isSliderHovered(mouseX, mouseY) || drag;
        if (hoveredORdragged) {
            anim2 = RenderUtil.move(anim2, 0.8f, 0.005f, 0.005f);
        } else {
            anim2 = RenderUtil.move(anim2, 0.4f, 0.005f, 0.005f);
        }
        int color2 = RenderUtil.reAlpha(Color.GREEN.getRGB(), anim2);
        double percentBar = (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());

        drawContext.fill((int) x, (int) y, (int) (x + width), (int) (y + height), Color.WHITE.getRGB());


        RenderUtil.drawString(drawContext, setting.getSettingName(), (float) (x + 2), (float) (y + 2), Color.BLACK.getRGB(), false);
        RenderUtil.drawString(drawContext, displayval, (float) (x + width - MinecraftClient.getInstance().textRenderer.getWidth(displayval)) - 1, (float) (y + 2), Color.BLACK.getRGB(), false);


        drawContext.fill((int) x, (int) (y + 11.5), (int) (x + width), (int) (y + 16), Color.GRAY.getRGB());
        anim = RenderUtil.move(anim, (float) percentBar, 0.005f, 0.005f);
        drawContext.fill((int) x, (int) (y + 11.5), (int) (x + (anim * width)), (int) (y + 16), color2);

        final double valAbs = mouseX - x;
        double perc = valAbs / this.width;
        final double valRel = (setting.getMax() - setting.getMin()) * perc;
        double stupid = setting.getMin() + valRel;

        if (this.drag) {
            this.lastMouseX = ((float) Math.min(Math.max(x, mouseX), x + this.width) - (float) x) / (float) this.width;
            stupid = Math.round(stupid * (1.0 / setting.getStep())) / (1.0 / setting.getStep());
            setting.setValue(stupid);
            if (stupid >= setting.getMax()) {
                setting.setValue(setting.getMax());
            }
            if (stupid <= setting.getMin()) {
                setting.setValue(setting.getMin());
            }
        } else {
            stupid = Math.round(stupid * (1.0 / setting.getStep())) / (1.0 / setting.getStep());
            setting.setValue((double) Math.round((double) setting.getValue() * (1.0D / setting.getStep())) / (1.0D / setting.getStep()));
        }
        super.drawScreen(drawContext, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.drag = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && isSliderHovered(mouseX, mouseY)) {
            this.drag = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isSliderHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y + 11.5 && mouseY <= y + 16;
    }
}
