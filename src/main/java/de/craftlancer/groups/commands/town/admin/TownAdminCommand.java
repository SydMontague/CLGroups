package de.craftlancer.groups.commands.town.admin;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.CLPlugin;
import de.craftlancer.core.command.SubCommandHandler;

/*
 * town admin help
 * town admin disband <town>
 * town admin rename <townOld> <townNew>
 * town admin kick <town> <member>
 * town admin welcome <town> <message>
 * town admin farewell <town> <message>
 * town admin login <town> <message>
 * town admin unclaim
 * town admin group add <town> <group> <player...>
 * town admin group remove <town> <group> <player...>
 * town admin group addperm <town> <group> <perm...>
 * town admin group removeperm <town> <group> <perm...
 * town admin group list <town>
 */
public class TownAdminCommand extends SubCommandHandler
{
    
    public TownAdminCommand(String permission, CLPlugin plugin)
    {
        super(permission, plugin, true);
//        registerSubCommand("help", new TownAdminHelpCommand("clgroups.commands.town.admin.help", plugin));
//        registerSubCommand("disband", new TownAdminDisbandCommand("clgroups.commands.town.admin.disband", plugin));
//        registerSubCommand("rename", new TownAdminRenameCommand("clgroups.commands.town.admin.rename", plugin));
//        registerSubCommand("kick", new TownAdminKickCommand("clgroups.commands.town.admin.kick", plugin));
//        registerSubCommand("welcome", new TownAdminWelcomeCommand("clgroups.commands.town.admin.welcome", plugin));
//        registerSubCommand("farewell", new TownAdminFarewellCommand("clgroups.commands.town.admin.farewell", plugin));
//        registerSubCommand("login", new TownAdminLoginCommand("clgroups.commands.town.admin.login", plugin));
//        registerSubCommand("unclaim", new TownAdminUnclaimCommand("clgroups.commands.town.admin.unclaim", plugin));
//        registerSubCommand("group", new TownAdminGroupCommand("clgroups.commands.town.admin.group", plugin));
//        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void help(CommandSender sender)
    {
        // TODO Auto-generated method stub
        
    }
}
