package de.craftlancer.groups.buildings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.craftlancer.core.Utils;
import de.craftlancer.economy.CLEco;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.ExpToBottleFeature;
import de.craftlancer.groups.FeatureType;
import de.craftlancer.groups.GroupLanguage;

public class ExpToBottleBuilding extends Building
{
    private static ItemStack signItem;
    
    static
    {
        signItem = new ItemStack(Material.SIGN);
        ItemMeta meta = signItem.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "ExpToBottle Sign");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.AQUA + "Feature Block");
        lore.add("Fülle EXP in Flaschen ab");
        lore.add("und nutze sie später.");
        meta.setLore(lore);
        signItem.setItemMeta(meta);
    }
    
    private Block exchangeSign;
    private ExpToBottleFeature feature;
    
    public ExpToBottleBuilding(CLGroups plugin, ExpToBottleFeature feature)
    {
        super(plugin);
        this.feature = feature;
        // TODO Auto-generated constructor stub
    }
    
    @EventHandler
    public void handleInteract(PlayerInteractEvent e)
    {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        
        if (!e.getClickedBlock().equals(exchangeSign))
            return;
        
        if (getPermission() != "" && getTown().hasPermission(e.getPlayer().getName(), getPermission()))
        {
            e.getPlayer().sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            return;
        }
        
        if (getTown().hasTownBank() && (getFreePermission() != "" && !getTown().hasPermission(e.getPlayer().getName(), getFreePermission())) && getCosts() != 0)
            if (!CLEco.getInstance().hasBalance(e.getPlayer().getInventory(), getCosts()))
            {
                e.getPlayer().sendMessage(GroupLanguage.ECONOMY_NOT_ENOUGH_MONEY);
                return;
            }
            else
                CLEco.getInstance().payBalance(e.getPlayer().getInventory(), getTown().getTownBank(), getCosts());
        
        fillXPBottle(e.getPlayer());
    }
    
    @Override
    public void handleBlockPlace(BlockPlaceEvent e)
    {
        if (isFinished())
            return;
        
        if (e.getItemInHand().equals(signItem))
        {
            exchangeSign = e.getBlock();
            Sign sign = (Sign) exchangeSign.getState();
            sign.setLine(0, "XP to Bottle");
            sign.setLine(1, "Tausche XP gegen");
            sign.setLine(2, "XP Flaschen ein!");
            sign.update();
            e.getPlayer().getInventory().removeItem(signItem);
        }
    }
    
    @EventHandler
    public void handleSignChange(SignChangeEvent e)
    {
        if (!isFinished())
            return;
        
        if (e.getBlock().equals(exchangeSign))
            e.setCancelled(true);
    }
    
    @Override
    public boolean checkBuilding()
    {
        if (exchangeSign != null)
        {
            setFinished(true);
            return true;
        }
        
        return false;
    }
    
    @Override
    public FeatureType getType()
    {
        return FeatureType.XP_TO_BOTTLE;
    }
    
    @Override
    public void save()
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public int getBuildBlockNumber()
    {
        return 1;
    }
    
    @Override
    public ItemStack[] getBuildBlocks()
    {
        return new ItemStack[] { signItem };
    }
    
    @SuppressWarnings("deprecation")
    public void fillXPBottle(Player p)
    {
        int exp = Utils.getExp((p.getLevel() + p.getExp()));
        
        if (exp >= feature.getCostPerBottle() && p.getLevel() > 0)
        {
            exp -= (feature.getCostPerBottle());
            
            int level = (int) Math.floor(Utils.getLevel(exp));
            float progress = (float) (Utils.getLevel(exp) - Math.floor(Utils.getLevel(exp)));
            
            p.setLevel(level);
            p.setExp(progress);
            
            ItemStack item = new ItemStack(Material.EXP_BOTTLE, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setLore(Arrays.asList(new String[] { "Experience: " + feature.getRecivePerBottle() }));
            item.setItemMeta(meta);
            
            p.getInventory().addItem(item);
            p.updateInventory();
        }
        else
            p.sendMessage("You don't have enough EXP");
    }
}
