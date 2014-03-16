package de.craftlancer.groups.questions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;

public class TownDisbandQuestion extends Question
{
    private Town town;
    
    public TownDisbandQuestion(CLGroups plugin, CommandSender sender, Town town)
    {
        super(plugin, sender);
        this.town = town;
    }
    
    @Override
    public void execute()
    {
        town.disband();
        for (CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_TOWN_DISBAND_SUCCESS);
        Bukkit.broadcastMessage(String.format(GroupLanguage.QUESTION_TOWN_DISBAND_BROADCAST, town.getName()));
    }
    
    @Override
    public void cancel()
    {
        for (CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_TOWN_DISBAND_CANCELLED);
    }
    
    @Override
    public void ask()
    {
        for (CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_TOWN_DISBAND_QUESTION);
    }
    
}
