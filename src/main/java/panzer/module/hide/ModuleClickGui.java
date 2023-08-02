package panzer.module.hide;

import org.lwjgl.glfw.GLFW;
import panzer.Client;
import panzer.module.Module;

public class ModuleClickGui extends Module {
    public ModuleClickGui() {
        super("ModuleClickGui", Category.HIDE);
        setKey(GLFW.GLFW_KEY_RIGHT_CONTROL);
    }

    @Override
    public void onEnable() {
        mc.setScreen(Client.clickGuiHook);
        setToggle(false);
        super.onEnable();
    }
}
