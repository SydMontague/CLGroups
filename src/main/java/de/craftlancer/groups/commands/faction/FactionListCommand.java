package de.craftlancer.groups.commands.faction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.FactionManager;

public class FactionListCommand extends GroupSubCommand
{
    
    public FactionListCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, true);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player && !sender.hasPermission(getPermission()))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        
        sender.sendMessage(GroupLanguage.COMMAND_FACTION_LIST_HEADER);
        StringBuilder str = new StringBuilder();
        for (String g : FactionManager.getFactionNames())
            str.append(g + ", ");
        if (str.length() > 2)
            str.delete(str.length() - 2, str.length());
        
        return str.toString();
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction list - Liste aller Fraktionen");
    }
    
}
