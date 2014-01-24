package de.craftlancer.groups.commands.chat;

import java.util.Map;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.command.HelpCommand;
import de.craftlancer.core.command.SubCommand;
import de.craftlancer.groups.CLGroups;

public class ChannelHelpCommand extends HelpCommand
{
    
    public ChannelHelpCommand(String permission, CLGroups plugin, Map<String, SubCommand> map)
    {
        super(permission, plugin, map);
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/ch help - diese Liste");
        sender.sendMessage("/ch local - Wechsel in den lokalen Chat");
        sender.sendMessage("/ch global - Wechsel in den globalen Chat");
        sender.sendMessage("/ch town - Wechsel in den Stadtchat");
        sender.sendMessage("/ch faction - Wechsel in den Fraktionschat");
    }
    
}
