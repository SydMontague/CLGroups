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

public class FactionInfoCommand extends GroupSubCommand
{
    
    public FactionInfoCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, true);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 2 && !(sender instanceof Player))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else if (args.length >= 2 && !getPlugin().hasFaction(args[1]))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOFACTION);
        else
        {
            Faction f;
            
            if (args.length >= 2)
                f = getPlugin().getFaction(args[1]);
            else
                f = getPlugin().getPlayerGroup(sender.getName());
            
            if (f == null)
            {
                sender.sendMessage("Du bist in keiner Fraktion!");
                return;
            }
            
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
            
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 2:
                return Utils.getMatches(args[1], getPlugin().getFactionNames());
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
