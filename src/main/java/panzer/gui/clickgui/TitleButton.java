package panzer.gui.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import panzer.utils.RenderUtil;

import java.awt.*;
import java.util.ArrayList;

public class TitleButton {
    private String name;
    private double x, y, x1, y1, width, height;
    private boolean drag;

    private final ArrayList<ModuleButton> moduleButtons = new ArrayList<>();

    public TitleButton(String name, double x, double y, double width, double height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        init();
    }

    public void init() {
    }

    private float animPos = -1;

    public ArrayList<ModuleButton> getModuleButtons() {
        return moduleButtons;
    }

    public void drawScreen(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        if (isDrag()) {
            x = x1 + mouseX;
            y = y1 + mouseY;
        }
        drawContext.fill((int) x, (int) y, (int) (x + width), (int) (y + height), Color.WHITE.getRGB());
        //centerX - textRenderer.getWidth(text) / 2

        RenderUtil.drawString(drawContext, name, (int) (x + width / 2) - MinecraftClient.getInstance().textRenderer.getWidth(name) / 2, (int) (y + height / 2 - 4), Color.BLACK.getRGB(), false);
        if (!moduleButtons.isEmpty()) {
            double startY = y + height;
            for (ModuleButton moduleButton : moduleButtons) {
                moduleButton.setX(x + 2);
                moduleButton.setY(startY);

                animPos = RenderUtil.move(animPos, (float) (moduleButton.getHeight()), 0.03F, 0.03F);

                startY += animPos;
                moduleButton.drawScreen(drawContext, mouseX, mouseY, partialTicks);
            }
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered((int) mouseX, (int) mouseY) && mouseButton == 0) {
            x1 = this.x - mouseX;
            y1 = this.y - mouseY;
            drag = true;
        } else {
            for (ModuleButton moduleButton : moduleButtons) {
                moduleButton.mouseClicked((int) mouseX, (int) mouseY, mouseButton);
            }
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            drag = false;
        }
        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.mouseReleased(mouseX, mouseY, state);
        }
    }

    protected void keyTyped(char typedChar, int keyCode) {
        for (ModuleButton moduleButton : moduleButtons) {
            moduleButton.keyTyped(typedChar, keyCode);
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
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

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

}
