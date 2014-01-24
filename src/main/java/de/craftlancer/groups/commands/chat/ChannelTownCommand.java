package de.craftlancer.groups.commands.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.chat.Channel;
import de.craftlancer.groups.commands.GroupSubCommand;

public class ChannelTownCommand extends GroupSubCommand
{
    
    public ChannelTownCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else
        {
            GroupPlayer gp = getPlugin().getGroupPlayer(sender.getName());
            Town t = gp.getTown();
            
            if (t == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINTOWN);
            else if (!t.getChannel().isAllowed(sender.getName()))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else
            {
                Channel chan = getPlugin().getChatManager().getActiveChannel((Player) sender);
                getPlugin().getChatManager().joinChannel(sender.getName(), t.getChannel());
                
                if (args.length == 1)
                    sender.sendMessage(GroupLanguage.COMMAND_CHAT_JOIN_TOWN);
                else
                {
                    StringBuilder str = new StringBuilder();
                    for (int i = 1; i < args.length; i++)
                        str.append(args[i] + " ");
                    
                    ((Player) sender).chat(str.toString());
                    getPlugin().getChatManager().joinChannel((Player) sender, chan);
                }
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/ch town - Wechsel in den Stadt Chat");
        sender.sendMessage("Alias: /ch t");
    }
    
}
