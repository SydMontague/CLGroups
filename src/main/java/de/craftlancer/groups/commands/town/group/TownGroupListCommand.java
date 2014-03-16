package de.craftlancer.groups.commands.town.group;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class TownGroupListCommand extends GroupSubCommand
{
    
    public TownGroupListCommand(String permission, CLGroups plugin)
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
        
        Town town = PlayerManager.getGroupPlayer(sender.getName()).getTown();
        
        if (town == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINTOWN;
        if (!town.hasPermission(sender.getName(), "town.group.list"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        
        sender.sendMessage(GroupLanguage.COMMAND_GROUP_LIST_HEADER);
        StringBuilder str = new StringBuilder();
        for (String g : town.getGroupNames())
            str.append(g + ", ");
        if (str.length() > 2)
            str.delete(str.length() - 2, str.length());
        return str.toString();
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town group list - zeige alle Stadtränge an");
    }
    
}
