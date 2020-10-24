package iskallia.thegoal.event;

import iskallia.thegoal.TheGoal;
import iskallia.thegoal.init.ModBlocks;
import iskallia.thegoal.init.ModModels;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TheGoal.MOD_ID)
public class RegistryEvents {

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        ModBlocks.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
//        InitItem.registerItems(event.getRegistry());
        ModBlocks.registerItemBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ModModels.registerItemModels();
    }

}
