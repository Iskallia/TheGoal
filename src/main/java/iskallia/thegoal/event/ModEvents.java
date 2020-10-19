package iskallia.thegoal.event;

import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;

public class ModEvents {

    public static void onConstruction(FMLConstructionEvent event) {

    }

    public static void onPreInitialization(FMLPreInitializationEvent event) {
//        InitEntity.registerEntities();

        if (event.getSide() == Side.CLIENT) {
//            InitEntity.registerEntityRenderers();
//            InitModel.registerTileEntityRenderers();
        }
    }

    public static void onInitialization(FMLInitializationEvent event) {
//        InitPacket.registerPackets();
//        NetworkRegistry.INSTANCE.registerGuiHandler(Traders.getInstance(), new GuiHandler());
    }

    public static void onPostInitialization(FMLPostInitializationEvent event) {
//        InitConfig.registerConfigs();
//        InitTieredLoot.registerTiers();
    }

    public static void onServerStart(FMLServerStartingEvent event) {
//        event.registerServerCommand(new CommandITraders());
//        event.registerServerCommand(new CommandGiveBits());
    }

}
