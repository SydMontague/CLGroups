package de.craftlancer.groups.commands.town;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class TownKickCommand extends GroupSubCommand
{
    
    public TownKickCommand(String permission, CLGroups plugin)
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
        if (!getPlugin().getServer().getOfflinePlayer(args[1]).hasPlayedBefore())
            return GroupLanguage.COMMAND_GENERAL_NOPLAYER;
        if (sender.getName().equalsIgnoreCase(args[1]))
            return GroupLanguage.COMMAND_TOWN_KICK_SELF;
        
        Town town = PlayerManager.getGroupPlayer(sender.getName()).getTown();
        
        if (town == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINTOWN;
        if (!town.hasPermission(sender.getName(), "town.kick"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        
        for (int i = 1; i < args.length; i++)
        {
            GroupPlayer gp = PlayerManager.getGroupPlayer(args[i]);
            OfflinePlayer p = getPlugin().getServer().getOfflinePlayer(args[i]);
            
            if (!town.equals(gp.getTown()))
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_KICK_NOTINTOWN);
            else
            {
                town.removeMember(args[i]);
                sender.sendMessage(String.format(GroupLanguage.COMMAND_TOWN_KICK_SUCCESS, p.getName()));
                if (p.isOnline())
                    p.getPlayer().sendMessage(GroupLanguage.COMMAND_TOWN_KICK_KICKED);
                town.sendMessage(String.format(GroupLanguage.COMMAND_TOWN_KICK_BROADCASTKICKED, p.getName(), sender.getName()));
            }
        }
        return null;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town kick <Spieler> - wirf einen Spieler aus der Stadt");
    }
    
}
