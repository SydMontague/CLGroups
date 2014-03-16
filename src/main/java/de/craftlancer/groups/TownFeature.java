package de.craftlancer.groups;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

/*
 * Listeners should only be used for global functions, like XP bottles, which are not bound to the location of the building.
 * 
 */
public abstract class TownFeature implements Listener
{
    private CLGroups plugin;
    private String key;
    private String name;
    private TownFeature upgrade = null;
    private boolean buildable;
    
    public TownFeature(CLGroups plugin, String key, FileConfiguration config)
    {
        this.plugin = plugin;
        this.key = key;
        // TODO costs
        this.name = config.getString(getKey() + ".name");
        this.buildable = config.getBoolean(getKey() + ".buildable", false);
        
        this.upgrade = getPlugin().getFeature(config.getString(getKey() + ".nextLevel"));
        
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }
    
    public abstract FeatureType getType();
    
    public CLGroups getPlugin()
    {
        return plugin;
    }
    
    public String getKey()
    {
        return key;
    }
    
    public String getName()
    {
        return name;
    }
    
    public boolean isUpgradeable()
    {
        return upgrade != null;
    }
    
    public boolean isBuildable()
    {
        return buildable;
    }
    
    public TownFeature getUpgrade()
    {
        return upgrade;
    }
    
    public void save(FileConfiguration config)
    {
        config.set(getKey() + ".name", getName());
        config.set(getKey() + ".type", getType().name());
    }
}
