package de.craftlancer.groups.commands.town;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class TownFarewellCommand extends GroupSubCommand
{
    
    public TownFarewellCommand(String permission, CLGroups plugin)
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
        if (!town.hasPermission(gp.getName(), "town.farewell"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        
        town.setFarewellMsg(args[1]);
        return GroupLanguage.COMMAND_TOWN_FAREWELL_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town farewell <Nachricht> - setz die Abschiedsnachricht.");
    }
    
}
