package panzer.module.movement;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import panzer.event.events.EventUpdate;
import panzer.module.Module;
import panzer.setting.Setting;

import java.util.ArrayList;

public class NoFall extends Module {
    public Setting settingMode;
    public NoFall() {
        super("Nofall", Category.MOVEMENT);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Vanill");
        settingManager.addSetting(settingMode = new Setting("Mode", "Vanill", strings, this));
    }

    @Override
    public void onUpdate(EventUpdate event) {
        setInfo(settingMode.getMode());
        boolean fallFlying = mc.player.isFallFlying();
        if (mc.player.fallDistance >= (fallFlying ? 1 : 2)) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
        super.onUpdate(event);
    }
}


