package panzer.gui.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import panzer.Client;
import panzer.gui.clickgui.settingbuttons.ClickButton;
import panzer.gui.clickgui.settingbuttons.KeyBindButton;
import panzer.gui.clickgui.settingbuttons.ModeButton;
import panzer.gui.clickgui.settingbuttons.SlideButton;
import panzer.module.Module;
import panzer.setting.Setting;
import panzer.utils.RenderUtil;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ModuleButton {
    private Module module;
    private final ArrayList<SettingButton> settingButtons = new ArrayList<>();
    private TitleButton titleButton;
    private double x, y, width, height;
    private boolean extend = false;


    public ModuleButton(Module module, TitleButton titleButton) {
        this.module = module;
        this.titleButton = titleButton;
        height = 15;
        width = 86;

        ArrayList<Setting> settings = Client.settingManager.getSettingByModule(module);
        if (settings != null) {
            for (Setting setting : settings) {
                if (setting.isBoolean()) {
                    settingButtons.add(new ClickButton(this, setting));
                } else if (setting.isValue()) {
                    settingButtons.add(new SlideButton(this, setting));
                } else if (setting.isMode()) {
                    settingButtons.add(new ModeButton(this, setting));
                }
            }
        }
        if (module.getCategory() != Module.Category.SETTING) {
            settingButtons.add(new KeyBindButton(this));
        }
    }

    public float animToggle = 0f;

    public void drawScreen(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        drawContext.fill((int) x, (int) y, (int) (x + width), (int) (y + height), Color.WHITE.getRGB());
        RenderUtil.drawString(drawContext, module.getName(), (float) x + 2, (float) (y + height / 2 - 3), Color.BLACK.getRGB(), false);
        drawContext.fill((int) (x + width - 12), (int) (y + 3), (int) (x + width - 2), (int) (y + 8 + 5), Color.BLACK.getRGB());
//        RenderUtil.drawFilledCircle((float) (x + width - 7), (float) (y + 8), 5, Color.BLACK.getRGB());
        if (module.isToggle()) {
            animToggle = RenderUtil.move(animToggle, 1f, 0.01F, 0.01F);
        } else {
            animToggle = RenderUtil.move(animToggle, 0f, 0.01F, 0.01F);
        }
        drawContext.fill((int) (x + width - 12), (int) (y + 3), (int) (x + width - 2), (int) (y + 8 + 5), RenderUtil.reAlpha(Color.GREEN.getRGB(), animToggle));
//        RenderUtil.drawFilledCircle((float) (x + width - 7), (float) (y + 8), animToggle, Color.GREEN.getRGB());

//        RenderUtil.drawRect((x + width - 12), (float) (y + 3), (x + width - 7) + 5, (float) (y + 8) + 5, Color.GRAY.getRGB());

        if (isExtend()) {
            float off = 0;
            for (SettingButton settingButton : settingButtons) {
                settingButton.offset = RenderUtil.move(settingButton.offset, off, 0.02F, 0.02F);
                settingButton.x = settingButton.moduleButton.getX() + settingButton.moduleButton.getWidth() + 2;
                settingButton.y = settingButton.moduleButton.y + settingButton.offset;
                settingButton.width = settingButton.moduleButton.width + 12;
                settingButton.height = 15;
                settingButton.drawScreen(drawContext, mouseX, mouseY, partialTicks);
                off += settingButton.height;
            }
        }
    }

    public boolean isHoveredToggleButton(int mouseX, int mouseY) {
        return mouseX >= (x + width - 12) && mouseX <= (x + width - 7) + 5 && mouseY >= (float) (y + 3) && mouseY <= (float) (y + 8) + 5;
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHoveredToggleButton(mouseX, mouseY) && mouseButton == 0) {
            module.setToggle(!module.isToggle());
            MinecraftClient.getInstance().player.playSound(SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS.ordinal(), MinecraftClient.getInstance().player.getPitch());
        } else if (isHoveredToggleButton(mouseX, mouseY) && mouseButton == 1) {
            this.setExtend(!this.isExtend());
            if (!isExtend()) {
                for (SettingButton settingButton : settingButtons) {
                    settingButton.offset = 0;
                }
            }
        } else if (isExtend()) {
            for (SettingButton settingButton : settingButtons) {
                settingButton.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (isExtend()) {
            for (SettingButton settingButton : settingButtons) {
                settingButton.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    protected void keyTyped(char typedChar, int keyCode) {
        for (SettingButton settingButton : settingButtons) {
            settingButton.keyTyped(typedChar, keyCode);
        }
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public ArrayList<SettingButton> getSettingButtons() {
        return settingButtons;
    }

    public TitleButton getTitleButton() {
        return titleButton;
    }

    public void setTitleButton(TitleButton titleButton) {
        this.titleButton = titleButton;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isExtend() {
        return extend;
    }

    public void setExtend(boolean extend) {
        this.extend = extend;
    }
}
