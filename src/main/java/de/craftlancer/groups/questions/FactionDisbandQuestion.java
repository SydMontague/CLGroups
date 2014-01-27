package de.craftlancer.groups.questions;

import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;

public class FactionDisbandQuestion extends Question
{
    private Faction faction;
    
    public FactionDisbandQuestion(CLGroups plugin, CommandSender sender, Faction faction)
    {
        super(plugin, sender);
        this.faction = faction;
    }
    
    @Override
    public void execute()
    {
        faction.disband();
        for (CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_FACTION_DISBAND_SUCCESS);
        getPlugin().getServer().broadcastMessage(String.format(GroupLanguage.QUESTION_FACTION_DISBAND_BROADCAST, faction.getName()));
    }
    
    @Override
    public void cancel()
    {
        for (CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_FACTION_DISBAND_CANCELLED);
    }
    
    @Override
    public void ask()
    {
        for (CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_FACTION_DISBAND_QUESTION);
    }
}
