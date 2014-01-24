package de.craftlancer.groups.data;

import de.craftlancer.groups.Faction;

public interface FactionDataHandler extends DataHandler
{    
    public void onFactionDisband(Faction f);
}
