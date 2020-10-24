package iskallia.thegoal.util;

import com.google.gson.annotations.Expose;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ItemParser {

    @Expose
    protected String itemExpression;

    private String itemId;
    private String itemNbtRaw;

    public ItemParser(String itemExpression) {
        this.itemExpression = itemExpression;
    }

    public void parse() {
        String[] itemTokens = itemExpression.split("\\{", 2);
        this.itemId = itemTokens[0];
        this.itemNbtRaw = itemTokens.length != 2 ? null : "{" + itemTokens[1];
    }

    public Item getItem() {
        return Item.REGISTRY.getObject(new ResourceLocation(itemId));
    }

    public NBTTagCompound getNBT() {
        try {
            return JsonToNBT.getTagFromJson(itemNbtRaw);

        } catch (NBTException e) {
            return null;
        }
    }

    public boolean isValid() {
        return getItem() != null
                && (itemNbtRaw == null || getNBT() != null);
    }

    public ItemStack generateItemStack(int quantity) {
        if (!isValid())
            return null;

        ItemStack itemStack = new ItemStack(getItem(), quantity);

        if (itemNbtRaw != null) {
            NBTTagCompound nbt = getNBT();
            if (nbt != null) itemStack.setTagCompound(nbt);
        }

        return itemStack;
    }

    @Override
    public String toString() {
        return String.format("%s | %s", itemId, itemNbtRaw);
    }

}
