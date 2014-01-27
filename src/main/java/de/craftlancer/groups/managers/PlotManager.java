package de.craftlancer.groups.managers;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.craftlancer.groups.Plot;
import de.craftlancer.groups.Town;

public class PlotManager
{
    private static PlotManager instance;
    private Map<Point, Plot> plots = new HashMap<Point, Plot>();
    private static final int MAX_PLOTS = 5;
    private static int TOWN_DISTANCE = 3;
    private static int NORMAL_DISTANCE = 3;
    
    public static PlotManager getInstance()
    {
        if (instance == null)
            instance = new PlotManager();
        
        return instance;
    }
    
    public static void addPlot(Point point, Plot plot)
    {
        getInstance().plots.put(point, plot);
    }
    
    public static Plot getPlot(Location loc)
    {
        return getPlot(loc.getChunk().getX(), loc.getChunk().getZ(), loc.getWorld().getName());
    }
    
    public static Plot getPlot(int x, int z, String world)
    {
        Point p = new Point(x, z);
        if (!getInstance().plots.containsKey(p))
            getInstance().plots.put(p, new Plot(p, world));
        
        return getInstance().plots.get(p);
    }
    
    public static boolean checkPlotDistance(Plot plot, Player player)
    {
        if (plot.isTownPlot())
            return true;
        
        int maxdistance = TOWN_DISTANCE >= NORMAL_DISTANCE ? TOWN_DISTANCE : NORMAL_DISTANCE;
        
        for (int i = -maxdistance; i <= maxdistance; i++)
            for (int j = -maxdistance; j <= maxdistance; j++)
            {
                Plot p = plot.getRelative(i, j);
                if (p.getOwner() != null && p.getOwner().getName().equals(player.getName()))
                    continue;
                
                double distance = p.distance(plot);
                
                if ((p.isTownPlot() && distance <= TOWN_DISTANCE) || (p.isPlayerPlot() && distance <= NORMAL_DISTANCE))
                    return true;
            }
        
        return false;
    }
    
    public static Plot getPlot(Point point)
    {
        return getInstance().plots.get(point);
    }
    
    public static boolean checkPlotLimit(Player p)
    {
        return checkPlotLimit(p.getName());
    }
    
    public static boolean checkPlotLimit(String name)
    {
        return PlayerManager.getGroupPlayer(name).getPlotCount() >= MAX_PLOTS;
    }
    
    public static boolean checkPlotLimit(Town t)
    {
        return t.getPlots().size() < t.getMember().size() * 20;
    }
    
    public static Collection<Plot> getPlots()
    {
        return getInstance().plots.values();
    }
    
    public static Set<Plot> getPlots(Player p)
    {
        if (PlayerManager.hasGroupPlayer(p.getName()))
            return PlayerManager.getGroupPlayer(p.getName()).getPlots();
        
        return null;
    }
}
