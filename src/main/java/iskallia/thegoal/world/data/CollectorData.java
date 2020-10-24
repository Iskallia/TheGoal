package iskallia.thegoal.world.data;

import iskallia.thegoal.TheGoal;
import iskallia.thegoal.init.ModConfigs;
import iskallia.thegoal.network.ModNetwork;
import iskallia.thegoal.network.packet.S2CSyncCollectorConfig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

public class CollectorData extends WorldSavedData {

    private static final String DATA_NAME = TheGoal.MOD_ID + "_CollectorData";

    private int collectedAmount;

    public CollectorData() {
        super(DATA_NAME);
    }

    public CollectorData(String name) {
        super(name);
    }

    public void add(int quantity) {
        collectedAmount += quantity;
        markDirty();
    }

    public void remove(int quantity) {
        collectedAmount -= quantity;
        markDirty();
    }

    public void reset() {
        collectedAmount = 0;
        markDirty();
    }

    public void notifyClients() {
        // TODO
    }

    public void syncConfigurations(MinecraftServer server, UUID playerUUID) {
        if (playerUUID == null) { // Send to All Clients!
            ModNetwork.CHANNEL.sendToAll(
                    new S2CSyncCollectorConfig(ModConfigs.CONFIG_ITEM_COLLECTOR)
            );

        } else { // Send to given player, if online
            EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(playerUUID);
            if (player != null) {
                ModNetwork.CHANNEL.sendTo(
                        new S2CSyncCollectorConfig(ModConfigs.CONFIG_ITEM_COLLECTOR),
                        player
                );
            }
        }
    }

    public int getCollectedAmount() {
        return collectedAmount;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("collectedAmount", Constants.NBT.TAG_INT)) {
            this.collectedAmount = nbt.getInteger("collectedAmount");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("collectedAmount", collectedAmount);
        return nbt;
    }

    public static CollectorData get(World world) {
        CollectorData savedData = (CollectorData) world.getMapStorage()
                .getOrLoadData(TimerData.class, DATA_NAME);

        if (savedData == null) {
            savedData = new CollectorData();
            world.getMapStorage().setData(DATA_NAME, savedData);
        }

        return savedData;
    }

}
