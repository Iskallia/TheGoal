package iskallia.thegoal.event;

import iskallia.thegoal.world.data.TimerData;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.time.Instant;

@Mod.EventBusSubscriber
public class TickEvents {

    @SubscribeEvent
    // Server and Client
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == Side.SERVER) {
            long now = Instant.now().getEpochSecond();
            TimerData.get(event.player.world)
                    .tick(event.player.getServer(), now, event.player.getUniqueID());
        }
    }

}
