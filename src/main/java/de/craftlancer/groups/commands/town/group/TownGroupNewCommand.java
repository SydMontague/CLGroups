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
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 3)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else
        {
            Town town = PlayerManager.getGroupPlayer(sender.getName()).getTown();
            
            if (town == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINTOWN);
            else if (town.hasGroup(args[2]))
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_EXISTS);
            else if (!town.hasPermission(sender.getName(), "town.group.new"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else
            {
                town.addGroup(args[2], new Group(getPlugin(), args[2], town));
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_NEW_SUCCESS);
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town group new <Rang> - erstelle einen neuen Stadtrang");
    }
    
}
