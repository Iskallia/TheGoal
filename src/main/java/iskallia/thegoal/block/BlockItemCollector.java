package iskallia.thegoal.block;

import iskallia.thegoal.TheGoal;
import iskallia.thegoal.block.entity.TEItemCollector;
import iskallia.thegoal.init.ModConfigs;
import iskallia.thegoal.world.data.CollectorData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;

public class BlockItemCollector extends Block {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockItemCollector(String name) {
        super(Material.ROCK);

        this.setUnlocalizedName(name);
        this.setRegistryName(TheGoal.getResource(name));

        this.setHardness(2f);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(FACING, EnumFacing.NORTH));
    }

    /* ---------------------------- */

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    /* ---------------------------- */

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return true;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    /* ---------------------------- */

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TEItemCollector();
    }

    /* ---------------------------- */

    public boolean isPlayerLookingAtFront(World world, BlockPos blockPos, EntityPlayerMP player) {
        IBlockState blockState = world.getBlockState(blockPos);

        if (blockState == null) return false;

        RayTraceResult rayResult = ForgeHooks.rayTraceEyes(player, player.interactionManager.getBlockReachDistance() + 1);

        if (rayResult == null) return false;

        EnumFacing facingSide = blockState.getValue(FACING);

        return facingSide == rayResult.sideHit;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            IBlockState north = world.getBlockState(pos.north());
            IBlockState south = world.getBlockState(pos.south());
            IBlockState west = world.getBlockState(pos.west());
            IBlockState east = world.getBlockState(pos.east());
            EnumFacing facing = state.getValue(FACING);

            if (facing == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) {
                facing = EnumFacing.SOUTH;
            } else if (facing == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) {
                facing = EnumFacing.NORTH;
            } else if (facing == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) {
                facing = EnumFacing.EAST;
            } else if (facing == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) {
                facing = EnumFacing.WEST;
            }

            world.setBlockState(pos, state.withProperty(FACING, facing), 2);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && isPlayerLookingAtFront(world, pos, (EntityPlayerMP) player)) {
            CollectorData collectorData = CollectorData.get(world);
            ItemStack heldStack = player.getHeldItem(hand);

            if (heldStack.getItem() == ModConfigs.CONFIG_ITEM_COLLECTOR.itemParser.getItem()) {
                collectorData.add(heldStack.getCount());
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
        }

        return true;
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
        if (!world.isRemote && isPlayerLookingAtFront(world, pos, (EntityPlayerMP) player)) {
            CollectorData collectorData = CollectorData.get(world);
            ItemStack heldStack = player.getHeldItemMainhand();

            if (heldStack.getItem() == ModConfigs.CONFIG_ITEM_COLLECTOR.itemParser.getItem()) {
                collectorData.add(1);
                heldStack.setCount(heldStack.getCount() - 1);
            }
        }

        super.onBlockClicked(world, pos, player);
    }

}
