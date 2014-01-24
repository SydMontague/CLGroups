package de.craftlancer.groups;

import java.util.HashSet;
import java.util.Set;

public class GroupPlayer implements Repuable
{
    private final String name;
    private Set<Plot> plots = new HashSet<Plot>();
    private Town town;
    
    public GroupPlayer(String name)
    {
        this.name = name;
    }
    
    public int getPlotCount()
    {
        return plots.size();
    }
    
    public Set<Plot> getPlots()
    {
        return plots;
    }
    
    public Town getTown()
    {
        return town;
    }
    
    public void setTown(Town town)
    {
        this.town = town;
    }
    
    @Override
    public String getName()
    {
        return name;
    }
    
    public Faction getFaction()
    {
        return town != null ? town.getFaction() : null;
    }
    
    public void removePlot(Plot plot)
    {
        plots.remove(plot);
    }
    
    public void addPlot(Plot plot)
    {
        plots.add(plot);
    }
}
