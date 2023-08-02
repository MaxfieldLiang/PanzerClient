package panzer.gui.clickgui;

import net.minecraft.client.gui.DrawContext;
import panzer.setting.Setting;

import java.io.IOException;

public class SettingButton {
    public ModuleButton moduleButton;
    public Setting setting;

    public double x, y, width, height;
    public float offset;

    public void drawScreen(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {

    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {

    }

    protected void keyTyped(char typedChar, int keyCode) {

    }

    public boolean isHovered(int mouseX, int mouseY) {

        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
