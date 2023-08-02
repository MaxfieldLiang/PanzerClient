package panzer.injection.mixins;

import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin
        extends SimpleChannelInboundHandler<Packet<?>>
{

}