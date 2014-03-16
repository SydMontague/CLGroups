package de.craftlancer.groups.commands.faction;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.FactionManager;

public class FactionInfoCommand extends GroupSubCommand
{
    
    public FactionInfoCommand(String permission, CLGroups plugin)
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
        if (args.length >= 2 && !FactionManager.hasFaction(args[1]))
            return GroupLanguage.COMMAND_GENERAL_NOFACTION;
        
        Faction f;
        
        if (args.length >= 2)
            f = FactionManager.getFaction(args[1]);
        else
            f = FactionManager.getPlayerGroup(sender.getName());
        
        if (f == null)
            return "Du bist in keiner Fraktion!";
        
        StringBuilder str = new StringBuilder();
        for (String g : f.getTownNames())
            str.append(g + ", ");
        if (str.length() > 2)
            str.delete(str.length() - 2, str.length());
        
        StringBuilder member = new StringBuilder();
        
        for (String g : f.getMember())
            member.append(g + ", ");
        if (member.length() > 2)
            member.delete(member.length() - 2, member.length());
        
        sender.sendMessage(GroupLanguage.COMMAND_FACTION_INFO_HEADER);
        sender.sendMessage("Name: " + f.getName() + "  Tag: " + f.getTag() + "  Color: " + f.getColor() + f.getColor().name());
        sender.sendMessage("No.Towns: " + f.getTowns().size() + "  Members: " + f.getMember().size() + "  DefaultRepu: " + f.getDefaultRepu());
        sender.sendMessage("Towns: " + str.toString());
        sender.sendMessage("Member: " + member.toString());
        return null;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 2:
                return Utils.getMatches(args[1], FactionManager.getFactionNames());
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction info <Fraktion> - Informationen über Fraktion");
    }
    
}
