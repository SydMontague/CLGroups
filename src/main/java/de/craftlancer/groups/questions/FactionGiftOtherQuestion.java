package de.craftlancer.groups.questions;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;

public class FactionGiftOtherQuestion extends Question
{
    private Town town;
    private Faction faction;
    private Set<CommandSender> subs;
    
    public FactionGiftOtherQuestion(Set<CommandSender> senderSet, Town town, Faction faction, Set<CommandSender> subs1, Set<CommandSender> subs2)
    {
        super(senderSet);
        this.town = town;
        this.faction = faction;
        this.subs = subs1;
        subs.addAll(subs2);
    }
    
    @Override
    public void ask()
    {
        for (CommandSender sender : getSender())
            sender.sendMessage(String.format(GroupLanguage.QUESTION_FACTION_GIFT_OTHER_QUESTION, town.getName(), town.getFaction().getName()));
    }
    
    @Override
    public void execute()
    {
        town.setFaction(faction);
        Bukkit.broadcastMessage(String.format(GroupLanguage.QUESTION_FACTION_GIFT_SUCCESS, town.getName(), town.getFaction().getName(), faction.getName()));
    }
    
    @Override
    public void cancel()
    {
        for (CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_FACTION_GIFT_OTHER_CANCELLED);
        for (CommandSender sender : subs)
            sender.sendMessage(String.format(GroupLanguage.QUESTION_FACTION_GIFT_OTHER_CANCELLED_SUBS, faction.getName()));
    }
    
}
