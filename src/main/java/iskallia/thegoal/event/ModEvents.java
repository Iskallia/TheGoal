package iskallia.thegoal.event;

import iskallia.thegoal.command.CommandTheGoalPause;
import iskallia.thegoal.command.CommandTheGoalSet;
import iskallia.thegoal.command.CommandTheGoalStart;
import iskallia.thegoal.init.ModModels;
import iskallia.thegoal.network.ModNetwork;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;

public class ModEvents {

    public static void onConstruction(FMLConstructionEvent event) { }

    public static void onPreInitialization(FMLPreInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            ModModels.registerTESRs();
        }
    }

    public static void onInitialization(FMLInitializationEvent event) {
        ModNetwork.registerPackets();
    }

    public static void onPostInitialization(FMLPostInitializationEvent event) {
//        InitConfig.registerConfigs();
//        InitTieredLoot.registerTiers();
    }

    public static void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandTheGoalSet());
        event.registerServerCommand(new CommandTheGoalStart());
        event.registerServerCommand(new CommandTheGoalPause());
    }

}
