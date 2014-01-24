package de.craftlancer.groups;

import java.util.LinkedList;
import java.util.List;

public class Group
{
    private final GroupHolder holder;
    private String name;
    private List<String> member = new LinkedList<String>();
    private List<String> perms = new LinkedList<String>();
    private CLGroups plugin;
    
    public Group(CLGroups plugin, String name, GroupHolder holder)
    {
        this.plugin = plugin;
        this.name = name;
        this.holder = holder;
    }
    
    public Group(CLGroups plugin, String name, GroupHolder holder, List<String> perms, String... members)
    {
        this.name = name;
        this.perms = perms;
        for (String s : members)
            member.add(s);
        this.plugin = plugin;
        this.holder = holder;
    }
    
    public Group(CLGroups plugin, String name, GroupHolder holder, List<String> perms, List<String> member)
    {
        this.name = name;
        this.perms = perms;
        this.plugin = plugin;
        this.member = member;
        this.holder = holder;
    }
    
    public String getName()
    {
        return name;
    }
    
    public List<String> getMember()
    {
        return member;
    }
    
    public boolean hasPlayer(String str)
    {
        return member.contains(str);
    }
    
    public void addPlayer(String string)
    {
        member.add(string);
    }
    
    protected CLGroups getPlugin()
    {
        return plugin;
    }
    
    public boolean hasPermission(String p, String perm)
    {
        return hasPlayer(p) && hasPermission(perm);
    }
    
    public boolean hasPermission(String perm)
    {
        return perms.contains(perm);
    }
    
    public boolean removePermission(String perm)
    {
        return perms.remove(perm);
    }
    
    public boolean addPermission(String perm)
    {
        return perms.add(perm);
    }
    
    public void removePlayer(String string)
    {
        member.remove(string);
    }
    
    public List<String> getPermissions()
    {
        return perms;
    }
    
    public GroupHolder getHolder()
    {
        return holder;
    }
}
