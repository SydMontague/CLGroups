package de.craftlancer.groups.commands.faction.group;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class FactionGroupListCommand extends GroupSubCommand
{
    
    public FactionGroupListCommand(String permission, CLGroups plugin)
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
        Faction f = gp.getFaction();
        
        if (f == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINFACTION;
        if (!f.hasPermission(sender.getName(), "faction.group.list"))
            return GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION;
        
        sender.sendMessage(GroupLanguage.COMMAND_GROUP_LIST_HEADER);
        StringBuilder str = new StringBuilder();
        for (String g : f.getGroupNames())
            str.append(g + ", ");
        if (str.length() > 2)
            str.delete(str.length() - 2, str.length());
        
        return str.toString();
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction group list - zeige alle Fraktionsränge an");
    }
    
}
