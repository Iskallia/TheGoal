package iskallia.thegoal.network.packet;

import io.netty.buffer.ByteBuf;
import iskallia.thegoal.world.storage.PlayerSpecificTimer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class S2CSyncPlayerTimer implements IMessage {

    private PlayerSpecificTimer timer;

    public S2CSyncPlayerTimer() {}

    public S2CSyncPlayerTimer(PlayerSpecificTimer timer) {
        this.timer = timer;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        int uuidLength = buffer.readInt();
        UUID playerUUID = UUID.fromString(buffer.readCharSequence(uuidLength, StandardCharsets.UTF_8).toString());
        this.timer = new PlayerSpecificTimer(playerUUID);

        this.timer.paused = buffer.readBoolean();
        this.timer.lastUpdatedUNIX = buffer.readLong();
        this.timer.targetSeconds = buffer.readLong();
        this.timer.pendingSeconds = buffer.readLong();
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        String uuid = timer.playerUUID.toString();
        buffer.writeInt(uuid.length());
        buffer.writeCharSequence(uuid, StandardCharsets.UTF_8);

        buffer.writeBoolean(timer.paused);
        buffer.writeLong(timer.lastUpdatedUNIX);
        buffer.writeLong(timer.targetSeconds);
        buffer.writeLong(timer.pendingSeconds);
    }

    /* ------------------------------------- */

    public static class S2CSyncPlayerTimerHandler implements IMessageHandler<S2CSyncPlayerTimer, IMessage> {

        @Override
        public IMessage onMessage(S2CSyncPlayerTimer message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            WorldServer world = player.getServerWorld();

            // TODO: Sync HUD
            System.out.println("Received: " + message.timer.serializeNBT());

            return null;
        }

    }

}
