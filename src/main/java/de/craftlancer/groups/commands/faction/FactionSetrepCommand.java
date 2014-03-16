package de.craftlancer.groups.commands.faction;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Repuable;
import de.craftlancer.groups.Reputation;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.FactionManager;
import de.craftlancer.groups.managers.PlayerManager;

public class FactionSetrepCommand extends GroupSubCommand
{
    
    public FactionSetrepCommand(String permission, CLGroups plugin)
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
        if (!Reputation.getNames().contains(args[args.length - 1].toUpperCase()))
            return GroupLanguage.COMMAND_FACTION_NOTAREPU;
        
        GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
        Faction f = gp.getFaction();
        Reputation repu = Reputation.valueOf(args[args.length - 1].toUpperCase());
        
        if (f == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINFACTION;
        if (!f.hasPermission(sender.getName(), "faction.setrep"))
            return GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION;
        
        Repuable repuable;
        
        if (args[1].startsWith("p:"))
            repuable = PlayerManager.getGroupPlayer(args[1].substring(2));
        else if (args[1].startsWith("f:"))
            repuable = FactionManager.getFaction(args[1].substring(2));
        else
            repuable = FactionManager.hasFaction(args[1]) ? FactionManager.getFaction(args[1]) : PlayerManager.getGroupPlayer(args[1]);
        
        if (repuable == null)
            return GroupLanguage.COMMAND_GENERAL_NOFACTION;
        
        f.setReputation(repuable, repu);
        String type = repuable instanceof Faction ? "Fraktion" : "Spieler";
        
        return String.format(GroupLanguage.COMMAND_FACTION_SETREP_SUCCESS, type, repuable.getName());
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 2:
                List<String> tmp = new LinkedList<String>();
                for (Player op : getPlugin().getServer().getOnlinePlayers())
                    if (op.getName().startsWith(args[1]))
                        tmp.add(op.getName());
                tmp.addAll(Utils.getMatches(args[1], FactionManager.getFactionNames()));
                return tmp;
            case 3:
                return Utils.getMatches(args[2].toUpperCase(), Reputation.getNames());
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction setrep <Fraktion/Spieler> <Reputation> - Setz die Reputation zu einem Spieler/einer Fraktion");
        sender.sendMessage("Mögliche Reputationen: WAR, ENEMY, NEUTRAL, FRIEND, ALLIED");
    }
}
