package iskallia.thegoal.world.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public class PlayerSpecificTimer implements INBTSerializable<NBTTagCompound> {

    private UUID playerUUID;
    private boolean stopped;
    private double secondsPending;

    public PlayerSpecificTimer(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setUniqueId("playerUUID", playerUUID);
        nbt.setBoolean("stopped", stopped);
        nbt.setDouble("secondsPending", secondsPending);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        UUID playerUUID = nbt.getUniqueId("playerUUID");
        boolean stopped = nbt.getBoolean("stopped");
        double secondsPending = nbt.getDouble("secondsPending");

        this.playerUUID = playerUUID;
        this.stopped = stopped;
        this.secondsPending = secondsPending;
    }

}
