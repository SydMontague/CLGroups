package de.craftlancer.groups.commands.faction.group;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.Group;
import de.craftlancer.groups.GroupDefaultLists;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class FactionGroupRemovepermCommand extends GroupSubCommand
{
    
    public FactionGroupRemovepermCommand(String permission, CLGroups plugin)
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
            else if (!f.hasPermission(sender.getName(), "faction.group.removeperm"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION);
            else if (args[2].equals("leaders"))
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_PROTECTED);
            else
            {
                Group g = f.getGroup(args[2]);
                for (int i = 3; i < args.length; i++)
                    if (f.hasPermission(sender.getName(), args[i]))
                        g.removePermission(args[i]);
                
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_REMOVEPERM_SUCCESS);
            }
        }
    }
    
    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 3:
                return Utils.getMatches(args[2], PlayerManager.getGroupPlayer(sender.getName()).getFaction().getGroupNames());
            case 4:
                return Utils.getMatches(args[3], PlayerManager.getGroupPlayer(sender.getName()).getFaction().getGroup(args[2]).getPermissions());
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction group removeperm <Rang> <Rechte...> - lösche Rechte von einem Fraktionsrang");
        sender.sendMessage("Mögliche Rechte: " + GroupDefaultLists.PERMLIST);
    }
    
}
