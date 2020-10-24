package iskallia.thegoal.config;

import com.google.gson.annotations.Expose;
import iskallia.thegoal.util.ItemParser;

public class ConfigItemCollector extends ModConfig {

    @Expose
    public ItemParser itemParser;

    @Expose
    public float yOffset;

    @Override
    public String getLocation() {
        return "item_collector.json";
    }

    @Override
    protected void resetConfig() {
        this.itemParser = new ItemParser("diamond_block");
        this.yOffset = 0;
    }

    @Override
    public ConfigItemCollector readConfig() {
        ConfigItemCollector config = (ConfigItemCollector) super.readConfig();
        config.itemParser.parse();
        return config;
    }

}
