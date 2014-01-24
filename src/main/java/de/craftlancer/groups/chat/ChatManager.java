package de.craftlancer.groups.chat;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;

public class ChatManager
{
    private static int DEF_RANGE = 100;

    private Map<String, String> lastPartner = new HashMap<String, String>();
    private Map<String, Channel> pToChan = new HashMap<String, Channel>();
    private Map<String, Channel> channels = new HashMap<String, Channel>();
    private CLGroups plugin;
    private ChatListener listener = new ChatListener(this);
    
    public ChatManager(CLGroups plugin)
    {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        
        registerChannel(new Channel("default", DEF_RANGE, false, ChatColor.WHITE, "[L]", plugin));
        registerChannel(new Channel("global", 0, false, ChatColor.YELLOW, "[G]", plugin));
    }
    
    public Channel getActiveChannel(Player player)
    {
        Channel chan = pToChan.get(player.getName());
        
        return chan != null ? chan : getDefaultChannel();
    }
    
    public Channel getDefaultChannel()
    {
        return channels.get("default");
    }
    
    public void registerChannel(Channel chan)
    {
        channels.put(chan.getName(), chan);
    }
    
    public CLGroups getPlugin()
    {
        return plugin;
    }
    
    public Channel getChannel(String s)
    {
        Channel chan = channels.get(s);
        
        return chan == null ? getDefaultChannel() : chan;
    }
    
    public boolean joinChannel(Player p, String chan)
    {
        return joinChannel(p.getName(), getChannel(chan));
    }
    
    public boolean joinChannel(Player p, Channel chan)
    {
        return joinChannel(p.getName(), chan);
    }
    
    public boolean joinChannel(String p, String chan)
    {
        return joinChannel(p, getChannel(chan));
    }
    
    public boolean joinChannel(String p, Channel chan)
    {
        if (!chan.isAllowed(p))
            return false;
        
        pToChan.put(p, chan);
        return true;
    }
    
    public String getLastChatPartner(String name)
    {
        return lastPartner.get(name);
    }
    
    public void setLastChatPartner(String name, String name2)
    {
        lastPartner.put(name, name2);
        lastPartner.put(name2, name);
    }
}
