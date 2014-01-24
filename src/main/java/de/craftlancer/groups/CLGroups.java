package de.craftlancer.groups;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.craftlancer.core.CLPlugin;
import de.craftlancer.groups.buildings.BuildingManager;
import de.craftlancer.groups.chat.ChatManager;
import de.craftlancer.groups.commands.chat.ChannelCommandHandler;
import de.craftlancer.groups.commands.chat.MsgCommand;
import de.craftlancer.groups.commands.chat.ReCommand;
import de.craftlancer.groups.commands.faction.FactionCommandHandler;
import de.craftlancer.groups.commands.plot.PlotCommandHandler;
import de.craftlancer.groups.commands.town.TownCommandHandler;
import de.craftlancer.groups.data.FactionDataHandler;
import de.craftlancer.groups.data.PlotDataHandler;
import de.craftlancer.groups.data.TownDataHandler;
import de.craftlancer.groups.data.config.FactionConfigHandler;
import de.craftlancer.groups.data.config.PlotConfigHandler;
import de.craftlancer.groups.data.config.TownConfigHandler;
import de.craftlancer.groups.questions.QuestionListener;

//TODO Stadtbefehl - Fraktion wechseln
//TODO Stadtbefehl - eigene Fraktion gründen
//TODO anzeigen welche Farben noch Frei sind
public class CLGroups extends JavaPlugin implements CLPlugin
{
    private static final int MAX_PLOTS = 5;
    private static CLGroups instance;
    public Map<String, Faction> factions = new HashMap<String, Faction>();
    private Map<Point, Plot> plots = new HashMap<Point, Plot>();
    private Map<String, GroupPlayer> gplayers = new HashMap<String, GroupPlayer>();
    private Map<String, Town> towns = new HashMap<String, Town>();
    private int TOWN_DISTANCE = 3;
    private int NORMAL_DISTANCE = 3;
    private ChatManager chat;
    
    private BuildingManager bManager = new BuildingManager(this);
    private Map<String, TownFeature> features = new HashMap<String, TownFeature>();
    
    private FactionDataHandler fhandler;
    private PlotDataHandler phandler;
    private TownDataHandler thandler;
    
    @Override
    public void onEnable()
    {
        instance = this;
        
        fhandler = FactionConfigHandler.getInstance();
        phandler = PlotConfigHandler.getInstance();
        thandler = TownConfigHandler.getInstance();
        
        chat = new ChatManager(this);
        load();
        
        getServer().getPluginManager().registerEvents(bManager, this);
        getServer().getPluginManager().registerEvents(new GroupListener(this), this);
        getServer().getPluginManager().registerEvents(new QuestionListener(this), this);
        
        getCommand("faction").setExecutor(new FactionCommandHandler(this));
        getCommand("plot").setExecutor(new PlotCommandHandler(this));
        getCommand("town").setExecutor(new TownCommandHandler(this));
        getCommand("ch").setExecutor(new ChannelCommandHandler(this));
        getCommand("msg").setExecutor(new MsgCommand(this));
        getCommand("re").setExecutor(new ReCommand(this));
        new GroupTask(this).runTaskTimer(this, 10L, 10L);
    }
    
    @Override
    public void onDisable()
    {
        getServer().getScheduler().cancelTasks(this);
        
        for (Player p : getServer().getOnlinePlayers())
        {
            bManager.stopBuilding(p);
        }
        
        save();
        
    }
    
    public BuildingManager getBuildingManager()
    {
        return bManager;
    }
    
    public static CLGroups getInstance()
    {
        return instance;
    }
    
    public ChatManager getChatManager()
    {
        return chat;
    }
    
