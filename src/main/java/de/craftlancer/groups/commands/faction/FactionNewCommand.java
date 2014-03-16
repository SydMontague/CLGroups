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
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        if (args.length < 3)
            return GroupLanguage.COMMAND_GENERAL_ARGUMENTS;
        
        Player p = (Player) sender;
        Plot plot = PlotManager.getPlot(p.getLocation());
        GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
        
        String fname = args[1];
        String tname = args[2];
        
        if (gp.getFaction() != null)
            return GroupLanguage.COMMAND_FACTION_NEW_FACTION_INFACTION;
        if (PlotManager.checkPlotDistance(plot, p))
            return GroupLanguage.COMMAND_FACTION_NEW_DISTANCE;
        if (TownManager.hasTown(tname))
            return GroupLanguage.COMMAND_FACTION_NEW_TOWNEXISTS;
        if (FactionManager.hasFaction(fname))
            return GroupLanguage.COMMAND_FACTION_NEW_FACTIONEXISTS;
        
        FactionManager.addFaction(new Faction(getPlugin(), fname, p));
        TownManager.addTown(new Town(getPlugin(), PlotManager.getPlot(p.getLocation()), tname, sender.getName(), FactionManager.getFaction(fname)));
        getPlugin().getServer().broadcastMessage(String.format(GroupLanguage.COMMAND_FACTION_NEW_BROADCAST, fname, tname));
        return GroupLanguage.COMMAND_FACTION_NEW_SUCCESS;
        
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction new <Fraktionsname> <Stadtname> - Gründe eine neue Fraktion");
    }
    
}
