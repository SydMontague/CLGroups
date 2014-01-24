package de.craftlancer.groups.commands.town.group;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Group;
import de.craftlancer.groups.GroupDefaultLists;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;

public class TownGroupRemovepermCommand extends GroupSubCommand
{
    
    public TownGroupRemovepermCommand(String permission, CLGroups plugin)
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
            Town town = getPlugin().getGroupPlayer(sender.getName()).getTown();
            
            if (town == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINTOWN);
            else if (!town.hasGroup(args[2]))
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_NOSUCHGROUP);
            else if (!town.hasPermission(sender.getName(), "town.group.removeperm"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else if (args[2].equals("mayors"))
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_PROTECTED);
            else
            {
                Group g = town.getGroup(args[2]);
                for (int i = 3; i < args.length; i++)
                    if (town.hasPermission(sender.getName(), args[i]))
                        g.removePermission(args[i]);
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_REMOVEPERM_SUCCESS);
            }
        }
    }
    
    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 3:
                return Utils.getMatches(args[2], getPlugin().getGroupPlayer(sender.getName()).getTown().getGroupNames());
            case 4:
                return Utils.getMatches(args[3], getPlugin().getGroupPlayer(sender.getName()).getTown().getGroup(args[2]).getPermissions());
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town group removeperm <Rang> <Rechte...> - lösche Rechte von einem Stadtrang");
        sender.sendMessage("Mögliche Rechte: " + GroupDefaultLists.PERMLIST);
    }
    
}
