package de.craftlancer.groups.chat;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{
    private ChatManager manager;
    
    public ChatListener(ChatManager manager)
    {
        this.manager = manager;
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e)
    {
        Player p = e.getPlayer();
        Channel chan = manager.getActiveChannel(p);
        
        if (!chan.isAllowed(p))
        {
            manager.joinChannel(p, manager.getDefaultChannel());
            chan = manager.getActiveChannel(p);
        }
        
        Set<Player> recipients = e.getRecipients();
        recipients.clear();
        
        for (Player op : Bukkit.getOnlinePlayers())
            if (chan.isAllowed(op))
                if (chan.getRange() == 0 || (p.getWorld().equals(op.getWorld()) && chan.getRange() >= p.getLocation().distance(op.getLocation())) || op.hasPermission("clgroups.chat." + chan.getName() + ".spy"))
                    recipients.add(op);
        
        e.setFormat(chan.getPlayerFormat(p));
    }
}
