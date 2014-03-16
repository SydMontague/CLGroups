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
public class PlotUnsaleCommand extends GroupSubCommand
{
    
    public PlotUnsaleCommand(String permission, CLGroups plugin)
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
        
        if (!plot.isOwner(p))
            return GroupLanguage.COMMAND_PLOT_NOTOWNER;
        
        // plot.setForSale(false, 0);
        return GroupLanguage.COMMAND_PLOT_UNSALE_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/plot unsale - Entfernt den Verkaufsstatus vom Plot");
    }
}
