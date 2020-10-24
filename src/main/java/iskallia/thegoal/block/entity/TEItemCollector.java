package iskallia.thegoal.block.entity;

import iskallia.thegoal.init.ModConfigs;
import iskallia.thegoal.world.data.CollectorData;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TEItemCollector extends TileEntity {

    IItemHandler itemInputHandler;

    public TEItemCollector() {
        super();
        this.itemInputHandler = createItemInputHandler();
    }

    /* -------------------------------------- */

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemInputHandler);
        }

        return super.getCapability(capability, facing);
    }

    public IItemHandler createItemInputHandler() {
        return new IItemHandler() {

            @Override
            public int getSlots() {
                return 1;
            }

            @Nonnull
            @Override
            public ItemStack getStackInSlot(int slot) {
                return ItemStack.EMPTY;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack itemStack, boolean simulate) {
                if (!world.isRemote && itemStack.getItem() == ModConfigs.CONFIG_ITEM_COLLECTOR.itemParser.getItem()) {
                    if (!simulate) {
                        CollectorData collectorData = CollectorData.get(world);
                        collectorData.add(itemStack.getCount());
                    }

                    return ItemStack.EMPTY;
                }

                return itemStack;
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }

            @Override
            public int getSlotLimit(int slot) {
                return Integer.MAX_VALUE;
            }

        };
    }

    /* -------------------------------------- */

}
