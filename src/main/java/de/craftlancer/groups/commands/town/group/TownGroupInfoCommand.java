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
import de.craftlancer.groups.managers.PlayerManager;

public class TownGroupInfoCommand extends GroupSubCommand
{
    
    public TownGroupInfoCommand(String permission, CLGroups plugin)
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
        
        Town town = PlayerManager.getGroupPlayer(sender.getName()).getTown();
        
        if (town == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINTOWN;
        if (!town.hasGroup(args[2]))
            return GroupLanguage.COMMAND_GROUP_NOSUCHGROUP;
        if (!town.hasPermission(sender.getName(), "town.group.info"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        
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
        return null;
    }
    
    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args)
    {
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
        sender.sendMessage("/town group info <Rang> - Infos über einen Stadtrang");
    }
    
}
