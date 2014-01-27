package de.craftlancer.groups.chat;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.managers.PlayerManager;

public class Channel
{
    private CLGroups plugin;
    private List<String> member = new LinkedList<String>();
    private int range;
    private boolean global;
    private String format = "$chanTag$<$pColor$$tag$$name$§f> $chanColor$%2$s";
    private ChatColor color;
    private String tag;
    private String name;

    public Channel(String name, int range, boolean global, ChatColor color, String tag, CLGroups plugin)
    {
        this.name = name;
        this.plugin = plugin;
        this.range = range;
        this.global = global;
        this.color = color;
        this.tag = tag;
    }
    
    /**
     * Get the range in which this channel can be heard.
     * Ignored if {@link #isGlobal()} is true.
     * 
     * @return the range of the channel, 0 for unlimited range
     */
    public int getRange()
    {
        return range;
    }
    
    /**
     * Get whether this channel is global and can be read from all worlds, or not.
     * If true {@link #getRange()} is ignored.
     * 
     * @return true if this channel can be read in all worlds
     */
    public boolean isGlobal()
    {
        return global;
    }
    
    public String getName()
    {
        return name;
    }

    public List<String> getMembers()
    {
        return member;
    }
    
    public void addMember(Player p)
    {
        addMember(p.getName());
    }
    
    public void addMember(String p)
    {
        member.add(p);
    }
    
    public void removeMember(Player p)
    {
        removeMember(p.getName());
    }

    private void removeMember(String p)
    {
        member.remove(p);
    }
    
    public void setChanColor(ChatColor color)
    {
        this.color = color;
    }
    
    public ChatColor getChanColor()
    {
        return color;
    }
    
    public void setChanTag(String tag)
    {
        this.tag = tag;
    }
    
    public String getChanTag()
    {
        return tag;
    }

    public String getFormat()
    {
        return format;
    }
    
    public CLGroups getPlugin()
    {
        return plugin;
    }
    
    public String getPlayerFormat(Player p)
    {
        return getPlayerFormat(p.getName());
    }
    
    public String getPlayerFormat(String p)
    {
        GroupPlayer gp = PlayerManager.getGroupPlayer(p);
        String localFormat = format;
        
        ChatColor pColor = gp.getFaction() == null ? ChatColor.WHITE : gp.getFaction().getColor();
        String ptag = gp.getFaction() == null ? "" : gp.getFaction().getFormattedTag();
        
        localFormat = localFormat.replace("$chanTag$", getChanTag());
        localFormat = localFormat.replace("$pColor$", pColor.toString());
        localFormat = localFormat.replace("$tag$", ptag);
        localFormat = localFormat.replace("$chanColor$", getChanColor().toString());
        localFormat = localFormat.replace("$name$", p);
        return localFormat;
    }
    
    public boolean isAllowed(Player p)
    {
        return true;
    }
    
    public boolean isAllowed(String p)
    {
        return true;
    }
}
