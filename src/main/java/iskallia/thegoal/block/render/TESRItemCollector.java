package iskallia.thegoal.block.render;

import iskallia.thegoal.block.BlockItemCollector;
import iskallia.thegoal.block.entity.TEItemCollector;
import iskallia.thegoal.init.ModBlocks;
import iskallia.thegoal.init.ModConfigs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import java.text.DecimalFormat;

public class TESRItemCollector extends TileEntitySpecialRenderer<TEItemCollector> {

    private static DecimalFormat NUMBER_FORMAT = new DecimalFormat("#.##");

    public static int collectedAmount = 0;

    @Override
    public void render(TEItemCollector te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ItemStack itemStack = ModConfigs.CONFIG_ITEM_COLLECTOR.itemParser.generateItemStack(1);

        if (itemStack == null) return;

        IBlockState state = te.getWorld().getBlockState(te.getPos());
        if (state.getBlock() != ModBlocks.ITEM_COLLECTOR) return;
        EnumFacing facing = state.getValue(BlockItemCollector.FACING);

        float floatingMax = 0.05f;
        float rotationAnimationAngle = (float) ((System.currentTimeMillis() / 50d) % 360);
//        float yOffset = (float) (Math.sin(System.currentTimeMillis() / 1000d)) * floatingMax;

        float scale = 1.5f;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + ModConfigs.CONFIG_ITEM_COLLECTOR.yOffset, z);
        GlStateManager.translate(0.5f, 0.5 / scale, 0.5f);
        GlStateManager.rotate(rotationAnimationAngle, 0, 1, 0);
        GlStateManager.scale(scale, scale, scale);
        Minecraft.getMinecraft().getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();

        renderCenteredText(itemStack.getDisplayName(), 15.5f / 16f, x, y, z, facing);
        renderCenteredText(NUMBER_FORMAT.format(collectedAmount), 1.5f / 16f, x, y, z, facing);

        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    }

    public void renderCenteredText(String text, float yOffset, double x, double y, double z, EnumFacing facing) {
        float size = (float) this.getFontRenderer().getStringWidth(text) * 0.007f;
        float textCenter = (1.0f + size) / 2.0f;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        if (facing == EnumFacing.NORTH) {
            GlStateManager.translate(textCenter, yOffset, -0.01f);
            GlStateManager.rotate(180, 0, 0, 1);
        } else if (facing == EnumFacing.SOUTH) {
            GlStateManager.translate(-textCenter + 1, yOffset, 1.01f);
            GlStateManager.rotate(180, 0, 0, 1);
            GlStateManager.rotate(180, 0, 1, 0);
        } else if (facing == EnumFacing.EAST) {
            GlStateManager.translate(1.01f, yOffset, textCenter);
            GlStateManager.rotate(180, 0, 0, 1);
            GlStateManager.rotate(90, 0, 1, 0);
        } else if (facing == EnumFacing.WEST) {
            GlStateManager.translate(-0.01f, yOffset, -textCenter + 1);
            GlStateManager.rotate(180, 0, 0, 1);
            GlStateManager.rotate(270, 0, 1, 0);
        }
        GlStateManager.scale(0.007f, 0.007f, 0.007f);
        getFontRenderer().drawString(text, 0, 0, 0xFF_FFFFFF);
        GlStateManager.popMatrix();
    }

}
