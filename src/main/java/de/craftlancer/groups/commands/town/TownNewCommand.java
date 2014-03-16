package de.craftlancer.groups.commands.town;

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
import de.craftlancer.groups.managers.PlayerManager;
import de.craftlancer.groups.managers.PlotManager;
import de.craftlancer.groups.managers.TownManager;

public class TownNewCommand extends GroupSubCommand
{
    
    public TownNewCommand(String permission, CLGroups plugin)
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
        GroupPlayer gp = PlayerManager.getGroupPlayer(p.getName());
        Town town = gp.getTown();
        
        if (town == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINTOWN;
        if (town.getMayors().size() == 1 && town.getMayors().contains(sender.getName()))
            return GroupLanguage.COMMAND_TOWN_LEAVE_LASTMAYOR;
        if (!town.getFaction().hasPermission(sender.getName(), "faction.newtown"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        if (PlotManager.checkPlotDistance(plot, p))
            return GroupLanguage.COMMAND_TOWN_NEW_DISTANCE;
        if (TownManager.hasTown(args[1]))
            return GroupLanguage.COMMAND_TOWN_NEW_EXISTS;
        
        Faction fac = town.getFaction();
        town.removeMember(sender.getName());
        
        TownManager.addTown(new Town(getPlugin(), plot, args[1], sender.getName(), fac));
        
        town.sendMessage(String.format(GroupLanguage.COMMAND_TOWN_LEAVE_LEFT, sender.getName()));
        getPlugin().getServer().broadcastMessage(String.format(GroupLanguage.COMMAND_TOWN_NEW_BROADCAST, args[1], fac.getName()));
        return GroupLanguage.COMMAND_TOWN_NEW_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town new <Name> - Erstellt eine neue Stadt");
    }
    
}
