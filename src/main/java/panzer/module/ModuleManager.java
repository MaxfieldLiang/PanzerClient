package panzer.module;

import panzer.module.hide.ModuleClickGui;
import panzer.module.movement.AutoSprint;
import panzer.module.movement.NoFall;
import panzer.module.render.Hud;

import java.util.ArrayList;

public class ModuleManager {
    private final ArrayList<Module> modules = new ArrayList<>();


    public void init() {
        modules.add(new AutoSprint());
        modules.add(new NoFall());


        modules.add(new Hud()); 
        modules.add(new ModuleClickGui());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
    public Module getModule(Class<? extends Module> clazz) {
        for (Module module : modules) {
            if (module.getClass() == clazz) {
                return module;
            }
        }
        return null;
    }
}
