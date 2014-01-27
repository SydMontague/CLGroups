package de.craftlancer.groups.commands.town;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class TownLoginCommand extends GroupSubCommand
{
    
    public TownLoginCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 2)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else
        {
            GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
            Town town = gp.getTown();
            if (town == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINTOWN);
            else if (!town.hasPermission(gp.getName(), "town.login"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else
            {
                town.setLoginMsg(args[1]);
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_LOGIN_SUCCESS);
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town login <Nachricht> - setz die Loginnachricht.");
    }
    
}
