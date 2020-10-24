package iskallia.thegoal.init;

import iskallia.thegoal.TheGoal;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ModModels {

    public static void registerItemModels() {
        registerSimpleItemModel(ModBlocks.ITEM_ITEM_COLLECTOR, 0);

        registerBlockModel(ModBlocks.ITEM_COLLECTOR, 0);
    }

    public static void registerTESRs() { }

    /* ---------------------------------- */

    private static void registerSimpleItemModel(Item item, int metadata) {
        ModelLoader.setCustomModelResourceLocation(item, metadata,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    private static void registerBlockModel(Block block, int metadata) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata,
                new ModelResourceLocation(TheGoal.getResource(block.getUnlocalizedName().substring(5)), "inventory"));
    }

}
