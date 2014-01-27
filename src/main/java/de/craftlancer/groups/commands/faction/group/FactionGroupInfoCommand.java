package de.craftlancer.groups.commands.faction.group;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.Group;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class FactionGroupInfoCommand extends GroupSubCommand
{
    
    public FactionGroupInfoCommand(String permission, CLGroups plugin)
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
            GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
            Faction f = gp.getFaction();
            
            if (f == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINFACTION);
            else if (!f.hasGroup(args[2]))
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_NOSUCHGROUP);
            else if (!f.hasPermission(sender.getName(), "faction.group.info"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION);
            else
            {
                Group g = f.getGroup(args[2]);
                
                StringBuilder member = new StringBuilder();
                StringBuilder permissions = new StringBuilder();
                
                for (String s : g.getMember())
                    member.append(s + ", ");
                for (String s : g.getPermissions())
                    permissions.append(s + ", ");
                if (member.length() > 2)
                    member.delete(member.length() - 2, member.length());
                if (permissions.length() > 2)
                    permissions.delete(permissions.length() - 2, permissions.length());
                
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_INFO_HEADER);
                sender.sendMessage("Name: " + g.getName());
                sender.sendMessage("Mitglieder: " + member.toString());
                sender.sendMessage("Permissions: " + permissions.toString());
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
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction group info <Rang> - Infos über einen Fraktionsrang");
    }
    
}
