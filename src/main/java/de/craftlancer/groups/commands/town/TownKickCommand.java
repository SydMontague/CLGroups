package de.craftlancer.groups.commands.town;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;

public class TownKickCommand extends GroupSubCommand
{
    
    public TownKickCommand(String permission, CLGroups plugin)
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
        else if (!getPlugin().getServer().getOfflinePlayer(args[1]).hasPlayedBefore())
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOPLAYER);
        else if (sender.getName().equalsIgnoreCase(args[1]))
            sender.sendMessage(GroupLanguage.COMMAND_TOWN_KICK_SELF);
        else
            for (int i = 1; i < args.length; i++)
            {
                GroupPlayer gp = getPlugin().getGroupPlayer(args[i]);
                OfflinePlayer p = getPlugin().getServer().getOfflinePlayer(args[i]);
                Town town = getPlugin().getGroupPlayer(sender.getName()).getTown();
                
                if (town == null || !town.equals(gp.getTown()))
                    sender.sendMessage(GroupLanguage.COMMAND_TOWN_KICK_NOTINTOWN);
                else if (!town.hasPermission(sender.getName(), "town.kick"))
                    sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
                else
                {
                    town.removeMember(args[i]);
                    sender.sendMessage(String.format(GroupLanguage.COMMAND_TOWN_KICK_SUCCESS, p.getName()));
                    if (p.isOnline())
                        p.getPlayer().sendMessage(GroupLanguage.COMMAND_TOWN_KICK_KICKED);
                    town.sendMessage(String.format(GroupLanguage.COMMAND_TOWN_KICK_BROADCASTKICKED, p.getName(), sender.getName()));
                }
            }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town kick <Spieler> - wirf einen Spieler aus der Stadt");
    }
    
}
