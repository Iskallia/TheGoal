package iskallia.thegoal.client.gui.overlay;

import iskallia.thegoal.TheGoal;
import iskallia.thegoal.world.storage.PlayerSpecificTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.time.Instant;

@Mod.EventBusSubscriber
public class TimerOverlay {

    public static final ResourceLocation OVERLAY_RESOURCE = TheGoal.getTexture("gui/overlay.png");

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

        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.getTextureManager().bindTexture(OVERLAY_RESOURCE);
        GlStateManager.enableBlend();

        String timeText = formatTimeString();

        int textWidth = minecraft.fontRenderer.getStringWidth(timeText);
        int containerWidth = 55;

        ScaledResolution scaledResolution = new ScaledResolution(minecraft);
        int x = (scaledResolution.getScaledWidth() - containerWidth) / 2;
        minecraft.ingameGUI.drawTexturedModalRect(x, 0,
                0, 0, 55, 19);

        if (timer.paused) {
            minecraft.ingameGUI.drawTexturedModalRect(x + 8, 3,
                    68, 0, 9, 9);
        } else {
            minecraft.ingameGUI.drawTexturedModalRect(x + 7, 2,
                    56, 0, 11, 11);
        }

        drawTextWithBorders(timeText, x + 21, 4, 0xFF_FFFFFF, 0xFF_000000);
    }

    public static void drawTextWithBorders(String text, int x, int y, int textColor, int borderColor) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

        fontRenderer.drawString(text, x - 1, y, borderColor);
        fontRenderer.drawString(text, x + 1, y, borderColor);
        fontRenderer.drawString(text, x, y - 1, borderColor);
        fontRenderer.drawString(text, x, y + 1, borderColor);
        fontRenderer.drawString(text, x, y, textColor);
    }

    @SubscribeEvent
    public static void onLeavingServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        timer = null;
    }

}
