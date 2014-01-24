package de.craftlancer.groups.commands.town;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;

public class TownLeaveCommand extends GroupSubCommand
{
    
    public TownLeaveCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else
        {
            Town town = getPlugin().getGroupPlayer(sender.getName()).getTown();
            
            if (town == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINTOWN);
            else if (town.getMayors().size() == 1 && town.getMayors().contains(sender.getName()))
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_LEAVE_LASTMAYOR);
            else
            {
                town.removeMember(sender.getName());
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_LEAVE_SUCCESS);
                town.sendMessage(String.format(GroupLanguage.COMMAND_TOWN_LEAVE_LEFT, sender.getName()));
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town leave - Stadt verlassen, Löscht alle eigenen Plots");
    }
    
}
