package de.craftlancer.groups.commands.town;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.TownManager;

public class TownListCommand extends GroupSubCommand
{
    
    public TownListCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, true);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        
        sender.sendMessage(GroupLanguage.COMMAND_TOWN_LIST_HEADER);
        StringBuilder str = new StringBuilder();
        for (String g : TownManager.getTownNames())
            str.append(g + ", ");
        if (str.length() > 2)
            str.delete(str.length() - 2, str.length());
        return str.toString();
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town list - Liste aller Städte");
    }
    
}
