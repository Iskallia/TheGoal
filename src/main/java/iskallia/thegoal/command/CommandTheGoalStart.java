package iskallia.thegoal.command;

import iskallia.thegoal.world.data.TimerData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.time.Instant;

public class CommandTheGoalStart extends CommandBase {

    @Override
    public String getName() {
        return "the_goal:start";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return getName() + " <player_nick>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        long nowUNIX = Instant.now().getEpochSecond();

        if (args.length != 1)
            throw new CommandException("Invalid length of arguments.");

        String nickname = args[0];

        EntityPlayerMP player = getPlayer(server, sender, nickname);

        World world = sender.getEntityWorld();
        TimerData.get(world).start(server, nowUNIX, player.getUniqueID());
    }

}
