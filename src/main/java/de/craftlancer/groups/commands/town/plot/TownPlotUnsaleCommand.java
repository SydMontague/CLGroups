package de.craftlancer.groups.commands.town.plot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlotManager;

@Deprecated
public class TownPlotUnsaleCommand extends GroupSubCommand
{
    
    public TownPlotUnsaleCommand(String permission, CLGroups plugin)
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
            Plot plot = PlotManager.getPlot(p.getLocation());
            
            if (plot.getTown() == null || plot.getOwner() != null)
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_NOT_OWN);
            else if (!plot.getTown().hasPermission(p.getName(), "town.plot.unsale"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else
            {
                //plot.setForSale(false, 0);
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_UNSALE_SUCCESS);
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town plot unsale - Entfernt ein Plot vom Verkauf");
    }
    
}
