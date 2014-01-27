package de.craftlancer.groups.managers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.Town;

public class FactionManager
{
    private static FactionManager instance = new FactionManager();
    private Map<String, Faction> factions = new HashMap<String, Faction>();
    
    public static FactionManager getInstance()
    {
        if(instance == null)
            instance = new FactionManager();
        
        return instance;
    }
    
    public static Faction getFaction(String name)
    {
        return getInstance().factions.get(name);
    }
    
    public static Faction getPlayerGroup(Player player)
    {
        return getPlayerGroup(player.getName());
    }
    
    public static Faction getPlayerGroup(String name)
    {
        for (Faction g : getInstance().factions.values())
            if (g.hasMember(name))
                return g;
        
        return null;
    }
    
    public static void disbandFaction(Faction faction)
    {
        if (faction == null)
            throw new NullPointerException("Faction can't be null!");
        
        for (Town t : new HashSet<Town>(faction.getTowns()))
            t.disband();
        
        for (Faction f : getInstance().factions.values())
            f.removeReputation(faction);
        
        CLGroups.getInstance().getFactionDataHandler().onFactionDisband(faction);
        faction.clear();
        instance.factions.remove(faction.getName());
    }
    
    public static void renameFaction(Faction faction, String newName)
    {
        String oldName = faction.getName();
        getInstance().factions.remove(oldName);
        getInstance().factions.put(newName, faction);
        
        for (Faction f : getInstance().factions.values())
            f.updateRepu(oldName, newName);
    }
    
    public static Collection<Faction> getFactions()
    {
        return getInstance().factions.values();
    }
    
    public static boolean isColorTaken(ChatColor color)
    {
        if (color == ChatColor.WHITE)
            return false;
        
        for (Faction f : getInstance().factions.values())
            if (color == f.getColor())
                return true;
        
        return false;
    }
    
    public static boolean isTagTaken(String tag)
    {
        if (tag.equals(""))
            return false;
        
        for (Faction f : getInstance().factions.values())
            if (tag.equalsIgnoreCase(f.getTag()))
                return true;
        
        return false;
    }
    
    public static boolean hasFaction(String string)
    {
        return getInstance().factions.containsKey(string);
    }
    
    public static void addFaction(Faction faction)
    {
        getInstance().factions.put(faction.getName(), faction);
    }
    
    public static Set<String> getFactionNames()
    {
        Set<String> tmp = new HashSet<String>();
        
        for (Entry<String, Faction> e : getInstance().factions.entrySet())
            tmp.add(e.getValue().getColor() + e.getKey());
        
        return tmp;
    }
}
