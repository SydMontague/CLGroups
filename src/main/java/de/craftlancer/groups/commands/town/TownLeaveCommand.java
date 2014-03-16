package de.craftlancer.groups.commands.town;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class TownLeaveCommand extends GroupSubCommand
{
    
    public TownLeaveCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        
        Town town = PlayerManager.getGroupPlayer(sender.getName()).getTown();
        
        if (town == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINTOWN;
        if (town.getMayors().size() == 1 && town.getMayors().contains(sender.getName()))
            return GroupLanguage.COMMAND_TOWN_LEAVE_LASTMAYOR;
        
        town.removeMember(sender.getName());
        town.sendMessage(String.format(GroupLanguage.COMMAND_TOWN_LEAVE_LEFT, sender.getName()));
        return GroupLanguage.COMMAND_TOWN_LEAVE_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town leave - Stadt verlassen, Löscht alle eigenen Plots");
    }
    
}
