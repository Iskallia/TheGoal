package iskallia.thegoal.event;

import iskallia.thegoal.client.gui.overlay.TimerOverlay;
import iskallia.thegoal.init.ModConfigs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod.EventBusSubscriber
public class ClientEvents {

    @SubscribeEvent
    public static void onLeavingServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        ModConfigs.initializeConfigs(); // <-- Reload configs, because they probably are overwritten by the server

        TimerOverlay.timer = null;
    }

}
