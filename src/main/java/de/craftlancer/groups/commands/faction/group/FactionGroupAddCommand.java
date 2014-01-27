package de.craftlancer.groups.commands.faction.group;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.Group;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class FactionGroupAddCommand extends GroupSubCommand
{
    
    public FactionGroupAddCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 4)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else
        {
            GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
            Faction f = gp.getFaction();
            
            if (f == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINFACTION);
            else if (!f.hasGroup(args[2]))
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_NOSUCHGROUP);
            else if (!f.hasPermission(sender.getName(), "faction.group.add"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION);
            else
            {
                Group g = f.getGroup(args[2]);
                for (int i = 3; i < args.length; i++)
                    if (!args[2].equals("leaders") || f.hasMember(args[i]))
                        g.addPlayer(args[i]);
                
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_ADD_SUCCESS);
            }
        }
    }
    
    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args)
    {
        if (!(sender instanceof Player))
            return null;
        switch (args.length)
        {
            case 3:
                return Utils.getMatches(args[2], PlayerManager.getGroupPlayer(sender.getName()).getFaction().getGroupNames());
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction group add <Rang> <Spieler...> - weise einen Spieler einen Fraktionsrang hinzu");
    }
    
}
