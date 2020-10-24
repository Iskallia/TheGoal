package iskallia.thegoal.network.packet;

import io.netty.buffer.ByteBuf;
import iskallia.thegoal.block.render.TESRItemCollector;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class S2CCollectedAmount implements IMessage {

    private int collectedAmount;

    public S2CCollectedAmount() {}

    public S2CCollectedAmount(int collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.collectedAmount = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.collectedAmount);
    }

    /* ------------------------------------- */

    public static class S2CCollectedAmountHandler implements IMessageHandler<S2CCollectedAmount, IMessage> {

        @Override
        public IMessage onMessage(S2CCollectedAmount message, MessageContext ctx) {
            TESRItemCollector.collectedAmount = message.collectedAmount;

            return null;
        }

    }

}
