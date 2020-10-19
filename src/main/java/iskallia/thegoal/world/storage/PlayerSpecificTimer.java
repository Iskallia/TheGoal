package iskallia.thegoal.world.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public class PlayerSpecificTimer implements INBTSerializable<NBTTagCompound> {

    private UUID playerUUID;
    private boolean paused;
    private long lastUpdatedUNIX;
    private long targetSeconds;
    private long pendingSeconds;

    public PlayerSpecificTimer(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.paused = true;
    }

    public void pause(long stoppedUNIX) {
        tick(stoppedUNIX);
        this.paused = true;
    }

    public void start(long startedUNIX) {
        this.paused = false;
        this.lastUpdatedUNIX = startedUNIX;
    }

    public void reset(long targetSeconds) {
        this.paused = true;
        this.targetSeconds = targetSeconds;
        this.pendingSeconds = targetSeconds;
    }

    public boolean tick(long nowUNIX) {
        if (paused) return false;

        long dt = nowUNIX - lastUpdatedUNIX;
        this.pendingSeconds = Math.max(0, pendingSeconds - dt);
        lastUpdatedUNIX = nowUNIX;
        return true;
    }

    public double getTargetSeconds() {
        return targetSeconds;
    }

    public double getPendingSeconds() {
        return pendingSeconds;
    }

    public boolean isFinished() {
        return pendingSeconds <= 0;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setUniqueId("playerUUID", playerUUID);
        nbt.setBoolean("paused", paused);
        nbt.setLong("lastUpdatedUNIX", lastUpdatedUNIX);
        nbt.setLong("targetSeconds", targetSeconds);
        nbt.setLong("pendingSeconds", pendingSeconds);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.playerUUID = nbt.getUniqueId("playerUUID");
        this.paused = nbt.getBoolean("paused");
        this.lastUpdatedUNIX = nbt.getLong("lastUpdatedUNIX");
        this.targetSeconds = nbt.getLong("targetSeconds");
        this.pendingSeconds = nbt.getLong("pendingSeconds");
    }

}
