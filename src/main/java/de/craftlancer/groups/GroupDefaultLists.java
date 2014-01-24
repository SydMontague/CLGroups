package de.craftlancer.groups;

import java.util.LinkedList;
import java.util.List;

public class GroupDefaultLists
{
    public static List<String> PERMLIST = new LinkedList<String>();
    public static List<String> MAYOR_PERMS = new LinkedList<String>();
    public static List<String> MEMBER_PERMS = new LinkedList<String>();
    public static List<String> LEADER_PERMS = new LinkedList<String>();
    
    static
    {
        PERMLIST.add("faction.rename");
        PERMLIST.add("faction.color");
        PERMLIST.add("faction.disband");
        PERMLIST.add("faction.gifttown");
        PERMLIST.add("faction.setrep");
        PERMLIST.add("faction.tag");
        PERMLIST.add("faction.newtown");
        PERMLIST.add("faction.defaultrep");
        PERMLIST.add("faction.group.add");
        PERMLIST.add("faction.group.addperm");
        PERMLIST.add("faction.group.delete");
        PERMLIST.add("faction.group.add");
        PERMLIST.add("faction.group.info");
        PERMLIST.add("faction.group.list");
        PERMLIST.add("faction.group.new");
        PERMLIST.add("faction.group.remove");
        PERMLIST.add("faction.group.removeperm");
        PERMLIST.add("town.group.add");
        PERMLIST.add("town.group.addperm");
        PERMLIST.add("town.group.delete");
        PERMLIST.add("town.group.info");
        PERMLIST.add("town.group.list");
        PERMLIST.add("town.group.new");
        PERMLIST.add("town.group.remove");
        PERMLIST.add("town.group.removeperm");
        PERMLIST.add("town.plot.add");
        PERMLIST.add("town.plot.assign");
        PERMLIST.add("town.plot.claim");
        PERMLIST.add("town.plot.flag");
        PERMLIST.add("town.plot.info");
        PERMLIST.add("town.plot.remove");
        PERMLIST.add("town.plot.sale");
        PERMLIST.add("town.plot.unclaim");
        PERMLIST.add("town.plot.unsale");
        PERMLIST.add("town.plot.flag.");
        PERMLIST.add("town.disband");
        PERMLIST.add("town.farewell");
        PERMLIST.add("town.invite");
        PERMLIST.add("town.kick");
        PERMLIST.add("town.login");
        PERMLIST.add("town.welcome");
        PERMLIST.add("town.buy");
        PERMLIST.add("town.rename");
    }
    
    static
    {
        MEMBER_PERMS.add("town.group.info");
        MEMBER_PERMS.add("town.group.list");
        MEMBER_PERMS.add("town.plot.info");
        MEMBER_PERMS.add("town.plot.flag.default");
        MEMBER_PERMS.add("town.buy");
        MEMBER_PERMS.add("town.chat");
    }
    
    static
    {
        MAYOR_PERMS.add("town.group.add");
        MAYOR_PERMS.add("town.group.addperm");
        MAYOR_PERMS.add("town.group.delete");
        MAYOR_PERMS.add("town.group.info");
        MAYOR_PERMS.add("town.group.list");
        MAYOR_PERMS.add("town.group.new");
        MAYOR_PERMS.add("town.group.remove");
        MAYOR_PERMS.add("town.group.removeperm");
        MAYOR_PERMS.add("town.plot.add");
        MAYOR_PERMS.add("town.plot.assign");
        MAYOR_PERMS.add("town.plot.claim");
        MAYOR_PERMS.add("town.plot.flag");
        MAYOR_PERMS.add("town.plot.info");
        MAYOR_PERMS.add("town.plot.remove");
        MAYOR_PERMS.add("town.plot.sale");
        MAYOR_PERMS.add("town.plot.unclaim");
        MAYOR_PERMS.add("town.plot.unsale");
        MAYOR_PERMS.add("town.plot.flag.");
        MAYOR_PERMS.add("town.disband");
        MAYOR_PERMS.add("town.farewell");
        MAYOR_PERMS.add("town.invite");
        MAYOR_PERMS.add("town.kick");
        MAYOR_PERMS.add("town.login");
        MAYOR_PERMS.add("town.welcome");
        MAYOR_PERMS.add("town.buy");
        MAYOR_PERMS.add("town.chat");
        MAYOR_PERMS.add("town.rename");
        MAYOR_PERMS.add("faction.chat");
    }
    
    static
    {
        LEADER_PERMS.add("faction.color");
        LEADER_PERMS.add("faction.disband");
        LEADER_PERMS.add("faction.gifttown");
        LEADER_PERMS.add("faction.setrep");
        LEADER_PERMS.add("faction.tag");
        LEADER_PERMS.add("faction.newtown");
        LEADER_PERMS.add("faction.defaultrep");
        LEADER_PERMS.add("faction.chat");
        LEADER_PERMS.add("faction.group.add");
        LEADER_PERMS.add("faction.group.addperm");
        LEADER_PERMS.add("faction.group.delete");
        LEADER_PERMS.add("faction.group.add");
        LEADER_PERMS.add("faction.group.info");
        LEADER_PERMS.add("faction.group.list");
        LEADER_PERMS.add("faction.group.new");
        LEADER_PERMS.add("faction.group.remove");
        LEADER_PERMS.add("faction.group.removeperm");
        LEADER_PERMS.add("faction.chat");
        LEADER_PERMS.add("faction.rename");
    }
}
