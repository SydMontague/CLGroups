package de.craftlancer.groups.commands.town.plot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlotManager;

public class TownPlotFlagCommand extends GroupSubCommand
{
    
    public TownPlotFlagCommand(String permission, CLGroups plugin)
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
        
        if (plot.getTown() == null || plot.getOwner() != null)
            return GroupLanguage.COMMAND_TOWN_PLOT_NOT_OWN;
        if (!plot.getTown().hasPermission(p.getName(), "town.plot.flag"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        
        plot.setFlag(args[2]);
        return GroupLanguage.COMMAND_TOWN_PLOT_FLAG_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town plot flag <Flag> - Setzt einen Flag für den plot -> town.plot.flag.<Flag>");
    }
    
}
