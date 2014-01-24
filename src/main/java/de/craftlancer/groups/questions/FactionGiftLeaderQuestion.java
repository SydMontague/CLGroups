package de.craftlancer.groups.questions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;

public class FactionGiftLeaderQuestion extends Question
{
    private Town town;
    private Faction faction;
    
    public FactionGiftLeaderQuestion(CLGroups plugin, CommandSender sender, Town town, Faction faction)
    {
        super(plugin, sender);
        this.town = town;
        this.faction = faction;
    }
    
    @Override
    public void execute()
    {
        Set<CommandSender> senderSet = new HashSet<CommandSender>();
        for (String s : town.getMayors())
        {
            if (s.equalsIgnoreCase("Console"))
                senderSet.add(Bukkit.getConsoleSender());
            
            OfflinePlayer p = Bukkit.getOfflinePlayer(s);
            if (p.isOnline())
                senderSet.add(p.getPlayer());
        }
        
        for(CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_FACTION_GIFT_LEADER_NEXT);        
        QuestionListener.addQuestion(new FactionGiftMayorQuestion(senderSet, town, faction, getSender()));
    }
    
    @Override
    public void cancel()
    {
        for (CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_FACTION_GIFT_LEADER_CANCELLED);
    }
    
    @Override
    public void ask()
    {
        for (CommandSender sender : getSender())
            sender.sendMessage(String.format(GroupLanguage.QUESTION_FACTION_GIFT_LEADER_QUESTION, town.getName(), faction.getName()));
    }
    
}
