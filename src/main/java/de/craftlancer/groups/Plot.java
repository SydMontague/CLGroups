package de.craftlancer.groups;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.craftlancer.core.Utils;

/*
 * plots:
 *  1,1:
 *      owner: player
 *      town: town
 *      groups:
 *          - group
 *      players:
 *          - player
 *      isForSale: true
 *      price: 100
 *      flag: default
 */
public class Plot
{
    private final Point position;
    private String world;
    private CLGroups plugin;
    private List<String> players = new LinkedList<String>();
    private List<Group> groups = new LinkedList<Group>();
    private Town town = null;
    private GroupPlayer owner = null;
    // private boolean isForSale = false;
    // private int price = 0;
    private String flag = "default";
    
    public Plot(Point position, String world, CLGroups plugin)
    {
        this.position = position;
        this.world = world;
        this.plugin = plugin;
    }
    
    public Plot(CLGroups plugin, String posi, FileConfiguration config)
    {
        this.plugin = plugin;
        position = Utils.parsePointString(posi);
        world = Utils.parsePointWorld(posi);
        
        owner = plugin.getGroupPlayer(config.getString(posi + ".owner"));
        if (owner != null)
            owner.addPlot(this);
        
        town = plugin.getTown(config.getString(posi + ".town"));
        if (town != null)
            town.addPlot(this);
        
        players = config.getStringList(posi + ".player");
        for (String s : config.getStringList(posi + ".group"))
        {
            String[] str = s.split(":");
            if (str[0].equals("f"))
                groups.add(town.getFaction().getGroup(str[1]));
            else
                groups.add(town.getGroup(str[1]));
        }
        // price = config.getInt(posi + ".price", 0);
        // isForSale = config.getBoolean(posi + ".isForSale", false);
        flag = config.getString(posi + ".flag", "default");
    }
    
    public boolean isBuyable()
    {
        return !isTownPlot() && getOwner() == null; // isForSale();
    }
    
    public boolean isTownPlot()
    {
        return town != null;
    }
    
    public boolean isPlayerPlot()
    {
        return !(isTownPlot() || owner == null);
    }
    
    public double distance(Plot plot)
    {
        return getPosition().distance(plot.getPosition());
    }
    
    public Point getPosition()
    {
        return position;
    }
    
    public Town getTown()
    {
        return town;
    }
    
    public void setOwner(String p)
    {
        if (owner != null)
            owner.removePlot(this);
        if (p != null)
        {
            owner = plugin.getGroupPlayer(p);
            owner.addPlot(this);
        }
        else
            owner = null;
        players.clear();
        groups.clear();
    }
    
    /*
     * public void setForSale(boolean isForSale, int price)
     * {
     * this.isForSale = isForSale;
     * this.price = price;
     * }
     * public boolean isForSale()
     * {
     * return isForSale;
     * }
     */
    
    public boolean isOwner(Player p)
    {
        return owner != null ? owner.getName().equals(p.getName()) : false;
    }
    
    public void addPlayer(String string)
    {
        players.add(string);
    }
    
    public void removePlayer(String string)
    {
        players.remove(string);
    }
    
    public List<String> getPlayers()
    {
        return players;
    }
    
    public List<Group> getGroups()
    {
        return groups;
    }
    
    /*
     * public int getPrice()
     * {
     * return price;
     * }
     */
    public void setTown(Town town)
    {
        this.town = town;
    }
    
    public void reset()
    {
        setTown(null);
        setOwner(null);
        // setForSale(false, 0);
        players.clear();
        groups.clear();
    }
    
    public GroupPlayer getOwner()
    {
        return owner;
    }
    
    public void addGroup(Group group)
    {
        groups.add(group);
    }
    
    public void setFlag(String flag)
    {
        this.flag = flag;
    }
    
    public String getFlag()
    {
        return flag != null ? flag : "default";
    }
    
    public void removeGroup(Group group)
    {
        groups.remove(group);
    }
    
    public boolean canBuild(Player p)
    {
        return canBuild(p.getName());
    }
    
    public boolean canBuild(String p)
    {
        if (town == null && owner == null)
            return true;
        
        if (town != null && owner == null && (town.hasPermission(p, "town.plot.flag." + getFlag()) || town.hasPermission(p, "town.plot.flag")))
            return true;
        
        if (players.contains(p))
            return true;
        
        if (owner != null && owner.getName().equals(p))
            return true;
        
        for (Group g : groups)
            if (g.getMember().contains(p))
                return true;
        
        return false;
    }
    
    public String getPosiString()
    {
        return getPosition().x + " " + getPosition().y + " " + world;
    }
    
    public void save(FileConfiguration config)
    {
        config.set(getPosiString() + ".owner", owner != null ? owner.getName() : null);
        config.set(getPosiString() + ".town", town != null ? town.getName() : null);
        // config.set(getPosiString() + ".isForSale", isForSale());
        // config.set(getPosiString() + ".price", getPrice());
        config.set(getPosiString() + ".flag", getFlag());
        config.set(getPosiString() + ".player", getPlayers());
        
        List<String> tmp = new LinkedList<String>();
        for (Group g : getGroups())
            tmp.add(g.getHolder().getHolderPrefix() + g.getName());
        
        config.set(getPosiString() + ".group", tmp);
    }
    
    public boolean isNextToTown(Town t)
    {
        return t.equals(getRelative(1, 0).getTown()) || t.equals(getRelative(0, 1).getTown()) || t.equals(getRelative(-1, 0).getTown()) || t.equals(getRelative(0, -1).getTown());
    }
    
    public Plot getRelative(int x, int y)
    {
        return plugin.getPlot(getPosition().x + x, getPosition().y + y, getWorld());
    }
    
    public String getWorld()
    {
        return world;
    }
    
    public String getOwnerName()
    {
        return owner == null ? "none" : owner.getName();
    }
    
    public String getTownName()
    {
        return town == null ? "none" : town.getName();
    }
}
