package de.craftlancer.groups.commands.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.chat.Channel;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class ChannelFactionCommand extends GroupSubCommand
{
    
    public ChannelFactionCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, true);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        
        Channel chan = getPlugin().getChatManager().getActiveChannel((Player) sender);
        GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
        Faction f = gp.getFaction();
        
        if (f == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINFACTION;
        if (!f.getChannel().isAllowed(sender.getName()))
            return GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION;
        
        getPlugin().getChatManager().joinChannel(sender.getName(), f.getChannel());
        
        if (args.length == 1)
            return GroupLanguage.COMMAND_CHAT_JOIN_FACTION;
        
        StringBuilder str = new StringBuilder();
        for (int i = 1; i < args.length; i++)
            str.append(args[i] + " ");
        
        ((Player) sender).chat(str.toString());
        getPlugin().getChatManager().joinChannel((Player) sender, chan);
        return null;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/ch faction - Wechsel in den Fraktions Chat");
        sender.sendMessage("Alias: /ch f, /ch fraktion");
    }
    
}
