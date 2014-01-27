package de.craftlancer.groups;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lemon42.PvPTimer.PvPTimer;

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
    private static CLGroups instance;
    private ChatManager chat;
    
    private BuildingManager bManager = new BuildingManager(this);
    private Map<String, TownFeature> features = new HashMap<String, TownFeature>();
    
    private FactionDataHandler fhandler;
    private PlotDataHandler phandler;
    private TownDataHandler thandler;
    
    private PvPTimer timer;
    
    @Override
    public void onEnable()
    {
        instance = this;
        
        if (getServer().getPluginManager().getPlugin("PvPTimer") != null)
            timer = (PvPTimer) getServer().getPluginManager().getPlugin("PvPTimer");
        
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
    
    public PvPTimer getPvPTimer()
    {
        return timer;
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
        thandler.load();
        phandler.load();
    }
    
    public void save()
    {
        fhandler.save();
        phandler.save();
        thandler.save();
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
    
    public TownFeature getFeature(String string)
    {
        return features.get(string);
    }
    
    public void addFeature(TownFeature feature)
    {
        features.put(feature.getName(), feature);
    }
    
    public FactionDataHandler getFactionDataHandler()
    {
        return fhandler;
    }
    
    public TownDataHandler getTownDataHandler()
    {
        return thandler;
    }
}
