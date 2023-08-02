package panzer.module.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import panzer.Client;
import panzer.event.events.EventGui;
import panzer.module.Module;
import panzer.setting.Setting;
import panzer.utils.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Hud extends Module {
    public final Setting arrayListS = new Setting("ArrayList", true, this);
    public Hud() {
        super("Hud", Category.RENDER);
        setKey(GLFW.GLFW_KEY_Y);
        settingManager.addSetting(arrayListS);
    }

    public float anim = 10;

    @Override
    public void onGuiRender(EventGui event) {
        DrawContext drawContext = event.getDrawContext();
        //RenderUtil.drawString(((EventGui) event).getDrawContext(), "Panzer", 3, 3, Color.WHITE.getRGB(), true);
        if (arrayListS.isEnable()) {
            drawArrayList(drawContext);
        }
        super.onGuiRender(event);
    }

    private void drawArrayList(DrawContext drawContext) {
        Window sr = mc.getWindow();
        ArrayList<Module> modules = (ArrayList<Module>) Client.moduleManager.getModules().clone();
        Collections.sort(modules, new Comparator<Module>() {
            public int compare(Module m1, Module m2) {
                if (RenderUtil.getStringWidth(m1.getName() + " " + m1.getInfo()) > RenderUtil.getStringWidth(m2.getName() + " " + m2.getInfo())) {
                    return -1;
                }
                if (RenderUtil.getStringWidth(m1.getName() + " " + m1.getInfo()) < RenderUtil.getStringWidth(m2.getName() + " " + m2.getInfo())) {
                    return 1;
                }
                return 0;
            }
        });
        int yPos = 2;
        int add = 10;
        for (Module module : modules) {
            if (module.getCategory() != Category.HIDE && module.getCategory() != Category.SETTING) {
                if (module.arrayListX != -RenderUtil.getStringWidth(module.getName() + " " + module.getInfo()) - 2) {
                    RenderUtil.drawString(drawContext, module.getName(), module.arrayListX, module.arrayListY, Color.WHITE.getRGB(), true);
                    if (module.getInfo() != null) {
                        RenderUtil.drawString(drawContext, module.getInfo(), module.arrayListX + RenderUtil.getStringWidth(module.getName() + " "), module.arrayListY, RenderUtil.reAlpha(Color.WHITE.getRGB(), 0.6F), true);
                    }
                }
                if (module.isToggle()) {
                    module.arrayListX = RenderUtil.move(module.arrayListX, 2, 0.04F, 0.04F);
                    module.arrayListY = RenderUtil.move(module.arrayListY, yPos, 0.04F, 0.04F);
                    yPos += add;
                } else {
                    module.arrayListX = RenderUtil.move(module.arrayListX, -RenderUtil.getStringWidth(module.getName() + " " + module.getInfo()) - 2, 0.04F, 0.04F);
                }
            }
        }
    }
}
