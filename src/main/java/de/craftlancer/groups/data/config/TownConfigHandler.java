package de.craftlancer.groups.data.config;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.data.TownDataHandler;

public class TownConfigHandler extends ConfigDataHandler implements TownDataHandler
{
    private static TownConfigHandler instance;
    
    private TownConfigHandler(CLGroups plugin)
    {
        super(plugin, "towns.yml");
    }
    
    @Override
    public void load()
    {
        for (String s : getConfig().getKeys(false))
            getPlugin().addTown(new Town(getPlugin(), s, getConfig()));
    }
    
    @Override
    public void save()
    {
        for (Town t : getPlugin().getTowns())
            t.save(getConfig());
        
        saveConfig();
    }
    
    @Override
    public void onTownDisband(Town town)
    {
        getConfig().set(town.getConfigKey(), null);
    }
    
    public static TownConfigHandler getInstance()
    {
        if (instance == null)
            instance = new TownConfigHandler(CLGroups.getInstance());
        
        return instance;
    }
    
}
