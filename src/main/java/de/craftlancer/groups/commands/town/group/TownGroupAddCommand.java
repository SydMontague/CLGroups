package de.craftlancer.groups.commands.town.group;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Group;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class TownGroupAddCommand extends GroupSubCommand
{
    
    public TownGroupAddCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 4)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else
        {
            Town town = PlayerManager.getGroupPlayer(sender.getName()).getTown();
            
            if (town == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINTOWN);
            else if (!town.hasGroup(args[2]))
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_NOSUCHGROUP);
            else if (!town.hasPermission(sender.getName(), "town.group.add"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else if (args[2].equals("members"))
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_PROTECTED);
            else
            {
                Group g = town.getGroup(args[2]);
                for (int i = 3; i < args.length; i++)
                    if (!args[2].equals("mayor") || town.hasMember(args[i]))
                        g.addPlayer(args[i]);
                
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_ADD_SUCCESS);
            }
        }
    }
    
    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args)
    {
        if (!(sender instanceof Player))
            return null;
        switch (args.length)
        {
            case 3:
                return Utils.getMatches(args[2], PlayerManager.getGroupPlayer(sender.getName()).getTown().getGroupNames());
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town group add <Rang> <Spieler...> - weise einen Spieler einen Stadtrang hinzu");
    }
    
}
