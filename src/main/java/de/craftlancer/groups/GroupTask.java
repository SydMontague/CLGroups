package de.craftlancer.groups;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.craftlancer.groups.managers.PlotManager;

public class GroupTask extends BukkitRunnable
{
    private HashMap<String, Location> locMap = new HashMap<String, Location>();
    private CLGroups plugin;
    
    public GroupTask(CLGroups plugin)
    {
        this.plugin = plugin;
    }
    
    @Override
    public void run()
    {
        for (Player p : plugin.getServer().getOnlinePlayers())
        {            
            Plot f = PlotManager.getPlot(locMap.containsKey(p.getName()) ? locMap.get(p.getName()) : p.getLocation());
            Plot t = PlotManager.getPlot(p.getLocation());
            Town from = f.getTown();
            Town to = t.getTown();
            
            locMap.put(p.getName(), p.getLocation());
            
            if (from == to && f.getOwner() == t.getOwner())
                return;
            
            String towner = t.getOwner() == null ? "" : " - " + t.getOwner().getName();
            String townName = to == null ? "Unbesetzt" : to.getName() + " (" + to.getFaction().getName() + ")";
            
            if (from != null)
                if (from.getFarewellMsg() != null && from.getFarewellMsg().length() != 0)
                    p.sendMessage(from.getFarewellMsg());
            
            p.sendMessage(townName + towner);
            
            if (to != null)
                if (to.getWelcomeMsg() != null && to.getWelcomeMsg().length() != 0)
                    p.sendMessage(to.getWelcomeMsg());
        }
    }
    
}
