package de.craftlancer.groups.chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.managers.PlayerManager;

public class FactionChannel extends Channel
{
    private Faction faction;
    
    public FactionChannel(CLGroups plugin, Faction faction)
    {
        super("f:" + faction.getName(), 0, true, ChatColor.GOLD, "[F]", plugin);
        this.faction = faction;
    }
    
    public Faction getFaction()
    {
        return faction;
    }
    
    @Override
    public boolean isAllowed(Player p)
    {
        return isAllowed(p.getName());
    }
    
    @Override
    public boolean isAllowed(String p)
    {
        GroupPlayer gp = PlayerManager.getGroupPlayer(p);
        return getFaction().equals(gp.getFaction()) && (getFaction().hasPermission(p, "faction.chat") || (gp.getTown() != null && gp.getTown().hasPermission(p, "faction.chat")));
    }
}
