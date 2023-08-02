package panzer.event;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;
import panzer.Client;
import panzer.event.events.EventGui;
import panzer.event.events.EventLivingUpdate;
import panzer.event.events.EventMotionUpdate;
import panzer.event.events.EventUpdate;
import panzer.module.Module;

public class EventManager {
    public void onKeyHook(int key, int action) {
        //Module toggle System
        Screen screen = MinecraftClient.getInstance().currentScreen;
        if (action == GLFW.GLFW_PRESS && screen == null) {
            for (Module module : Client.moduleManager.getModules()) {
                if (module.getKey() == key) {
                    module.setToggle(!module.isToggle());
                }

            }
        }
    }

    public void onMotionUpdate(EventMotionUpdate event) {

        for (Module module : Client.moduleManager.getModules()) {
            if (module.isToggle()) {
                module.onEventMotionUpdate(event);
            }
        }
    }

    public void onGuiRender(EventGui event) {
        for (Module module : Client.moduleManager.getModules()) {
            if (module.isToggle()) {
                module.onGuiRender(event);
            }
        }

        if (!Client.clickGuiHook.isOpen() && Client.clickGuiHook.getClickGui().openAnim != 0) {
            Client.clickGuiHook.render(event.getDrawContext(), 0, 0, 0);
        }
    }

    public void onUpdate(EventUpdate event) {
        for (Module module : Client.moduleManager.getModules()) {
            if (module.isToggle()) {
                module.onUpdate(event);
            }
        }
    }

    public void onLivingUpdate(EventLivingUpdate event) {
        for (Module module : Client.moduleManager.getModules()) {
            if (module.isToggle()) {
                module.onLivingUpdate(event);
            }
        }
    }
}
