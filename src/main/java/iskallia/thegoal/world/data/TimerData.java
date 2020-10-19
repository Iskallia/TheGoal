package iskallia.thegoal.world.data;

import iskallia.thegoal.TheGoal;
import iskallia.thegoal.world.storage.PlayerSpecificTimer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimerData extends WorldSavedData {

    private static final String DATA_NAME = TheGoal.MOD_ID + "_TimerData";

    private Map<UUID, PlayerSpecificTimer> timersMap = new HashMap<>();

    public PlayerSpecificTimer getTimer(UUID playerUUID) {
        return timersMap.getOrDefault(playerUUID, new PlayerSpecificTimer(playerUUID));
    }

    public void tick(long unix, UUID playerUUID) {
        if (getTimer(playerUUID).tick(unix)) {
            markDirty();
        }
    }

    public void start(long unix, UUID playerUUID) {
        getTimer(playerUUID).start(unix);
        markDirty();
    }

    public void reset(long targetSeconds, UUID playerUUID) {
        getTimer(playerUUID).reset(targetSeconds);
        markDirty();
    }

    public void logout(long unix, UUID playerUUID) {
        getTimer(playerUUID).pause(unix);
        markDirty();
    }

    public TimerData() {
        super(DATA_NAME);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("timers")) {
            NBTTagList timers = nbt.getTagList("timers", Constants.NBT.TAG_COMPOUND);

            timers.forEach(nbtBase -> {
                NBTTagCompound timerNBT = (NBTTagCompound) nbtBase;
                UUID playerUUID = timerNBT.getUniqueId("playerUUID");
                PlayerSpecificTimer playerSpecificTimer = new PlayerSpecificTimer(playerUUID);
                playerSpecificTimer.deserializeNBT(timerNBT.getCompoundTag("timer"));
            });
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagList timers = new NBTTagList();

        timersMap.forEach((uuid, timer) -> {
            NBTTagCompound timerNBT = new NBTTagCompound();
            timerNBT.setUniqueId("playerUUID", uuid);
            timerNBT.setTag("timer", timer.serializeNBT());
            timers.appendTag(timerNBT);
        });

        nbt.setTag("timers", timers);
        return nbt;
    }

    public static TimerData get(World world) {
        TimerData savedData = (TimerData) world.getMapStorage().getOrLoadData(TimerData.class, DATA_NAME);

        if (savedData == null) {
            savedData = new TimerData();
            world.getMapStorage().setData(DATA_NAME, savedData);
        }

        return savedData;
    }

}