    public void load()
    {
        fhandler.load();
        phandler.load();
        thandler.load();
        /*
         * File facFile = new File(getDataFolder(), "factions.yml");
         * FileConfiguration facConfig =
         * YamlConfiguration.loadConfiguration(facFile);
         * File plotFile = new File(getDataFolder(), "plots.yml");
         * FileConfiguration plotConfig =
         * YamlConfiguration.loadConfiguration(plotFile);
         * File townFile = new File(getDataFolder(), "towns.yml");
         * FileConfiguration townConfig =
         * YamlConfiguration.loadConfiguration(townFile);
         * File featureFile = new File(getDataFolder(), "feature.yml");
         * FileConfiguration featureConfig =
         * YamlConfiguration.loadConfiguration(featureFile);
         * for (String s : facConfig.getKeys(false))
         * addFaction(new Faction(this, s, facConfig));
         * for (String s : townConfig.getKeys(false))
         * addTown(new Town(this, s, townConfig)); // TODO load buildings of
         * // out towns.yml
         * for (String s : plotConfig.getKeys(false))
         * addPlot(Utils.parsePointString(s), new Plot(this, s, plotConfig));
         * for (String s : featureConfig.getKeys(false))
         * {
         * addFeature(FeatureFactory.getFeature(this, s, featureConfig));
         * }
         */
    }
    
    /*
     * private void addFeature(TownFeature feature)
     * {
     * features.put(feature.getName(), feature);
     * }
     */
    
    public void addPlot(Point point, Plot plot)
    {
        plots.put(point, plot);
    }
    
    public void save()
    {
        fhandler.save();
        phandler.save();
        thandler.save();
        /*
         * File facFile = new File(getDataFolder(), "factions.yml");
         * FileConfiguration facConfig =
         * YamlConfiguration.loadConfiguration(facFile);
         * File plotFile = new File(getDataFolder(), "plots.yml");
         * FileConfiguration plotConfig =
         * YamlConfiguration.loadConfiguration(plotFile);
         * File townFile = new File(getDataFolder(), "towns.yml");
         * FileConfiguration townConfig =
         * YamlConfiguration.loadConfiguration(townFile);
         * for (Faction f : factions.values())
         * f.save(facConfig);
         * for (Plot f : plots.values())
         * f.save(plotConfig);
         * for (Town f : towns.values())
         * f.save(townConfig);
         * facConfig.save(facFile);
         * plotConfig.save(plotFile);
         * townConfig.save(townFile);
         */
    }
    
    public Faction getFaction(String name)
    {
        return factions.get(name);
    }
    
    public Faction getPlayerGroup(Player player)
    {
        return getPlayerGroup(player.getName());
    }
    
    public Faction getPlayerGroup(String name)
    {
        for (Faction g : factions.values())
            if (g.hasMember(name))
                return g;
        
        return null;
    }
    
    public List<String> getDefMemberPerms()
    {
        return GroupDefaultLists.MEMBER_PERMS;
    }
    
    public List<String> getDefMayorPerms()
    {
        return GroupDefaultLists.MAYOR_PERMS;
    }
    
    public List<String> getDefLeaderPerms()
    {
        return GroupDefaultLists.LEADER_PERMS;
    }
    
    public Plot getPlot(Location loc)
    {
        return getPlot(loc.getChunk().getX(), loc.getChunk().getZ(), loc.getWorld().getName());
    }
    
    public Plot getPlot(int x, int z, String world)
    {
        Point p = new Point(x, z);
        if (!plots.containsKey(p))
            plots.put(p, new Plot(p, world, this));
        
        return plots.get(p);
    }
    
    public boolean checkPlotDistance(Plot plot, Player player)
    {
        if (plot.isTownPlot())
            return true;
        
        int maxdistance = TOWN_DISTANCE >= NORMAL_DISTANCE ? TOWN_DISTANCE : NORMAL_DISTANCE;
        
        for (int i = -maxdistance; i <= maxdistance; i++)
            for (int j = -maxdistance; j <= maxdistance; j++)
            {
                Plot p = plot.getRelative(i, j);
                if (p.getOwner() != null && p.getOwner().getName().equals(player.getName()))
                    continue;
                
                double distance = p.distance(plot);
                
                if ((p.isTownPlot() && distance <= TOWN_DISTANCE) || (p.isPlayerPlot() && distance <= NORMAL_DISTANCE))
                    return true;
            }
        
        return false;
    }
    
