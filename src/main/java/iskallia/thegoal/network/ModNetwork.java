package iskallia.thegoal.network;

import iskallia.thegoal.TheGoal;
import iskallia.thegoal.network.packet.S2CCollectedAmount;
import iskallia.thegoal.network.packet.S2CSyncCollectorConfig;
import iskallia.thegoal.network.packet.S2CSyncPlayerTimer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModNetwork {

    public static SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(TheGoal.MOD_ID);

    private static int packetId = 0;

    private static int nextId() {
        return packetId++;
    }

    public static void registerPackets() {
        CHANNEL.registerMessage(
                S2CSyncPlayerTimer.S2CSyncPlayerTimerHandler.class,
                S2CSyncPlayerTimer.class,
                nextId(),
                Side.CLIENT
        );

        CHANNEL.registerMessage(
                S2CSyncCollectorConfig.S2CSyncCollectorConfigHandler.class,
                S2CSyncCollectorConfig.class,
                nextId(),
                Side.CLIENT
        );

        CHANNEL.registerMessage(
                S2CCollectedAmount.S2CCollectedAmountHandler.class,
                S2CCollectedAmount.class,
                nextId(),
                Side.CLIENT
        );
    }

}
