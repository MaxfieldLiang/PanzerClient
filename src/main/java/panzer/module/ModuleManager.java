package panzer.module;

import panzer.module.combat.AntiKnowback;
import panzer.module.combat.Criticals;
import panzer.module.combat.KillAura;
import panzer.module.hide.ModuleClickGui;
import panzer.module.misc.AutoNod;
import panzer.module.movement.*;
import panzer.module.render.*;
import panzer.module.world.ChestPick;
import panzer.module.world.Scaffold;
import panzer.module.world.Timer;

import java.util.ArrayList;

public class ModuleManager {
    private final ArrayList<Module> modules = new ArrayList<>();


    public void init() {
        //combat
        modules.add(new AntiKnowback());
        modules.add(new Criticals());
        modules.add(new KillAura());
        //movement
        modules.add(new BetterElytra());
        modules.add(new AutoSprint());
        modules.add(new FastLadder());
        modules.add(new LiquidWalk());
        modules.add(new Flight());
        modules.add(new AntiSlow());
        modules.add(new AntiWeb());
        modules.add(new BoatFly());
        modules.add(new NoFall());
        modules.add(new Speed());
        //render
        modules.add(new FullBright());
        modules.add(new Nametag());
        modules.add(new Xray());
        modules.add(new Esp());
        modules.add(new Hud());
        //world
        modules.add(new ChestPick());
        modules.add(new Scaffold());
        modules.add(new Timer());
        //misc
        modules.add(new AutoNod());


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
