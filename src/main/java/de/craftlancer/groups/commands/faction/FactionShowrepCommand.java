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

public class FactionShowrepCommand extends GroupSubCommand
{
    
    public FactionShowrepCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(checkSender(sender)))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else
        {
            GroupPlayer gp = getPlugin().getGroupPlayer(sender.getName());
            Repuable rep;
            StringBuilder str = new StringBuilder();
            for (int i = 1; i < args.length; i++)
                str.append(args[i] + " ");
            if (str.length() > 1)
                str.delete(str.length() - 1, str.length());
            String name = str.toString();
            
            if (args.length >= 2)
                if (name.startsWith("p:"))
                    rep = getPlugin().getGroupPlayer(name.substring(2));
                else if (name.startsWith("f:"))
                    rep = getPlugin().getFaction(name.substring(2));
                else
                    rep = getPlugin().hasFaction(name) ? getPlugin().getFaction(name) : getPlugin().getGroupPlayer(name);
            else
                rep = gp;
            
            sender.sendMessage(String.format(GroupLanguage.COMMAND_FACTION_SHOWREP_HEADER, rep.getName()));
            for (Faction ff : getPlugin().getFactions())
            {
                if (rep instanceof Faction && ff.getName().equals(rep.getName()))
                    continue;
                
                Faction f2 = rep instanceof Faction ? (Faction) rep : ((GroupPlayer) rep).getFaction();
                
                Reputation rep1 = ff.getReputation(f2);
                Reputation rep2 = f2 != null ? f2.getReputation(ff) : Reputation.UNDEFINED;
                
                Reputation repu = rep1.getLowest(rep2);
                
                sender.sendMessage(String.format(GroupLanguage.COMMAND_FACTION_SHOWREP_CONTENT, rep.getName(), ff.getName(), repu.toString()));
            }
            
        }
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
                tmp.addAll(Utils.getMatches(args[1], getPlugin().getFactionNames()));
                return tmp;
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction showrep <Spieler/Fraktion> - zeige die Reputation eines Spieler/einer Fraktion");
    }
    
}
