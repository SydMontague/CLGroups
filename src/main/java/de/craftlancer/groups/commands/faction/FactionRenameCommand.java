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
        if (!f.hasPermission(gp.getName(), "faction.rename"))
            return GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION;
        if (FactionManager.hasFaction(args[1]))
            return GroupLanguage.COMMAND_FACTION_RENAME_EXISTS;
        
        getPlugin().getServer().broadcastMessage(String.format(GroupLanguage.COMMAND_FACTION_RENAME_BROADCAST, f.getName(), args[1]));
        f.rename(args[1]);
        return GroupLanguage.COMMAND_FACTION_RENAME_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction rename <Name> - Benennt deine Fraktion in <Name> um.");
    }
    
}
