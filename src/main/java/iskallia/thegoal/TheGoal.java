package iskallia.thegoal;

import iskallia.thegoal.event.ModEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = TheGoal.MOD_ID, name = TheGoal.MOD_NAME, version = TheGoal.MOD_VERSION)
public class TheGoal {

    @Mod.Instance
    private static TheGoal INSTANCE;

    public static TheGoal getInstance() {
        return INSTANCE;
    }

    public static final String MOD_ID = "thegoal";
    public static final String MOD_NAME = "TheGoal";
    public static final String MOD_VERSION = "${version}";

    public static final Logger LOGGER = LogManager.getLogger(TheGoal.MOD_NAME);

    public static ResourceLocation getResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static ResourceLocation getTexture(String path) {
        return getResource("textures/" + path);
    }

    @Mod.EventHandler
    public void onConstruction(FMLConstructionEvent event) {
        ModEvents.onConstruction(event);
    }

    @Mod.EventHandler
    public void onPreInitialization(FMLPreInitializationEvent event) {
        ModEvents.onPreInitialization(event);
    }

    @Mod.EventHandler
    public void onInitialization(FMLInitializationEvent event) {
        ModEvents.onInitialization(event);
    }

    @Mod.EventHandler
    public void onPostInitialization(FMLPostInitializationEvent event) {
        ModEvents.onPostInitialization(event);
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        ModEvents.onServerStart(event);
    }

}
