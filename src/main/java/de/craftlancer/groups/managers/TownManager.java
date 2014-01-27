package de.craftlancer.groups.managers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.Town;

public class TownManager
{
    private static TownManager instance;
    private Map<String, Town> towns = new HashMap<String, Town>();
    
    public static TownManager getInstance()
    {
        if (instance == null)
            instance = new TownManager();
        
        return instance;
    }
    
    public static void disbandTown(Town town)
    {
        if (town == null)
            throw new NullPointerException("Town can't be null!");
        
        for (Plot t : town.getPlots())
            t.reset();
        
        for (GroupPlayer gp : PlayerManager.getGroupPlayers())
            if (town.equals(gp.getTown()))
                gp.setTown(null);
        
        for (Faction f : FactionManager.getFactions())
            if (f.hasTown(town))
            {
                f.removeTown(town);
                break;
            }
        
        CLGroups.getInstance().getTownDataHandler().onTownDisband(town);
        town.clear();
        getInstance().towns.remove(town.getName());
    }
    
    public static boolean hasTown(String string)
    {
        return getInstance().towns.containsKey(string);
    }
    
    public static Town getTown(String string)
    {
        return getInstance().towns.get(string);
    }
    
    public static Set<String> getTownNames()
    {
        Set<String> tmp = new HashSet<String>();
        
        for (Entry<String, Town> e : getInstance().towns.entrySet())
            tmp.add(e.getValue().getFaction().getColor() + e.getKey() + " (" + e.getValue().getFaction().getName() + ")");
        
        return tmp;
    }
    
    public static void addTown(Town town)
    {
        getInstance().towns.put(town.getName(), town);
    }
    
    public static void renameTown(Town town, String newName)
    {
        String oldName = town.getName();
        getInstance().towns.remove(oldName);
        getInstance().towns.put(newName, town);
    }
    
    public static Collection<Town> getTowns()
    {
        return getInstance().towns.values();
    }
}
