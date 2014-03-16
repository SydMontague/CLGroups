package de.craftlancer.groups.commands.plot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlotManager;

@Deprecated
public class PlotSaleCommand extends GroupSubCommand
{
    
    public PlotSaleCommand(String permission, CLGroups plugin)
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
        
        Player p = (Player) sender;
        Plot plot = PlotManager.getPlot(p.getLocation());
        // int price;
        try
        {
            // price = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e)
        {
            return GroupLanguage.COMMAND_GENERAL_NOTANUMBER;
        }
        
        if (!plot.isOwner(p))
            return GroupLanguage.COMMAND_PLOT_NOTOWNER;
        
        // plot.setForSale(true, price);
        return GroupLanguage.COMMAND_PLOT_SALE_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/plot sale <Preis> - Setzt den Plot zum Verkauf");
        sender.sendMessage("Preis ist momentan ohne Funktion!");
    }
    
}
