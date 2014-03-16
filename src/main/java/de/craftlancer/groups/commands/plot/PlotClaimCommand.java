package de.craftlancer.groups.commands.plot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlotManager;

public class PlotClaimCommand extends GroupSubCommand
{
    // TODO economy implementation
    public PlotClaimCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        
        Player p = (Player) sender;
        Plot plot = PlotManager.getPlot(p.getLocation());
        
        if (!plot.isBuyable())
            return GroupLanguage.COMMAND_PLOT_CLAIM_NOTFORSALE;
        if (!plot.isTownPlot() && PlotManager.checkPlotDistance(plot, p))
            return GroupLanguage.COMMAND_PLOT_CLAIM_TOOCLOSE;
        if (plot.isTownPlot() && !plot.getTown().hasPermission(p.getName(), "town.buy"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        if (PlotManager.checkPlotLimit(p))
            return GroupLanguage.COMMAND_PLOT_CLAIM_LIMIT;
        
        plot.setOwner(p.getName());
        return GroupLanguage.COMMAND_PLOT_CLAIM_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/plot claim - Plot kaufen");
    }
    
}
