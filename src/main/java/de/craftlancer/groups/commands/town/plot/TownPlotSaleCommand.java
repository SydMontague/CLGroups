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
public class TownPlotSaleCommand extends GroupSubCommand
{
    
    public TownPlotSaleCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        if (args.length < 3)
            return GroupLanguage.COMMAND_GENERAL_ARGUMENTS;
        
        Player p = (Player) sender;
        Plot plot = PlotManager.getPlot(p.getLocation());
        // int price;
        
        try
        {
            // price = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e)
        {
            return GroupLanguage.COMMAND_GENERAL_NOTANUMBER;
            
        }
        
        if (plot.getTown() == null || plot.getOwner() != null)
            return GroupLanguage.COMMAND_TOWN_PLOT_NOT_OWN;
        if (!plot.getTown().hasPermission(p.getName(), "town.plot.sale"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        
        // plot.setForSale(true, price);
        return GroupLanguage.COMMAND_TOWN_PLOT_SALE_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town plot sale <Preis> - Setz ein Plot zum Verkauf");
    }
    
}
