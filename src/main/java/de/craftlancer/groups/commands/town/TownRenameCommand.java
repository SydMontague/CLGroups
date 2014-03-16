package de.craftlancer.groups.commands.town;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;
import de.craftlancer.groups.managers.TownManager;

public class TownRenameCommand extends GroupSubCommand
{
    
    public TownRenameCommand(String permission, CLGroups plugin)
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
        
        GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
        Town town = gp.getTown();
        
        if (town == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINTOWN;
        if (!town.hasPermission(gp.getName(), "town.rename"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        if (TownManager.hasTown(args[1]))
            return GroupLanguage.COMMAND_TOWN_RENAME_EXISTS;
        
        getPlugin().getServer().broadcastMessage(String.format(GroupLanguage.COMMAND_TOWN_RENAME_BROADCAST, town.getName(), args[1]));
        town.rename(args[1]);
        return GroupLanguage.COMMAND_TOWN_RENAME_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town rename <Name> - Benennt deine Stadt in <Name> um.");
    }
    
}
