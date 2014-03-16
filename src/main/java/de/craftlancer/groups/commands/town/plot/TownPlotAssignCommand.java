package de.craftlancer.groups.commands.town.plot;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlotManager;

public class TownPlotAssignCommand extends GroupSubCommand
{
    
    public TownPlotAssignCommand(String permission, CLGroups plugin)
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
        if (!getPlugin().getServer().getOfflinePlayer(args[2]).hasPlayedBefore())
            return GroupLanguage.COMMAND_GENERAL_NOPLAYER;
        
        Player p = (Player) sender;
        OfflinePlayer p2 = getPlugin().getServer().getOfflinePlayer(args[2]);
        Plot plot = PlotManager.getPlot(p.getLocation());
        
        if (plot.getTown() == null || plot.getOwner() != null)
            return GroupLanguage.COMMAND_TOWN_PLOT_NOT_OWN;
        if (!plot.getTown().hasMember(args[2]))
            return GroupLanguage.COMMAND_TOWN_PLOT_ASSIGN_NOTINTOWN;
        if (!plot.getTown().hasPermission(p.getName(), "town.plot.assign"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        
        plot.setOwner(p2.getName());
        if (p2.isOnline())
            p2.getPlayer().sendMessage(String.format(GroupLanguage.COMMAND_TOWN_PLOT_ASSIGN_ASSIGNED, plot.getPosiString()));
        
        return GroupLanguage.COMMAND_TOWN_PLOT_ASSIGN_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town plot assign <Spieler> - Setzt einen Plotbesitzer fest");
    }
    
}
