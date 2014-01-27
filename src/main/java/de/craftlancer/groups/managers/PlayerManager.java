package de.craftlancer.groups.managers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.craftlancer.groups.GroupPlayer;

public class PlayerManager
{
    private static PlayerManager instance;
    private Map<String, GroupPlayer> gplayers = new HashMap<String, GroupPlayer>();
    
    public static PlayerManager getInstance()
    {
        if(instance == null)
            instance = new PlayerManager();
        
        return instance;
    }
    
    public static GroupPlayer getGroupPlayer(String p)
    {
        if (p == null)
            return null;
        
        if (!hasGroupPlayer(p))
            getInstance().gplayers.put(p, new GroupPlayer(p));
        
        return getInstance().gplayers.get(p);
    }
    
    public static boolean hasGroupPlayer(String p)
    {
        return getInstance().gplayers.containsKey(p);
    }
    
    public static Collection<GroupPlayer> getGroupPlayers()
    {
        return getInstance().gplayers.values();
    }
}
