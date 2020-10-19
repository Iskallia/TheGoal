package iskallia.thegoal.event;

import iskallia.thegoal.world.data.TimerData;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.time.Instant;

@Mod.EventBusSubscriber
public class TimerEvents {

    @SubscribeEvent
    // Server and Client
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        long now = Instant.now().getEpochSecond();
        TimerData.get(event.player.world).tick(now, event.player.getUniqueID());
    }

    @SubscribeEvent
    // Server only
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        long now = Instant.now().getEpochSecond();
        TimerData.get(event.player.world).logout(now, event.player.getUniqueID());
    }

}