    public GroupPlayer getGroupPlayer(String p)
    {
        if (p == null)
            return null;
        
        if (!hasGroupPlayer(p))
            gplayers.put(p, new GroupPlayer(p));
        
        return gplayers.get(p);
    }
    
    public boolean hasGroupPlayer(String p)
    {
        return gplayers.containsKey(p);
    }
    
    public Set<Plot> getPlots(Player p)
    {
        if (hasGroupPlayer(p.getName()))
            return getGroupPlayer(p.getName()).getPlots();
        
        return null;
    }
    
    public void disbandTown(Town town)
    {
        if (town == null)
            throw new NullPointerException("Town can't be null!");
        
        for (Plot t : town.getPlots())
            t.reset();
        
        for (GroupPlayer gp : gplayers.values())
            if (town.equals(gp.getTown()))
                gp.setTown(null);
        
        for (Faction f : factions.values())
            if (f.hasTown(town))
            {
                f.removeTown(town);
                break;
            }
        
        thandler.onTownDisband(town);
        town.clear();
        towns.remove(town.getName());
    }
    
    public void disbandFaction(Faction faction)
    {
        if (faction == null)
            throw new NullPointerException("Faction can't be null!");
        
        for (Town t : faction.getTowns())
            disbandTown(t);
        
        for (Faction f : factions.values())
            f.removeReputation(faction);
        
        fhandler.onFactionDisband(faction);
        faction.clear();
        factions.remove(faction.getName());
    }
    
    public void renameFaction(Faction faction, String newName)
    {
        String oldName = faction.getName();
        factions.remove(oldName);
        factions.put(newName, faction);
        faction.setName(newName);
        
        for (Faction f : factions.values())
            f.updateRepu(oldName, newName);
    }
    
    public boolean hasTown(String string)
    {
        return towns.containsKey(string);
    }
    
    public Town getTown(String string)
    {
        return towns.get(string);
    }
    
    public Set<String> getTownNames()
    {
        Set<String> tmp = new HashSet<String>();
        
        for (Entry<String, Town> e : towns.entrySet())
            tmp.add(e.getValue().getFaction().getColor() + e.getKey() + " (" + e.getValue().getFaction().getName() + ")");
        
        return tmp;
    }
    
    public void addTown(Town town)
    {
        towns.put(town.getName(), town);
    }
    
    public void renameTown(Town town, String newName)
    {
        String oldName = town.getName();
        towns.remove(oldName);
        towns.put(newName, town);
        town.setName(newName);
    }
    
    public boolean isColorTaken(ChatColor color)
    {
        if (color == ChatColor.WHITE)
            return false;
        
        for (Faction f : factions.values())
            if (color == f.getColor())
                return true;
        
        return false;
    }
    
    public boolean isTagTaken(String tag)
    {
        if (tag.equals(""))
            return false;
        
        for (Faction f : factions.values())
            if (tag.equalsIgnoreCase(f.getTag()))
                return true;
        
        return false;
    }
    
    public boolean hasFaction(String string)
    {
        return factions.containsKey(string);
    }
    
    public void addFaction(Faction faction)
    {
        factions.put(faction.getName(), faction);
    }
    
    public Set<String> getFactionNames()
    {
        Set<String> tmp = new HashSet<String>();
        
        for (Entry<String, Faction> e : factions.entrySet())
            tmp.add(e.getValue().getColor() + e.getKey());
        
        return tmp;
    }
    
    public Plot getPlot(Point point)
    {
        return plots.get(point);
    }
    
    public Collection<Faction> getFactions()
    {
        return factions.values();
    }
    
    public boolean checkPlotLimit(Player p)
    {
        return checkPlotLimit(p.getName());
    }
    
    public boolean checkPlotLimit(String name)
    {
        return getGroupPlayer(name).getPlotCount() >= MAX_PLOTS;
    }
    
    public boolean checkPlotLimit(Town t)
    {
        return t.getPlots().size() < t.getMember().size() * 20;
    }
    
    public TownFeature getFeature(String string)
    {
        return features.get(string);
    }
    
    public Collection<Town> getTowns()
    {
        return towns.values();
    }
    
    public Collection<Plot> getPlots()
    {
        return plots.values();
    }
}
