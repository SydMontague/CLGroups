package de.craftlancer.groups.buildings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import de.craftlancer.core.InventoryUtils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.ExpToBottleFeature;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.TownFeature;

public class BuildingManager implements Listener
{
    private CLGroups plugin;
    private Map<String, Building> builder = new HashMap<String, Building>();
    
    public BuildingManager(CLGroups plugin)
    {
        this.plugin = plugin;
        // TODO Auto-generated constructor stub
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
        if (!builder.containsKey(e.getPlayer().getName()))
            return;
        
        builder.get(e.getPlayer().getName()).handleBlockPlace(e);
        
        if (builder.get(e.getPlayer().getName()).checkBuilding())
        {
            Building build = builder.remove(e.getPlayer().getName());
            Town t = plugin.getGroupPlayer(e.getPlayer().getName()).getTown();
            
            if(t == null || build == null)
                return;
            
            t.addBuilding(build);
        }
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (!builder.containsKey(e.getWhoClicked().getName()))
            return;
        
        if (e.isShiftClick() && isBuildItem(e.getCurrentItem()))
            e.setCancelled(true);
        
        if (isBuildItem(e.getCursor()))
            if (e.getRawSlot() < e.getView().getTopInventory().getSize())
                e.setCancelled(true);
    }
    
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e)
    {
        if (!builder.containsKey(e.getWhoClicked().getName()))
            return;
        
        if (isBuildItem(e.getOldCursor()))
            e.setCancelled(true);
    }
    
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e)
    {
        if (!builder.containsKey(e.getPlayer().getName()))
            return;
        
        if (isBuildItem(e.getItemDrop().getItemStack()))
            e.setCancelled(true);
    }
    
    @EventHandler
    public void onDeath(PlayerDeathEvent e)
    {
        stopBuilding(e.getEntity());
    }
    
    @EventHandler
    public void onDeath(ItemSpawnEvent e)
    {
        if (isBuildItem(e.getEntity().getItemStack()))
            e.setCancelled(true);
    }
    
    public static boolean isBuildItem(ItemStack i)
    {
        return i != null && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().get(0).equals(ChatColor.AQUA + "Feature Block");
        
    }
    
    public void startBuilding(Player p, TownFeature feature)
    {
        Validate.notNull(p, "Player can't be null!");
        Validate.notNull(feature, "TownFeature can't be null!");
        
        Building b = createBuilding(feature);
        
        if (InventoryUtils.freeSlots(p.getInventory()) < b.getBuildBlockNumber())
            p.sendMessage(GroupLanguage.BUILDING_INVENTORY_FULL);
        
        p.getInventory().addItem(b.getBuildBlocks());
        
        builder.put(p.getName(), b);
    }
    
    private Building createBuilding(TownFeature feature)
    {
        switch (feature.getType())
        {
            case ENCHANT_TO_XP:
                return null;
            case XP_TO_BOTTLE:
                return new ExpToBottleBuilding(plugin, (ExpToBottleFeature) feature);
            default:
                throw new IllegalArgumentException();
        }
    }
    
    public void stopBuilding(Player p)
    {
        List<ItemStack> items = new ArrayList<ItemStack>();
        
        for (ItemStack i : p.getInventory())
            if (i != null && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().get(0).equals(ChatColor.AQUA + "Feature Block"))
                items.add(i);
        
        for (ItemStack i : items)
            p.getInventory().removeItem(i);
        
        builder.remove(p.getName());
    }
}
