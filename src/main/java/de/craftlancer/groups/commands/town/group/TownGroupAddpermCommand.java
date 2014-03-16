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
import de.craftlancer.groups.managers.PlayerManager;

public class TownGroupAddpermCommand extends GroupSubCommand
{
    
    public TownGroupAddpermCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        if (args.length < 4)
            return GroupLanguage.COMMAND_GENERAL_ARGUMENTS;
        
        Town town = PlayerManager.getGroupPlayer(sender.getName()).getTown();
        
        if (town == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINTOWN;
        if (!town.hasGroup(args[2]))
            return GroupLanguage.COMMAND_GROUP_NOSUCHGROUP;
        if (!town.hasPermission(sender.getName(), "town.group.addperm"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        if (args[2].equals("mayors") || args[2].equals("members"))
            return GroupLanguage.COMMAND_GROUP_PROTECTED;
        
        Group g = town.getGroup(args[2]);
        for (int i = 3; i < args.length; i++)
            if (town.hasPermission(sender.getName(), args[i]))
                g.addPermission(args[i]);
        
        return GroupLanguage.COMMAND_GROUP_ADDPERM_SUCCESS;
    }
    
    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 3:
                return Utils.getMatches(args[2], PlayerManager.getGroupPlayer(sender.getName()).getTown().getGroupNames());
            case 4:
                return Utils.getMatches(args[3], GroupDefaultLists.PERMLIST);
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town group addperm <Rang> <Rechte...> - füge einem Stadtrang Rechte hinzu");
    }
    
}
