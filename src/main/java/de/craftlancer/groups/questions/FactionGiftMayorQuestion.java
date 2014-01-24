package de.craftlancer.groups.questions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;

public class FactionGiftMayorQuestion extends Question
{
    private Town town;
    private Faction faction;
    private Set<CommandSender> subs;
    
    public FactionGiftMayorQuestion(Set<CommandSender> sender, Town town, Faction faction, Set<CommandSender> subs)
    {
        super(sender);
        this.town = town;
        this.faction = faction;
        this.subs = subs;
    }

    @Override
    public void ask()
    {
        for(CommandSender sender : getSender())
            sender.sendMessage(String.format(GroupLanguage.QUESTION_FACTION_GIFT_MAYOR_QUESTION, town.getName(), faction.getName()));
    }
    
    @Override
    public void execute()
    {
        Set<CommandSender> senderSet = new HashSet<CommandSender>();
        for (String s : faction.getLeaders())
        {
            if (s.equalsIgnoreCase("Console"))
                senderSet.add(Bukkit.getConsoleSender());
            
            OfflinePlayer p = Bukkit.getOfflinePlayer(s);
            if (p.isOnline())
                senderSet.add(p.getPlayer());
        }
        
        for(CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_FACTION_GIFT_MAYOR_NEXT);        
        QuestionListener.addQuestion(new FactionGiftOtherQuestion(senderSet, town, faction, getSender(), subs));
    }
    
    @Override
    public void cancel()
    {
        for(CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_FACTION_GIFT_MAYOR_CANCELLED);
        for(CommandSender sender : subs)
            sender.sendMessage(GroupLanguage.QUESTION_FACTION_GIFT_MAYOR_CANCELLED_LEADER);
    }
    
}
