package de.craftlancer.groups.buildings;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.FeatureType;
import de.craftlancer.groups.Town;

/*
 * - start building
 * - give blocks to player
 * - handle player's actions with these blocks (save them)
 * - check everytime if the building is finished
 * - if so, finish the building and add the to the town while removing it's token for that
 * - if the town has no token, then stop the building process
 * - 
 * 
 * 
 * Each building has a feature. The feature CAN change. (upgrade)
 */
public abstract class Building implements Listener
{
    private boolean finished;
    private CLGroups plugin;
    private Town town;
    private String permission;
    private String freePermission;
    private int costs;
    
    public Building(CLGroups plugin)
    {
        this.plugin = plugin;
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }
    
    public CLGroups getPlugin()
    {
        return plugin;
    }
    
    public boolean isFinished()
    {
        return finished;
    }
    
    protected void setFinished(boolean bool)
    {
        this.finished = bool;
    }
    
    public abstract void handleBlockPlace(BlockPlaceEvent e);
    
    public abstract boolean checkBuilding();
    
    public abstract FeatureType getType();
    
    public abstract void save();
    
    public abstract int getBuildBlockNumber();
    
    public abstract ItemStack[] getBuildBlocks();
    
    public void setTown(Town town)
    {
        this.town = town;
    }
    
    public Town getTown()
    {
        return town;
    }
    
    public String getPermission()
    {
        return permission;
    }
    
    public void setPermission(String permission)
    {
        this.permission = permission;
    }
    
    public String getFreePermission()
    {
        return freePermission;
    }
    
    public void setFreePermission(String freePermission)
    {
        this.freePermission = freePermission;
    }
    
    public int getCosts()
    {
        return costs;
    }
    
    public void setCosts(int costs)
    {
        this.costs = costs;
    }
}
