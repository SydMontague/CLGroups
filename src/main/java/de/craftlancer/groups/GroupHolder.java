package de.craftlancer.groups;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

/*
 * 
 * <name>
 *  groups:
 *      member:
 *          member:
 *              - player
 *          permissions:
 *              - permission
 *              
 */
public abstract class GroupHolder
{
    private String configKey;
    private String name;
    private CLGroups plugin;
    private Map<String, Group> groups = new HashMap<String, Group>();
    
    public GroupHolder(CLGroups plugin, String name)
    {
        this.configKey = name;
        this.name = name;
        this.plugin = plugin;
    }
    
    public GroupHolder(CLGroups plugin, String configId, String name, FileConfiguration config)
    {
        this.configKey = configId;
        this.name = name;
        this.plugin = plugin;
        
        if (config.getConfigurationSection(getConfigKey() + ".groups") != null)
            for (String s : config.getConfigurationSection(getConfigKey() + ".groups").getKeys(false))
            {
                List<String> mList = config.getStringList(getConfigKey() + ".groups." + s + ".member");
                List<String> pList = config.getStringList(getConfigKey() + ".groups." + s + ".permission");
                addGroup(s, new Group(plugin, s, this, pList, mList));
            }
    }
    
    public String getConfigKey()
    {
        return configKey;
    }
    
    public CLGroups getPlugin()
    {
        return plugin;
    }
    
    public void addGroup(String str, Group group)
    {
        groups.put(str, group);
    }
    
    public void removeGroup(String str)
    {
        groups.remove(str);
    }
    
    public boolean hasGroup(String str)
    {
        return groups.containsKey(str);
    }
    
    public Group getGroup(String group)
    {
        return groups.get(group);
    }
    
    public Collection<Group> getGroups()
    {
        return groups.values();
    }
    
    public Set<String> getGroupNames()
    {
        return groups.keySet();
    }
    
    public boolean hasPermission(String p, String perm)
    {
        for (Group g : getGroups())
            if (g.hasPermission(p, perm))
                return true;
        
        return false;
    }
    
    public Map<String, Group> getGroupMap()
    {
        return groups;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void save(FileConfiguration config)
    {
        for (Entry<String, Group> e : groups.entrySet())
        {
            config.set(getConfigKey() + ".groups." + e.getKey() + ".member", e.getValue().getMember());
            config.set(getConfigKey() + ".groups." + e.getKey() + ".permission", e.getValue().getPermissions());
        }
    }
    
    protected void setName(String newName)
    {
        this.name = newName;
    }
    
    public abstract String getHolderPrefix();
}
