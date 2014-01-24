package de.craftlancer.groups.questions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;

public abstract class Question
{
    private CLGroups plugin;
    private long time = System.currentTimeMillis();
    private Set<CommandSender> senderSet;
    
    public Question(CLGroups plugin, CommandSender sender)
    {
        senderSet = new HashSet<CommandSender>();
        senderSet.add(sender);
        this.plugin = plugin;
    }
    
    public Question(Set<CommandSender> senderSet)
    {
        this.senderSet = senderSet;
    }
    
    public CLGroups getPlugin()
    {
        return plugin;
    }
    
    public long getLifetime()
    {
        return System.currentTimeMillis() - time;
    }
    
    public Set<CommandSender> getSender()
    {
        return senderSet;
    }
    
    public boolean isAsked(CommandSender sender)
    {
        return senderSet.contains(sender);
    }
    
    public abstract void ask();
    
    public abstract void execute();
    
    public abstract void cancel();
}
