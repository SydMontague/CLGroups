package de.craftlancer.groups.commands.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.chat.Channel;
import de.craftlancer.groups.commands.GroupSubCommand;

public class ChannelLocalCommand extends GroupSubCommand
{
    
    public ChannelLocalCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        
        Channel chan = getPlugin().getChatManager().getActiveChannel((Player) sender);
        
        getPlugin().getChatManager().joinChannel(sender.getName(), getPlugin().getChatManager().getDefaultChannel());
        if (args.length == 1)
            return GroupLanguage.COMMAND_CHAT_JOIN_LOCAL;
        
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
        sender.sendMessage("/ch local - Wechsel in den lokalen Chat");
        sender.sendMessage("Alias: /ch l, /ch lokal");
    }
    
}
