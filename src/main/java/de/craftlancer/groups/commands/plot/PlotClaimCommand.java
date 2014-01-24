package de.craftlancer.groups.commands.plot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;

public class PlotClaimCommand extends GroupSubCommand
{
    // TODO economy implementation
    public PlotClaimCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else
        {
            Player p = (Player) sender;
            Plot plot = getPlugin().getPlot(p.getLocation());
            
            if (!plot.isBuyable())
                sender.sendMessage(GroupLanguage.COMMAND_PLOT_CLAIM_NOTFORSALE);
            else if (!plot.isTownPlot() && getPlugin().checkPlotDistance(plot, p))
                sender.sendMessage(GroupLanguage.COMMAND_PLOT_CLAIM_TOOCLOSE);
            else if (plot.isTownPlot() && !plot.getTown().hasPermission(p.getName(), "town.buy"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else if (getPlugin().checkPlotLimit(p))
                sender.sendMessage(GroupLanguage.COMMAND_PLOT_CLAIM_LIMIT);
            else
            {
                plot.setOwner(p.getName());
                sender.sendMessage(GroupLanguage.COMMAND_PLOT_CLAIM_SUCCESS);
            }
            
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/plot claim - Plot kaufen");
    }
    
}
