package de.craftlancer.groups.commands.town.group;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Group;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;

public class TownGroupInfoCommand extends GroupSubCommand
{
    
    public TownGroupInfoCommand(String permission, CLGroups plugin)
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
            Town town = getPlugin().getGroupPlayer(sender.getName()).getTown();
            
            if (town == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINTOWN);
            else if (!town.hasGroup(args[2]))
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_NOSUCHGROUP);
            else if (!town.hasPermission(sender.getName(), "town.group.info"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else
            {
                Group g = town.getGroup(args[2]);
                
                StringBuilder member = new StringBuilder();
                StringBuilder permissions = new StringBuilder();
                
                for (String s : g.getMember())
                    member.append(s + ", ");
                for (String s : g.getPermissions())
                    permissions.append(s + ", ");
                if (member.length() > 2)
                    member.delete(member.length() - 2, member.length());
                if (permissions.length() > 2)
                    permissions.delete(permissions.length() - 2, permissions.length());
                
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_INFO_HEADER);
                sender.sendMessage("Name: " + g.getName());
                sender.sendMessage("Members: " + member.toString());
                sender.sendMessage("Permissions: " + permissions.toString());
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
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town group info <Rang> - Infos über einen Stadtrang");
    }
    
}
