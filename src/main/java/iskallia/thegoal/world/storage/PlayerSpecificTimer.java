package iskallia.thegoal.world.storage;

import iskallia.thegoal.network.ModNetwork;
import iskallia.thegoal.network.packet.S2CSyncPlayerTimer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

public class PlayerSpecificTimer implements INBTSerializable<NBTTagCompound> {

    public UUID playerUUID;
    public boolean paused;
    public long lastUpdatedUNIX;
    public long targetSeconds;
    public long pendingSeconds;

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

    public boolean isFinished() {
        return pendingSeconds <= 0;
    }

    @SideOnly(Side.SERVER)
    public void syncWithPlayer(MinecraftServer server) {
        EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(this.playerUUID);

        if (player != null) {
            ModNetwork.CHANNEL.sendTo(
                    new S2CSyncPlayerTimer(this),
                    player
            );
        }
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
