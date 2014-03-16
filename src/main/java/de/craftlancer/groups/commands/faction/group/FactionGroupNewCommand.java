package de.craftlancer.groups.commands.faction.group;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.Group;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class FactionGroupNewCommand extends GroupSubCommand
{
    
    public FactionGroupNewCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        if (args.length < 3)
            return GroupLanguage.COMMAND_GENERAL_ARGUMENTS;
        
        GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
        Faction f = gp.getFaction();
        
        if (f == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINFACTION;
        if (f.hasGroup(args[2]))
            return GroupLanguage.COMMAND_GROUP_EXISTS;
        if (!f.hasPermission(sender.getName(), "faction.group.new"))
            return GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION;
        
        f.addGroup(args[2], new Group(getPlugin(), args[2], f));
        return GroupLanguage.COMMAND_GROUP_NEW_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction group new <Rang> - erstelle einen neuen Fraktionsrang");
    }
    
}
