package de.craftlancer.groups.questions;

import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.managers.PlayerManager;

public class TownInviteQuestion extends Question
{
    private Town town;
    private String player;
    
    public TownInviteQuestion(CLGroups plugin, CommandSender sender, Town town)
    {
        super(plugin, sender);
        player = sender.getName();
        this.town = town;
    }
    
    @Override
    public void execute()
    {
        if(PlayerManager.getGroupPlayer(player).getTown() != null)
        {
            for(CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_TOWN_INVITE_INTOWNP);
            town.sendMessage(String.format(GroupLanguage.QUESTION_TOWN_INVITE_INTOWNT, player));
            return;
        }
        
        town.sendMessage(String.format(GroupLanguage.QUESTION_TOWN_INVITE_JOINED, player));
        town.addMember(player);
        for(CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_TOWN_INVITE_ACCEPTED);
    }
    
    @Override
    public void cancel()
    {
        for(CommandSender sender : getSender())
            sender.sendMessage(GroupLanguage.QUESTION_TOWN_INVITE_CANCELLED);
        town.sendMessage(player + " hat die Einladung abgelehnt.");
    }
    
    @Override
    public void ask()
    {
        for (CommandSender sender : getSender())
            sender.sendMessage(String.format(GroupLanguage.QUESTION_TOWN_INVITE_QUESTION, town.getName()));
    }
    
}
