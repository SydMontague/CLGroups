package de.craftlancer.groups.data;

import de.craftlancer.groups.Town;

public interface TownDataHandler extends DataHandler
{
    public void onTownDisband(Town town);
}
