package de.craftlancer.groups.commands.faction.group;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.Group;
import de.craftlancer.groups.GroupDefaultLists;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class FactionGroupAddPermCommand extends GroupSubCommand
{
    
    public FactionGroupAddPermCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        if (args.length < 4)
            return GroupLanguage.COMMAND_GENERAL_ARGUMENTS;
        
        GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
        Faction f = gp.getFaction();
        
        if (f == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINFACTION;
        if (!f.hasGroup(args[2]))
            return GroupLanguage.COMMAND_GROUP_NOSUCHGROUP;
        if (!f.hasPermission(sender.getName(), "faction.group.addperm"))
            return GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION;
        if (args[2].equals("leaders"))
            return GroupLanguage.COMMAND_GROUP_PROTECTED;
        
        Group g = f.getGroup(args[2]);
        for (int i = 3; i < args.length; i++)
            if (f.hasPermission(sender.getName(), args[i]))
                g.addPermission(args[i]);
        
        return GroupLanguage.COMMAND_GROUP_ADDPERM_SUCCESS;
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
            case 4:
                return Utils.getMatches(args[3], GroupDefaultLists.PERMLIST);
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction group addperm <Rang> <Rechte...> - füge einem Fraktionsrang Rechte hinzu");
        sender.sendMessage("Mögliche Rechte: " + GroupDefaultLists.PERMLIST);
    }
    
}
