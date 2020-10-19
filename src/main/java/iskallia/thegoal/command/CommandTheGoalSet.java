package iskallia.thegoal.command;

import iskallia.thegoal.world.data.TimerData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommandTheGoalSet extends CommandBase {

    @Override
    public String getName() {
        return "the_goal:set";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return getName() + " <player_nick> <seconds>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 2)
            throw new CommandException("Invalid length of arguments.");

        String nickname = args[0];
        long seconds = parseLong(args[1]);

        EntityPlayerMP player = getPlayer(server, sender, nickname);

        World world = sender.getEntityWorld();
        TimerData.get(world).reset(server, seconds, player.getUniqueID());
    }

}
