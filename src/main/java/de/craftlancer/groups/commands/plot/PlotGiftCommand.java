package de.craftlancer.groups.commands.plot;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlotManager;

public class PlotGiftCommand extends GroupSubCommand
{
    
    public PlotGiftCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        if (args.length < 2)
            return GroupLanguage.COMMAND_GENERAL_ARGUMENTS;
        if (!getPlugin().getServer().getOfflinePlayer(args[1]).hasPlayedBefore())
            return GroupLanguage.COMMAND_GENERAL_NOPLAYER;
        
        Player p = (Player) sender;
        OfflinePlayer p2 = getPlugin().getServer().getOfflinePlayer(args[1]);
        Plot plot = PlotManager.getPlot(p.getLocation());
        
        if (!plot.isOwner(p))
            return GroupLanguage.COMMAND_PLOT_NOTOWNER;
        if (!PlotManager.checkPlotLimit(p2.getName()))
            return GroupLanguage.COMMAND_PLOT_GIFT_LIMIT;
        
        plot.setOwner(p2.getName());
        if (p2.isOnline())
            p2.getPlayer().sendMessage(String.format(GroupLanguage.COMMAND_PLOT_GIFT_GIFTED, plot.getPosiString(), sender.getName()));
        
        return GroupLanguage.COMMAND_PLOT_GIFT_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("plot gift <Spieler> - Schenkt den Plot an einen Spieler");
    }
    
}
