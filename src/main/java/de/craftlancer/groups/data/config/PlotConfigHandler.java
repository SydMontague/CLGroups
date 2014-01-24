package de.craftlancer.groups.data.config;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.data.PlotDataHandler;

public class PlotConfigHandler extends ConfigDataHandler implements PlotDataHandler
{
    private static PlotConfigHandler instance;
    
    private PlotConfigHandler(CLGroups plugin)
    {
        super(plugin, "plots.yml");
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void load()
    {
        for (String s : getConfig().getKeys(false))
            getPlugin().addPlot(Utils.parsePointString(s), new Plot(getPlugin(), s, getConfig()));
    }
    
    @Override
    public void save()
    {
        for (Plot f : getPlugin().getPlots())
            f.save(getConfig());
        
        saveConfig();
    }

    public static PlotConfigHandler getInstance()
    {
        if (instance == null)
            instance = new PlotConfigHandler(CLGroups.getInstance());
        
        return instance;
    }
    
}
