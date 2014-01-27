package de.craftlancer.groups;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.craftlancer.groups.buildings.Building;
import de.craftlancer.groups.chat.Channel;
import de.craftlancer.groups.chat.TownChannel;
import de.craftlancer.groups.managers.FactionManager;
import de.craftlancer.groups.managers.PlayerManager;
import de.craftlancer.groups.managers.TownManager;

/*
 * town:
 *  name: name
 *  plots:
 *      - 1,1
 *      - 2,1
 *  welcomeMsg: msg
 *  farewellMsg: msg
 *  loginMsg: msg
 *  groups:
 *      member:
 *          member:
 *              - player
 *          permissions:
 *              - permission
 */

public class Town extends GroupHolder
{
    private Faction faction;
    private List<Plot> plots = new ArrayList<Plot>();
    private String welcomeMsg, farewellMsg, loginMsg;
    private Channel chan;
    private List<Building> buildings = new LinkedList<Building>();
    
    // private List<FeatureType> tokens = new LinkedList<FeatureType>();
    
    public Town(CLGroups plugin, Plot plot, String name, String founder, Faction faction)
    {
        super(plugin, name);
        this.faction = faction;
        faction.addTown(this);
        addPlot(plot);
        addGroup("member", new Group(plugin, "member", this, plugin.getDefMemberPerms()));
        addGroup("mayor", new Group(plugin, "mayor", this, plugin.getDefMayorPerms(), founder));
        addMember(founder);
        chan = new TownChannel(getPlugin(), this);
        getPlugin().getChatManager().registerChannel(getChannel());
    }
    
    public Town(CLGroups plugin, String name, FileConfiguration config)
    {
        super(plugin, name, config.getString(name + ".name"), config);
        faction = FactionManager.getFaction(config.getString(getConfigKey() + ".faction"));
        faction.addTown(this);
        welcomeMsg = config.getString(getConfigKey() + ".welcomeMsg");
        farewellMsg = config.getString(getConfigKey() + ".farewellMsg");
        loginMsg = config.getString(getConfigKey() + ".loginMsg");
        
        for (String s : getMember())
            PlayerManager.getGroupPlayer(s).setTown(this);
        
        chan = new TownChannel(getPlugin(), this);
        getPlugin().getChatManager().registerChannel(getChannel());
    }
    
    public Channel getChannel()
    {
        return chan;
    }
    
    public void addPlot(Plot plot)
    {
        plot.setTown(this);
        plots.add(plot);
    }
    
    public void removePlot(Plot plot)
    {
        plots.remove(plot);
        plot.reset();
    }
    
    public Faction getFaction()
    {
        return faction;
    }
    
    public void setFaction(Faction f)
    {
        faction.removeTown(this);
        f.addTown(this);
        faction = f;
    }
    
    public boolean hasMember(Player p)
    {
        return hasMember(p.getName());
    }
    
    public boolean hasMember(String p)
    {
        return getMember().contains(p);
    }
    
    public boolean hasMayor(String p)
    {
        return getMayors().contains(p);
    }
    
    public List<String> getMember()
    {
        return getGroup("member").getMember();
    }
    
    public void addMember(String p)
    {
        getGroup("member").addPlayer(p);
        PlayerManager.getGroupPlayer(p).setTown(this);
    }
    
    public void sendMessage(String string)
    {
        for (String p : getMember())
            if (getPlugin().getServer().getOfflinePlayer(p).isOnline())
                getPlugin().getServer().getPlayerExact(p).sendMessage(string);
    }
    
    public List<String> getMayors()
    {
        return getGroup("mayor").getMember();
    }
    
    public void setWelcomeMsg(String string)
    {
        welcomeMsg = string;
    }
    
    public void setLoginMsg(String string)
    {
        loginMsg = string;
    }
    
    public void setFarewellMsg(String string)
    {
        farewellMsg = string;
    }
    
    public String getWelcomeMsg()
    {
        return welcomeMsg;
    }
    
    public String getFarewellMsg()
    {
        return farewellMsg;
    }
    
    public String getLoginMsg()
    {
        return loginMsg;
    }
    
    public List<Plot> getPlots()
    {
        return plots;
    }
    
    public void removeMember(String string)
    {
        for (Plot p : plots)
            if (p.getOwner() != null && p.getOwner().getName().equals(string))
            {
                p.setOwner(null);
                // p.setForSale(false, 0);
            }
        
        for (Group g : getGroups())
            g.removePlayer(string);
        
        PlayerManager.getGroupPlayer(string).setTown(null);
    }
    
    public void clear()
    {
        plots.clear();
        getGroups().clear();
    }
    
    @Override
    public void save(FileConfiguration config)
    {
        super.save(config);
        config.set(getConfigKey() + ".name", getName());
        config.set(getConfigKey() + ".welcomeMsg", getWelcomeMsg());
        config.set(getConfigKey() + ".farewellMsg", getFarewellMsg());
        config.set(getConfigKey() + ".loginMsg", getLoginMsg());
        config.set(getConfigKey() + ".faction", getFaction().getName());
    }
    
    @Override
    public String getHolderPrefix()
    {
        return "t:";
    }
    
    public void addBuilding(Building build)
    {
        buildings.add(build);
        build.setTown(this);
    }
    
    public boolean hasTownBank()
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    public Inventory getTownBank()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void disband()
    {
        TownManager.disbandTown(this);
    }
    
    public void rename(String string)
    {
        TownManager.renameTown(this, string);
        setName(string);
    }
}
