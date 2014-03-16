package de.craftlancer.groups;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kitteh.tag.TagAPI;

import de.craftlancer.groups.chat.Channel;
import de.craftlancer.groups.chat.FactionChannel;
import de.craftlancer.groups.managers.FactionManager;

/*
 * faction:
 *  name: name
 *  defaultRepu: -100
 *  color: WHITE
 *  tag: "[tag]"
 *  towns:
 *      - town
 *  groups:
 *      member:
 *          member:
 *              - player
 *          permissions:
 *              - permission
 *  reputation:
 *      player:
 *          player1: -100
 *          player2: 100
 *      faction:
 *          faction1: -100
 *          faction2: 200
 * 
 */
public class Faction extends GroupHolder implements Repuable
{
    private Set<Town> towns = new HashSet<Town>();
    private Map<String, Reputation> groupRepu = new HashMap<String, Reputation>();
    private Map<String, Reputation> playerRepu = new HashMap<String, Reputation>();
    private Reputation defaultRepu = Reputation.NEUTRAL;
    private ChatColor color = ChatColor.WHITE;
    private String tag = "";
    private Channel chan;
    
    public Faction(CLGroups plugin, String name, Player p)
    {
        super(plugin, name);
        addGroup("leaders", new Group(plugin, name, this, plugin.getDefLeaderPerms(), p.getName()));
        chan = new FactionChannel(getPlugin(), this);
        getPlugin().getChatManager().registerChannel(getChannel());
    }
    
    public Faction(CLGroups plugin, String name, FileConfiguration config)
    {
        super(plugin, name, config.getString(name + ".name"), config);
        defaultRepu = Reputation.valueOf(config.getString(getConfigKey() + ".defaultRepu", Reputation.NEUTRAL.name()));
        color = ChatColor.valueOf(config.getString(getConfigKey() + ".color", "WHITE"));
        tag = config.getString(getConfigKey() + ".tag");
        
        if (config.getConfigurationSection(getConfigKey() + ".reputation.player") != null)
            for (String s : config.getConfigurationSection(getConfigKey() + ".reputation.player").getKeys(false))
                playerRepu.put(s, Reputation.valueOf(config.getString(getConfigKey() + ".reputation.player." + s, Reputation.NEUTRAL.name())));
        
        if (config.getConfigurationSection(getConfigKey() + ".reputation.faction") != null)
            for (String s : config.getConfigurationSection(getConfigKey() + ".reputation.faction").getKeys(false))
                groupRepu.put(s, Reputation.valueOf(config.getString(getConfigKey() + ".reputation.faction." + s, Reputation.NEUTRAL.name())));
        
        chan = new FactionChannel(getPlugin(), this);
        getPlugin().getChatManager().registerChannel(getChannel());
    }
    
    public Channel getChannel()
    {
        return chan;
    }
    
    public void setReputation(GroupPlayer gp, Reputation repu)
    {
        playerRepu.put(gp.getName(), repu);
        
        Player p = getPlugin().getServer().getPlayerExact(gp.getName());
        if (p != null)
            for (String s : getMember())
            {
                OfflinePlayer op = getPlugin().getServer().getOfflinePlayer(s);
                if (op.isOnline())
                {
                    TagAPI.refreshPlayer(p, op.getPlayer());
                    TagAPI.refreshPlayer(op.getPlayer(), p);
                }
            }
        
    }
    
    public void setReputation(Faction f, Reputation repu)
    {
        groupRepu.put(f.getName(), repu);
        
        for (String ownMember : getMember())
        {
            OfflinePlayer op = getPlugin().getServer().getOfflinePlayer(ownMember);
            if (op.isOnline())
                for (String i : f.getMember())
                {
                    OfflinePlayer fp = getPlugin().getServer().getOfflinePlayer(i);
                    if (fp.isOnline())
                    {
                        TagAPI.refreshPlayer(fp.getPlayer(), op.getPlayer());
                        TagAPI.refreshPlayer(op.getPlayer(), fp.getPlayer());
                    }
                }
        }
    }
    
    public Set<String> getMember()
    {
        Set<String> tmp = new HashSet<String>();
        for (Town t : towns)
            tmp.addAll(t.getMember());
        
        return tmp;
    }
    
    public boolean hasMember(Player p)
    {
        return hasMember(p.getName());
    }
    
    public boolean hasMember(String p)
    {
        for (Town t : towns)
            if (t.hasMember(p))
                return true;
        
        return false;
    }
    
