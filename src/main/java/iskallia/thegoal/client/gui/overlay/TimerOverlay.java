package iskallia.thegoal.client.gui.overlay;

import iskallia.thegoal.world.storage.PlayerSpecificTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.time.Instant;

@Mod.EventBusSubscriber
public class TimerOverlay {

    public static PlayerSpecificTimer timer;

    public static void updateTimer(PlayerSpecificTimer timer) {
        TimerOverlay.timer = timer;
    }

    public static String formatTimeString() {
        long seconds = timer.pendingSeconds % 60;
        long minutes = (timer.pendingSeconds / 60) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @SubscribeEvent
    public static void onRenderGuiPost(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR)
            return; // Render only on HOTBAR

        if (timer == null)
            return;

        long nowUNIX = Instant.now().getEpochSecond();

        if (!timer.paused) timer.tick(nowUNIX);

        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

        String timeText = formatTimeString();
        fontRenderer.drawString(timeText, 10, 30, 0xFF_FFFFFF);
    }

    @SubscribeEvent
    public static void onLeavingServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        timer = null;
    }

}
