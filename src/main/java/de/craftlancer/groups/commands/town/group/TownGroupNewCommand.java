package de.craftlancer.groups.commands.town.group;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Group;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class TownGroupNewCommand extends GroupSubCommand
{
    
    public TownGroupNewCommand(String permission, CLGroups plugin)
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
        if (town.hasGroup(args[2]))
            return GroupLanguage.COMMAND_GROUP_EXISTS;
        if (!town.hasPermission(sender.getName(), "town.group.new"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        
        town.addGroup(args[2], new Group(getPlugin(), args[2], town));
        return GroupLanguage.COMMAND_GROUP_NEW_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town group new <Rang> - erstelle einen neuen Stadtrang");
    }
    
}
