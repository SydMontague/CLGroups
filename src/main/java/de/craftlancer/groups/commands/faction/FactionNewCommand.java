package de.craftlancer.groups.commands.faction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.FactionManager;
import de.craftlancer.groups.managers.PlayerManager;
import de.craftlancer.groups.managers.PlotManager;
import de.craftlancer.groups.managers.TownManager;

public class FactionNewCommand extends GroupSubCommand
{
    
    public FactionNewCommand(String permission, CLGroups plugin)
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
            GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
            
            String fname = args[1];
            String tname = args[2];
            
            if (gp.getFaction() != null)
                sender.sendMessage(GroupLanguage.COMMAND_FACTION_NEW_FACTION_INFACTION);
            else if (PlotManager.checkPlotDistance(plot, p))
                sender.sendMessage(GroupLanguage.COMMAND_FACTION_NEW_DISTANCE);
            else if (TownManager.hasTown(tname))
                sender.sendMessage(GroupLanguage.COMMAND_FACTION_NEW_TOWNEXISTS);
            else if (FactionManager.hasFaction(fname))
                sender.sendMessage(GroupLanguage.COMMAND_FACTION_NEW_FACTIONEXISTS);
            else
            {
                FactionManager.addFaction(new Faction(getPlugin(), fname, p));
                TownManager.addTown(new Town(getPlugin(), PlotManager.getPlot(p.getLocation()), tname, sender.getName(), FactionManager.getFaction(fname)));
                sender.sendMessage(GroupLanguage.COMMAND_FACTION_NEW_SUCCESS);
                getPlugin().getServer().broadcastMessage(String.format(GroupLanguage.COMMAND_FACTION_NEW_BROADCAST, fname, tname));
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction new <Fraktionsname> <Stadtname> - Gründe eine neue Fraktion");
    }
    
}