    public Set<Town> getTowns()
    {
        return towns;
    }
    
    public Reputation getReputation(Player p)
    {
        return getReputation(p.getName());
    }
    
    public Reputation getReputation(Repuable rep)
    {
        if (rep instanceof GroupPlayer)
            return getReputation(((GroupPlayer) rep).getName());
        else if (rep instanceof Faction)
            return getReputation((Faction) rep);
        else
        {
            getPlugin().getLogger().warning("Tried to get Reputation of unkown Repuable object!");
            return getDefaultRepu();
        }
    }
    
    public Reputation getReputation(String p)
    {
        Faction f = FactionManager.getPlayerGroup(p);
        
        if (hasMember(p))
            return Reputation.OWN_FACTION;
        else if (playerRepu.containsKey(p))
            return playerRepu.get(p);
        else
            return getReputation(f);
    }
    
    public Reputation getReputation(Faction f)
    {
        if (f != null && groupRepu.containsKey(f.getName()))
            return groupRepu.get(f.getName());
        else
            return getDefaultRepu();
    }
    
    public Reputation getDefaultRepu()
    {
        return defaultRepu;
    }
    
    public void setDefaultRepu(Reputation defaultRepu)
    {
        this.defaultRepu = defaultRepu;
        for (Player p : getPlugin().getServer().getOnlinePlayers())
            for (String m : getMember())
            {
                if (m.equals(p.getName()))
                    continue;
                OfflinePlayer op = getPlugin().getServer().getOfflinePlayer(m);
                if (op.isOnline())
                {
                    TagAPI.refreshPlayer(p, op.getPlayer());
                    TagAPI.refreshPlayer(op.getPlayer(), p);
                }
            }
    }
    
    public boolean hasTown(Town town)
    {
        return towns.contains(town);
    }
    
    public void removeTown(Town town)
    {
        towns.remove(town);
    }
    
    public void addTown(Town town)
    {
        towns.add(town);
    }
    
    public void sendMessage(String string)
    {
        for (Town t : towns)
            t.sendMessage(string);
    }
    
    public void setColor(ChatColor color)
    {
        this.color = color;
    }
    
    public ChatColor getColor()
    {
        return color;
    }
    
    public void removeReputation(Faction faction)
    {
        groupRepu.remove(faction);
    }
    
    public void clear()
    {
        groupRepu.clear();
        playerRepu.clear();
    }
    
    public String getTag()
    {
        return tag;
    }
    
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    
    @Override
    public void save(FileConfiguration config)
    {
        super.save(config);
        config.set(getConfigKey() + ".name", getName());
        config.set(getConfigKey() + ".defaultRepu", getDefaultRepu().name());
        config.set(getConfigKey() + ".color", getColor().name());
        config.set(getConfigKey() + ".tag", getTag());
        
        for (Entry<String, Reputation> e : playerRepu.entrySet())
            config.set(getConfigKey() + ".reputation.player." + e.getKey(), e.getValue().name());
        for (Entry<String, Reputation> e : groupRepu.entrySet())
            config.set(getConfigKey() + ".reputation.faction." + e.getKey(), e.getValue().name());
    }
    
    public List<String> getTownNames()
    {
        List<String> tmp = new LinkedList<String>();
        for (Town t : getTowns())
            tmp.add(t.getName());
        
        return tmp;
    }
    
    @Override
    public String getHolderPrefix()
    {
        return "f:";
    }
    
    public String getFormattedTag()
    {
        return getTag() == null || getTag().isEmpty() ? "" : "[" + getTag() + "] ";
    }
    
    public void setReputation(Repuable repuable, Reputation repu)
    {
        if (repuable instanceof Faction)
            setReputation((Faction) repuable, repu);
        else if (repuable instanceof GroupPlayer)
            setReputation((GroupPlayer) repuable, repu);
        else
            getPlugin().getLogger().warning("Tried to set Reputation of unknown Repuable object.");
    }
    
    public List<String> getLeaders()
    {
        return getGroup("leaders").getMember();
    }
    
    public void updateRepu(String oldName, String newName)
    {
        if (groupRepu.containsKey(oldName))
        {
            groupRepu.put(newName, groupRepu.get(oldName));
            groupRepu.remove(oldName);
        }
    }
    
    public void disband()
    {
        FactionManager.disbandFaction(this);
    }
    
    public void rename(String newName)
    {
        FactionManager.renameFaction(this, newName);
        setName(newName);
    }
}
