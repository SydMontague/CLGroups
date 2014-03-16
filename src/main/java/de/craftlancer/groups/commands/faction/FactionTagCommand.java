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

public class FactionTagCommand extends GroupSubCommand
{
    
    public FactionTagCommand(String permission, CLGroups plugin)
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
        if (!f.hasPermission(gp.getName(), "faction.tag"))
            return GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION;
        if (FactionManager.isTagTaken(args[1]))
            return GroupLanguage.COMMAND_FACTION_TAG_TAKEN;
        
        f.setTag(args[1]);
        return GroupLanguage.COMMAND_FACTION_TAG_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction tag <Tag> - setze den Tag deine Fraktion");
    }
    
}
