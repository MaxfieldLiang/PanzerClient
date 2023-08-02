package panzer.module.misc;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import panzer.event.events.EventUpdate;
import panzer.module.Module;

public class AutoNod extends Module {
    public AutoNod() {
        super("AutoNod", Category.MISC);
    }

    @Override
    public void onUpdate(EventUpdate event) {
        float timer = mc.player.age % 20 / 10F;
        float pitch = MathHelper.sin((float) (timer * 3.14159)) * 90F;

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.
                LookAndOnGround(mc.player.getYaw()
                , pitch, mc.player.isOnGround()));
        super.onUpdate(event);
    }
}
