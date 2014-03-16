package de.craftlancer.groups.commands.town;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;
import de.craftlancer.groups.managers.TownManager;

public class TownInfoCommand extends GroupSubCommand
{
    
    public TownInfoCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, true);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        if (args.length < 2 && !(sender instanceof Player))
            return GroupLanguage.COMMAND_GENERAL_ARGUMENTS;
        if (args.length >= 2 && !TownManager.hasTown(args[1]))
            return GroupLanguage.COMMAND_GENERAL_NOTOWN;
        
        Town town;
        if (args.length >= 2)
            town = TownManager.getTown(args[1]);
        else
            town = PlayerManager.getGroupPlayer(sender.getName()).getTown();
        
        if (town == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINTOWN;
        
        StringBuilder memstr = new StringBuilder();
        StringBuilder maystr = new StringBuilder();
        for (String g : town.getMember())
            memstr.append(g + ", ");
        for (String g : town.getMayors())
            maystr.append(g + ", ");
        if (memstr.length() > 2)
            memstr.delete(memstr.length() - 2, memstr.length());
        if (maystr.length() > 2)
            maystr.delete(maystr.length() - 2, maystr.length());
        
        sender.sendMessage(GroupLanguage.COMMAND_TOWN_INFO_HEADER);
        sender.sendMessage("Name: " + town.getName() + "  Faction: " + town.getFaction().getName());
        sender.sendMessage("No.Plots: " + town.getPlots().size() + "  Members: " + town.getMember().size());
        sender.sendMessage("Member: " + memstr.toString());
        sender.sendMessage("Mayor: " + maystr.toString());
        
        return null;
    }
    
    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 2:
                return Utils.getMatches(args[1], TownManager.getTownNames());
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town info [Stadt] - info of town [Stadt]");
    }
    
}
