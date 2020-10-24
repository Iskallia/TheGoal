package iskallia.thegoal.init;

import iskallia.thegoal.config.ConfigItemCollector;

public class ModConfigs {

    public static ConfigItemCollector CONFIG_ITEM_COLLECTOR;

    public static void initializeConfigs() {
        CONFIG_ITEM_COLLECTOR = (ConfigItemCollector) new ConfigItemCollector().readConfig();
    }

}
