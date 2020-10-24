package iskallia.thegoal.event;

import iskallia.thegoal.world.data.CollectorData;
import iskallia.thegoal.world.data.TimerData;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.time.Instant;

@Mod.EventBusSubscriber
public class PlayerEvents {

    @SubscribeEvent
    // Server only
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        long now = Instant.now().getEpochSecond();

        TimerData.get(event.player.world)
                .login(event.player.getServer(), now, event.player.getUniqueID());

        CollectorData.get(event.player.world)
                .syncConfigurations(event.player.getServer(), event.player.getUniqueID());

        CollectorData.get(event.player.world)
                .notifyPlayer(event.player.getServer(), event.player.getUniqueID());
    }

    @SubscribeEvent
    // Server only
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        long now = Instant.now().getEpochSecond();

        TimerData.get(event.player.world)
                .logout(event.player.getServer(), now, event.player.getUniqueID());
    }

}
