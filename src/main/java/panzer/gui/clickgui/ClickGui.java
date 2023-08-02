package panzer.gui.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;
import panzer.Client;
import panzer.module.Module;
import panzer.utils.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class ClickGui {
    private final ArrayList<TitleButton> titleButtons = new ArrayList<>();

    private float backGroundAlpha = 0F;

    public float openAnim = 0F;

    public ClickGui() {
        double width = 80;
        double height = 15;
        double titleX = 5;
        double titleY = 5;

        double titleYPlus = height + 10;

        for (Module.Category category : Module.Category.values()) {
            if (category != Module.Category.HIDE) {
                String name = category.name().toUpperCase(Locale.ROOT);
                this.titleButtons.add(new TitleButton(name, titleX, titleY, width, height) {
                    @Override
                    public void init() {
                        for (Module module : Client.moduleManager.getModules()) {
                            if (module.getCategory() == category) {
                                this.getModuleButtons().add(new ModuleButton(module, this));
                            }
                        }
                        super.init();
                    }
                });
                titleY += titleYPlus;
            }
        }
    }
    public void mouseClicked(int mouseX, int mouseY, int button) {
        for (TitleButton titleButton : titleButtons) {
            titleButton.mouseClicked(mouseX, mouseY, button);
        }
    }
    public void mouseReleased(int mouseX, int mouseY, int button) {
        for (TitleButton titleButton : titleButtons) {
            titleButton.mouseReleased((int) mouseX, (int) mouseY, button);
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY,
                       float partialTicks) {
        Window sr = MinecraftClient.getInstance().getWindow();
        context.fill(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), RenderUtil.reAlpha(Color.WHITE.getRGB(), backGroundAlpha));
        context.getMatrices().push();
        context.getMatrices().translate(1, openAnim, 1);
        //context.getMatrices().scale(openAnim, openAnim, openAnim);
        for (TitleButton titleButton : titleButtons) {
            titleButton.drawScreen(context, mouseX, mouseY, partialTicks);
        }
        context.getMatrices().pop();
        if (Client.clickGuiHook.isOpen()) {
            backGroundAlpha = RenderUtil.move(backGroundAlpha, 0.8F, 0.01F, 0.001F);
            openAnim = RenderUtil.move(openAnim, 1F, 0.01F, 0.01F);
        } else {
            backGroundAlpha = RenderUtil.move(backGroundAlpha, 0F, 0.01F, 0.001F);
            openAnim = RenderUtil.move(openAnim, 600, 0.01F, 0.01F);
        }
    }
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        for (TitleButton titleButton : titleButtons) {
            titleButton.keyTyped(' ', keyCode);
        }
    }

    public ArrayList<TitleButton> getTitleButtons() {
        return titleButtons;
    }
}
