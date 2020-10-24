package iskallia.thegoal.block;

import iskallia.thegoal.TheGoal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;

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
    public boolean hasTileEntity(IBlockState state) {
        return false;
    }

    /* ---------------------------- */

}
