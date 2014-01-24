package de.craftlancer.groups;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;

public enum Reputation
{
    WAR(ChatColor.DARK_RED, "Krieg"),
    ENEMY(ChatColor.RED, "Feind"),
    NEUTRAL(ChatColor.WHITE, "Neutral"),
    FRIEND(ChatColor.YELLOW, "Freund"),
    ALLIED(ChatColor.GREEN, "Bündnis"),
    OWN_FACTION(ChatColor.DARK_GREEN, "Mitglied"),
    UNDEFINED(ChatColor.WHITE, "Neutral");
    
    private ChatColor color;
    private String name;
    
    public static List<String> names = new LinkedList<String>()
    {
        private static final long serialVersionUID = 1L;
        {
            add(WAR.name());
            add(ENEMY.name());
            add(NEUTRAL.name());
            add(FRIEND.name());
            add(ALLIED.name());
        }
    };
    
    private Reputation(ChatColor color, String name)
    {
        this.color = color;
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public ChatColor getColor()
    {
        return color;
    }
    
    public Reputation getLowest(Reputation rep)
    {
        return compareTo(rep) <= 0 ? this : rep;
    }
    
    @Override
    public String toString()
    {
        return getColor() + name();
    }
    
    /**
     * Get a list of all possible reputation state names.
     * Valid are WAR, ENEMY, NEUTRAL, FRIEND and ALLIED
     * For TabComplete use.
     * 
     * @return the list of names
     */
    public static List<String> getNames()
    {
        return names;
    }
}
