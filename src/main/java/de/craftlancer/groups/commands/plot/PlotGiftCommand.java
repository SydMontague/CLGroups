package de.craftlancer.groups.commands.plot;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;

public class PlotGiftCommand extends GroupSubCommand
{
    
    public PlotGiftCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 2)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else if (!getPlugin().getServer().getOfflinePlayer(args[1]).hasPlayedBefore())
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOPLAYER);
        else
        {
            Player p = (Player) sender;
            OfflinePlayer p2 = getPlugin().getServer().getOfflinePlayer(args[1]);
            Plot plot = getPlugin().getPlot(p.getLocation());
            
            if (!plot.isOwner(p))
                sender.sendMessage(GroupLanguage.COMMAND_PLOT_NOTOWNER);
            else if (!getPlugin().checkPlotLimit(p2.getName()))
                sender.sendMessage(GroupLanguage.COMMAND_PLOT_GIFT_LIMIT);
            else
            {
                plot.setOwner(p2.getName());
                sender.sendMessage(GroupLanguage.COMMAND_PLOT_GIFT_SUCCESS);
                if (p2.isOnline())
                    p2.getPlayer().sendMessage(String.format(GroupLanguage.COMMAND_PLOT_GIFT_GIFTED, plot.getPosiString(), sender.getName()));
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("plot gift <Spieler> - Schenkt den Plot an einen Spieler");
    }
    
}
