package iskallia.thegoal.network.packet;

import io.netty.buffer.ByteBuf;
import iskallia.thegoal.config.ConfigItemCollector;
import iskallia.thegoal.init.ModConfigs;
import iskallia.thegoal.util.ItemParser;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.StandardCharsets;

public class S2CSyncCollectorConfig implements IMessage {

    ConfigItemCollector config;

    public S2CSyncCollectorConfig() {}

    public S2CSyncCollectorConfig(ConfigItemCollector config) {
        this.config = config;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        int length = buffer.readInt();
        String itemExpression = buffer.readCharSequence(length, StandardCharsets.UTF_8).toString();
        config = new ConfigItemCollector();
        config.itemParser = new ItemParser(itemExpression);
        config.yOffset = buffer.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        String itemExpression = config.itemParser.getItemExpression();

        buffer.writeInt(itemExpression.length());
        buffer.writeCharSequence(itemExpression, StandardCharsets.UTF_8);

        buffer.writeFloat(config.yOffset);
    }

    /* ---------------------------- */

    public static class S2CSyncCollectorConfigHandler implements IMessageHandler<S2CSyncCollectorConfig, IMessage> {

        @Override
        public IMessage onMessage(S2CSyncCollectorConfig message, MessageContext ctx) {
            ModConfigs.CONFIG_ITEM_COLLECTOR = message.config;

            return null;
        }

    }

}
