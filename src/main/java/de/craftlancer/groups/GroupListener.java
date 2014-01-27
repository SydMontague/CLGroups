package de.craftlancer.groups;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import de.craftlancer.groups.managers.FactionManager;
import de.craftlancer.groups.managers.PlayerManager;
import de.craftlancer.groups.managers.PlotManager;

public class GroupListener implements Listener
{
    private CLGroups plugin;
    private Set<Material> farmMapBreak = new HashSet<Material>()
    {
        private static final long serialVersionUID = 1L;
        {
            add(Material.POTATO);
            add(Material.CROPS);
            add(Material.CARROT);
            add(Material.PUMPKIN);
            add(Material.MELON_BLOCK);
            add(Material.SUGAR_CANE_BLOCK);
            add(Material.NETHER_WARTS);
            add(Material.RED_MUSHROOM);
            add(Material.BROWN_MUSHROOM);
        }
    };
    private Set<Material> farmMapPlace = new HashSet<Material>()
    {
        private static final long serialVersionUID = 1L;
        {
            add(Material.POTATO);
            add(Material.CROPS);
            add(Material.CARROT);
            add(Material.PUMPKIN_STEM);
            add(Material.MELON_STEM);
            add(Material.SUGAR_CANE_BLOCK);
            add(Material.NETHER_WARTS);
            add(Material.RED_MUSHROOM);
            add(Material.BROWN_MUSHROOM);
        }
    };
    
    public GroupListener(CLGroups plugin)
    {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onNameTag(PlayerReceiveNameTagEvent e)
    {
        if (e.getTag().equals("PumpkinBandit"))
            return;
        
        Faction g1 = FactionManager.getPlayerGroup(e.getPlayer());
        Faction g2 = FactionManager.getPlayerGroup(e.getNamedPlayer());
        
        Reputation rep1 = g1 != null ? g1.getReputation(e.getNamedPlayer()) : Reputation.UNDEFINED;
        Reputation rep2 = g2 != null ? g2.getReputation(e.getPlayer()) : Reputation.UNDEFINED;
        
        ChatColor color = rep1.getLowest(rep2).getColor();
        e.setTag(color + e.getTag());
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Town t = PlayerManager.getGroupPlayer(e.getPlayer().getName()).getTown();
        if (t != null)
            e.getPlayer().sendMessage(t.getLoginMsg());
    }
    
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e)
    {
        if (e.getPlayer().hasPermission("clgroups.admin"))
            return;
        
        if (farmMapBreak.contains(e.getBlock().getType()) && (plugin.getPvPTimer() == null || plugin.getPvPTimer().isProtected(e.getPlayer())))
            return;
        
        Plot plot = PlotManager.getPlot(e.getBlock().getLocation());
        String p = e.getPlayer().getName();
        
        if (!plot.canBuild(p))
        {
            e.setCancelled(true);
            e.getPlayer().setMetadata("clgroups.breakmsg", new FixedMetadataValue(plugin, null));
        }
    }
    
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e)
    {
        if (e.getPlayer().hasPermission("clgroups.admin"))
            return;
        
        if (farmMapPlace.contains(e.getBlock().getType()))
            return;
        
        Plot plot = PlotManager.getPlot(e.getBlock().getLocation());
        String p = e.getPlayer().getName();
        
        if (!plot.canBuild(p))
        {
            e.setCancelled(true);
            e.getPlayer().setMetadata("clgroups.placemsg", new FixedMetadataValue(plugin, null));
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void blockPlaceMonitor(BlockPlaceEvent e)
    {
        if (e.isCancelled() && e.getPlayer().hasMetadata("clgroups.placemsg"))
        {
            e.getPlayer().sendMessage(GroupLanguage.GAME_BUILD_FORBIDDEN);
            e.getPlayer().removeMetadata("clgroups.placemsg", plugin);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent e)
    {
        if (!(e.getEntity() instanceof Player))
            return;
        
        Player victim = (Player) e.getEntity();
        Player attacker;
        
        if (e.getDamager() instanceof Projectile && (((Projectile) e.getDamager()).getShooter() instanceof Player))
            attacker = (Player) ((Projectile) e.getDamager()).getShooter();
        else if (e.getDamager() instanceof Player)
            attacker = (Player) e.getDamager();
        else
            return;
        
        Plot p = PlotManager.getPlot(victim.getLocation());
        Town t1 = PlayerManager.getGroupPlayer(attacker.getName()).getTown();
        Town t2 = PlayerManager.getGroupPlayer(victim.getName()).getTown();
        
        if (p.getTown() == null || t1 == null || t2 == null || t1 != t2 || p.getTown() != t1)
            return;
        
        e.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void blockBreakMonitor(BlockBreakEvent e)
    {
        if (e.isCancelled() && e.getPlayer().hasMetadata("clgroups.breakmsg"))
        {
            e.getPlayer().sendMessage(GroupLanguage.GAME_BUILD_FORBIDDEN);
            e.getPlayer().removeMetadata("clgroups.breakmsg", plugin);
        }
    }
}
