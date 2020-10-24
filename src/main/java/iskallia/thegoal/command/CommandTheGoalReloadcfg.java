package iskallia.thegoal.command;

import iskallia.thegoal.init.ModConfigs;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandTheGoalReloadcfg extends CommandBase {

    @Override
    public String getName() {
        return "the_goal:reloadcfg";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return getName();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ModConfigs.initializeConfigs();
    }

}
