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

public class TownNewCommand extends GroupSubCommand
{
    
    public TownNewCommand(String permission, CLGroups plugin)
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
            Plot plot = getPlugin().getPlot(p.getLocation());
            GroupPlayer gp = getPlugin().getGroupPlayer(p.getName());
            Town town = gp.getTown();
                        
            if (town == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINTOWN);
            else if (town.getMayors().size() == 1 && town.getMayors().contains(sender.getName()))
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_LEAVE_LASTMAYOR);
            else if (!town.getFaction().hasPermission(sender.getName(), "faction.newtown"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else if (getPlugin().checkPlotDistance(plot, p))
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_NEW_DISTANCE);
            else if (getPlugin().hasTown(args[1]))
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_NEW_EXISTS);
            else
            {
                Faction fac = town.getFaction();
                town.removeMember(sender.getName());
                
                getPlugin().addTown(new Town(getPlugin(), plot, args[1], sender.getName(), fac));

                town.sendMessage(String.format(GroupLanguage.COMMAND_TOWN_LEAVE_LEFT, sender.getName()));
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_NEW_SUCCESS);
                getPlugin().getServer().broadcastMessage(String.format(GroupLanguage.COMMAND_TOWN_NEW_BROADCAST, args[1], fac.getName()));
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town new <Name> - Erstellt eine neue Stadt");
    }
    
}
