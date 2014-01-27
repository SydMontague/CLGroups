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
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 2)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else
        {
            Player p = (Player) sender;
            Plot plot = PlotManager.getPlot(p.getLocation());
            //int price;
            try
            {
                //price = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e)
            {
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTANUMBER);
                return;
            }
            
            if (!plot.isOwner(p))
                sender.sendMessage(GroupLanguage.COMMAND_PLOT_NOTOWNER);
            else
            {
                //plot.setForSale(true, price);
                sender.sendMessage(GroupLanguage.COMMAND_PLOT_SALE_SUCCESS);
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/plot sale <Preis> - Setzt den Plot zum Verkauf");
        sender.sendMessage("Preis ist momentan ohne Funktion!");
    }
    
}
