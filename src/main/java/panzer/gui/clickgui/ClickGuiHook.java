package panzer.gui.clickgui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiHook extends Screen {
    private boolean isOpen = false;
    private final ClickGui clickGui;
    public ClickGuiHook(ClickGui clickGui) {
        super(Text.literal(""));
        this.clickGui = clickGui;
    }

    public ClickGui getClickGui() {
        return clickGui;
    }

    @Override
    protected void init() {
        setOpen(true);
        super.init();
    }

    @Override
    public void close() {
        setOpen(false);
        super.close();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        clickGui.mouseClicked((int) mouseX, (int) mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        clickGui.mouseReleased((int) mouseX, (int) mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        clickGui.render(context,mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        clickGui.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
