package de.craftlancer.groups.chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Town;

public class TownChannel extends Channel
{
    private Town town;
    
    public TownChannel(CLGroups plugin, Town town)
    {
        super("t:" + town.getName(), 0, true, ChatColor.BLUE, "[T]", plugin);
        this.town = town;
    }
    
    public Town getTown()
    {
        return town;
    }
    
    @Override
    public boolean isAllowed(Player p)
    {
        return isAllowed(p.getName());
    }
    
    @Override
    public boolean isAllowed(String p)
    {
        return town.hasPermission(p, "town.chat");
    }
}
