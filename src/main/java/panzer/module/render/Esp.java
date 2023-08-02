package panzer.module.render;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import panzer.event.events.EventRender;
import panzer.event.events.EventUpdate;
import panzer.module.Module;
import panzer.setting.Setting;
import panzer.utils.RenderUtil;

import java.awt.*;

public class Esp extends Module {
    public Setting playerS = new Setting("Player", false, this);
    public Setting mobS = new Setting("Mob", false, this);

    public Esp() {
        super("Esp", Category.RENDER);
        settingManager.addSetting(playerS);
        settingManager.addSetting(mobS);
    }

    @Override
    public void onUpdate(EventUpdate event) {

        super.onUpdate(event);
    }

    @Override
    public void onRender(EventRender event) {
        if (playerS.isEnable()) {
            for (AbstractClientPlayerEntity entity : mc.world.getPlayers()) {
                if (entity != mc.player) {
                    RenderUtil.draw3DBox(event.getMatrixStack(), entity.getBoundingBox(), new Color(255f, 255f, 255f, 1.0f), 1);
                }
            }
        }
        super.onRender(event);
    }
}
