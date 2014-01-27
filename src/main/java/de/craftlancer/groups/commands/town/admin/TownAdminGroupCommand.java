package de.craftlancer.groups.commands.town.admin;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.CLPlugin;
import de.craftlancer.core.command.SubCommandHandler;

/*
 * town admin group help
 * town admin group add <town> <group> <player...>
 * town admin group remove <town> <group> <player...>
 * town admin group addperm <town> <group> <perm...>
 * town admin group removeperm <town> <group> <perm...
 * town admin group list <town>
 */
public class TownAdminGroupCommand extends SubCommandHandler
{
    
    public TownAdminGroupCommand(String permission, CLPlugin plugin)
    {
        super(permission, plugin, true);
//        registerSubCommand("help", new TownAdminGroupHelpCommand("clgroups.commands.town.admin.group.help", plugin));
//        registerSubCommand("help", new TownAdminGroupAddCommand("clgroups.commands.town.admin.group.add", plugin));
//        registerSubCommand("help", new TownAdminGroupRemoveCommand("clgroups.commands.town.admin.group.remove", plugin));
//        registerSubCommand("help", new TownAdminGroupAddpermCommand("clgroups.commands.town.admin.group.addperm", plugin));
//        registerSubCommand("help", new TownAdminGroupRemovepermCommand("clgroups.commands.town.admin.group.removeperm", plugin));
//        registerSubCommand("help", new TownAdminGroupListCommand("clgroups.commands.town.admin.group.list", plugin));
    }
    
    @Override
    public void help(CommandSender sender)
    {
        // TODO Auto-generated method stub
        
    }
    
}
