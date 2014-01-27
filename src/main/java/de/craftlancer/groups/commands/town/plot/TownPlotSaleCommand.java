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
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 3)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else
        {
            Player p = (Player) sender;
            Plot plot = PlotManager.getPlot(p.getLocation());
            //int price;
            
            try
            {
                //price = Integer.parseInt(args[2]);
            }
            catch (NumberFormatException e)
            {
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTANUMBER);
                return;
            }
            
            if (plot.getTown() == null || plot.getOwner() != null)
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_NOT_OWN);
            else if (!plot.getTown().hasPermission(p.getName(), "town.plot.sale"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else
            {
                //plot.setForSale(true, price);
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_SALE_SUCCESS);
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town plot sale <Preis> - Setz ein Plot zum Verkauf");
    }
    
}
