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
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 3)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else if (!getPlugin().getServer().getOfflinePlayer(args[2]).hasPlayedBefore())
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOPLAYER);
        else
        {
            Player p = (Player) sender;
            OfflinePlayer p2 = getPlugin().getServer().getOfflinePlayer(args[2]);
            Plot plot = PlotManager.getPlot(p.getLocation());
            
            if (plot.getTown() == null || plot.getOwner() != null)
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_NOT_OWN);
            else if (!plot.getTown().hasMember(args[2]))
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_ASSIGN_NOTINTOWN);
            else if (!plot.getTown().hasPermission(p.getName(), "town.plot.assign"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else
            {
                plot.setOwner(p2.getName());
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_ASSIGN_SUCCESS);
                if (p2.isOnline())
                    p2.getPlayer().sendMessage(String.format(GroupLanguage.COMMAND_TOWN_PLOT_ASSIGN_ASSIGNED, plot.getPosiString()));
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town plot assign <Spieler> - Setzt einen Plotbesitzer fest");
    }
    
}
