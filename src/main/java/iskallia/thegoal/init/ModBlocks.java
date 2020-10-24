package iskallia.thegoal.init;

import iskallia.thegoal.block.BlockItemCollector;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

    public static final BlockItemCollector ITEM_COLLECTOR = new BlockItemCollector("item_collector");
    public static final ItemBlock ITEM_ITEM_COLLECTOR = getItemBlock(ITEM_COLLECTOR, 1);

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registerBlock(ITEM_COLLECTOR, registry);
    }

    public static void registerTileEntities() {}

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.register(ITEM_ITEM_COLLECTOR);
    }

    /* -------------------------- */

    private static void registerBlock(Block block, IForgeRegistry<Block> registry) {
        registry.register(block);
    }

    private static ItemBlock getItemBlock(Block block) {
        return getItemBlock(block, 64);
    }

    private static ItemBlock getItemBlock(Block block, int maxStackSize) {
        ItemBlock itemBlock = new ItemBlock(block);

        if (block.getRegistryName() == null)
            throw new InternalError("Cannot create ItemBlock of "
                    + block.getUnlocalizedName() + " without a Registry name");

        String resourceName = block.getRegistryName().getResourcePath();
        itemBlock.setUnlocalizedName(resourceName);
        itemBlock.setRegistryName(resourceName);
        itemBlock.setMaxStackSize(maxStackSize);
        return itemBlock;
    }

}
