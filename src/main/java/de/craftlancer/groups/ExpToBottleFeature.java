package de.craftlancer.groups;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

/*
 * feature:
 *  xp2bottle:
 *      name: "EXP to Bottle"
 *      type: XP_TO_BOTTLE
 *      costs: 
 *      - currencyHandler currencies
 *      costPerBottle: 10 //xp taken per bottle
 *      recivePerBottle: 10 //xp a bottle gives back
 *      nextLevel: xp2bottle2
 */
public class ExpToBottleFeature extends TownFeature
{
    private int costPerBottle;
    private int recivePerBottle;
    
    private static Map<Location, ItemStack> locs = new HashMap<Location, ItemStack>();
    
    public ExpToBottleFeature(CLGroups plugin, String key, FileConfiguration config)
    {
        super(plugin, key, config);
        
        this.costPerBottle = config.getInt(getKey() + ".costPerBottle", 0);
        this.recivePerBottle = config.getInt(getKey() + ".recivePerBottle", 0);
        // TODO Auto-generated constructor stub
    }
    
    public int getCostPerBottle()
    {
        return costPerBottle;
    }
    
    public int getRecivePerBottle()
    {
        return recivePerBottle;
    }
    
    @EventHandler
    public static void onExpBottleDispense(BlockDispenseEvent e)
    {
        if (e.getItem().getType() != Material.EXP_BOTTLE)
            return;
        
        if (e.getBlock().getType() != Material.DISPENSER)
            return;
        
        locs.put(e.getBlock().getLocation(), e.getItem());
    }
    
    @EventHandler
    public static void onExpBottleThrow(ProjectileLaunchEvent e)
    {
        if (e.getEntityType() != EntityType.THROWN_EXP_BOTTLE)
            return;
        
        if (e.getEntity().getShooter() != null && !(e.getEntity().getShooter() instanceof Player))
            return;
        
        ItemStack i = null;
        
        if (e.getEntity().getShooter() == null)
        {
            Location l = null;
            for (Entry<Location, ItemStack> entry : locs.entrySet())
            {
                double distance = e.getEntity().getLocation().distance(entry.getKey());
                if (distance >= 0.7348 && distance <= 1.4)
                {
                    i = entry.getValue();
                    l = entry.getKey();
                    break;
                }
            }
            
            if (l == null)
                return;
            
            locs.remove(l);
        }
        else
            i = ((Player) e.getEntity().getShooter()).getItemInHand();
        
        if (i == null || !i.hasItemMeta() || !i.getItemMeta().hasLore())
            return;
        
        String line1 = i.getItemMeta().getLore().get(0);
        
        if (!line1.startsWith("Experience: "))
            return;
        
        int exp = Integer.parseInt(line1.split(" ")[1]);
        
        e.getEntity().setMetadata("clgroups.xpbottle", new FixedMetadataValue(CLGroups.getInstance(), exp));
    }
    
    @EventHandler
    public static void onExpBottle(ExpBottleEvent e)
    {
        if (!e.getEntity().hasMetadata("clgroups.xpbottle"))
            return;
        
        e.setExperience(e.getEntity().getMetadata("clgroups.xpbottle").get(0).asInt());
    }
    
    @Override
    public FeatureType getType()
    {
        return FeatureType.XP_TO_BOTTLE;
    }
    
}
