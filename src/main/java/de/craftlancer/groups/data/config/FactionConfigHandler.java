package de.craftlancer.groups.data.config;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.data.FactionDataHandler;
import de.craftlancer.groups.managers.FactionManager;

public class FactionConfigHandler extends ConfigDataHandler implements FactionDataHandler
{
    private static FactionConfigHandler instance;
    
    private FactionConfigHandler(CLGroups plugin)
    {
        super(plugin, "factions.yml");
    }
    
    @Override
    public void save()
    {
        for (Faction f : FactionManager.getFactions())
            f.save(getConfig());
        
        saveConfig();
    }
    
    @Override
    public void load()
    {
        for (String s : getConfig().getKeys(false))
            FactionManager.addFaction(new Faction(getPlugin(), s, getConfig()));
        
    }
    
    @Override
    public void onFactionDisband(Faction f)
    {
        getConfig().set(f.getConfigKey(), null);
    }

    public static FactionConfigHandler getInstance()
    {
        if (instance == null)
            instance = new FactionConfigHandler(CLGroups.getInstance());
        
        return instance;
    }
    
}
