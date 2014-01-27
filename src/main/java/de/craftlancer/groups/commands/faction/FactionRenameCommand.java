package de.craftlancer.groups.commands.faction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.FactionManager;
import de.craftlancer.groups.managers.PlayerManager;

public class FactionRenameCommand extends GroupSubCommand
{
    
    public FactionRenameCommand(String permission, CLGroups plugin)
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
            else if (!f.hasPermission(gp.getName(), "faction.rename"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION);
            else if (FactionManager.hasFaction(args[1]))
                sender.sendMessage(GroupLanguage.COMMAND_FACTION_RENAME_EXISTS);
            else
            {
                getPlugin().getServer().broadcastMessage(String.format(GroupLanguage.COMMAND_FACTION_RENAME_BROADCAST, f.getName(), args[1]));
                f.rename(args[1]);
                sender.sendMessage(GroupLanguage.COMMAND_FACTION_RENAME_SUCCESS);
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction rename <Name> - Benennt deine Fraktion in <Name> um.");
    }
    
}
