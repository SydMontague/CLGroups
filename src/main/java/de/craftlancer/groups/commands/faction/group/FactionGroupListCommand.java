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
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 2)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else
        {
            GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
            Faction f = gp.getFaction();
            
            if (f == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINFACTION);
            else if (!f.hasPermission(sender.getName(), "faction.group.list"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION);
            else
            {
                sender.sendMessage(GroupLanguage.COMMAND_GROUP_LIST_HEADER);
                StringBuilder str = new StringBuilder();
                for (String g : f.getGroupNames())
                    str.append(g + ", ");
                if (str.length() > 2)
                    str.delete(str.length() - 2, str.length());
                sender.sendMessage(str.toString());
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction group list - zeige alle Fraktionsränge an");
    }
    
}
