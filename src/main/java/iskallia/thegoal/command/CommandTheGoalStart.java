package iskallia.thegoal.command;

import iskallia.thegoal.world.data.TimerData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
            throw new WrongUsageException(getUsage(sender));

        String nickname = args[0];

        for (EntityPlayerMP player : getPlayers(server, sender, nickname)) {
            World world = sender.getEntityWorld();
            TimerData.get(world).start(server, nowUNIX, player.getUniqueID());
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos blockPos) {
        if (args.length == 1) {
            LinkedList<String> words = new LinkedList<>(Arrays.asList(server.getOnlinePlayerNames()));
            words.add("@a");
            return getListOfStringsMatchingLastWord(args, words);
        }

        return Collections.emptyList();
    }

}
