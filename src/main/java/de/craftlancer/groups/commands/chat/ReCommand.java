package de.craftlancer.groups.commands.chat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.managers.PlayerManager;

public class ReCommand implements CommandExecutor
{
    private CLGroups plugin;
    
    public ReCommand(CLGroups plugin)
    {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player && !sender.hasPermission("clgroups.commands.chat.msg"))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 1)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else if (plugin.getChatManager().getLastChatPartner(sender.getName()) == null)
            sender.sendMessage(GroupLanguage.COMMAND_CHAT_NOPARTNER);
        else
        {
            ChatColor color = sender instanceof Player ? PlayerManager.getGroupPlayer(sender.getName()).getFaction() != null ? PlayerManager.getGroupPlayer(sender.getName()).getFaction().getColor() : ChatColor.WHITE : ChatColor.WHITE;
            String ptag = sender instanceof Player ? PlayerManager.getGroupPlayer(sender.getName()).getFaction() != null ? PlayerManager.getGroupPlayer(sender.getName()).getFaction().getFormattedTag() : "" : "";
            
            String last = plugin.getChatManager().getLastChatPartner(sender.getName());
            CommandSender target = last.equalsIgnoreCase("Console") ? plugin.getServer().getConsoleSender() : plugin.getServer().getPlayer(last);
            
            if(target == null)
            {
                sender.sendMessage(GroupLanguage.COMMAND_CHAT_NOPARTNER);
                return true;
            }
            
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < args.length; i++)
                message.append(args[i] + " ");
            
            String m = "[MSG]<" + color + ptag + sender.getName() + ChatColor.WHITE + "> " + ChatColor.AQUA + message.toString();
            target.sendMessage(m);
            sender.sendMessage(m);
            
            plugin.getChatManager().setLastChatPartner(sender.getName(), target.getName());
        }
        
        return true;
    }
    
}
