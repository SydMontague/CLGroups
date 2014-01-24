package de.craftlancer.groups.commands.faction;

import de.craftlancer.core.command.CommandHandler;
import de.craftlancer.groups.CLGroups;

/*
 * faction - info about your faction
 * faction help [command] - help command
 * faction info [faction] - info about the faction you're standing or [faction]
 * faction new <name> <town> - creates a new faction with a town at your location
 * faction setrep <faction/player> <value> - set reputation to faction (p: or f: prefix for ambigous names)
 * faction showrep [player/faction] - show reputation of your faction, or to player/faction specific
 * faction disband - disbands your faction, only possible if only one town is left
 * faction gift <town> <faction> - hand over a town to another faction
 * faction merge <faction> - merge your faction with another faction, faction groups and assignments will be lost
 * faction list - show list of all available factions
 * faction color <color> - set the color of your faction, shown in chat - must be unique?
 * faction tag <tag> - set a faction tag, shown in chat
 * 
 * faction group new <name>        - create a new town-group
 * faction group delete <name>     - delete a existing town-group
 * faction group list              - list all town groups
 * faction group info <name>       - get info of town-group
 * faction group add <name> <player...>        - add players to town-group
 * faction group remove <name> <player...>     - remove players from town-group
 * faction group addperm <name> <perm...>      - add perms to group
 * faction group removeperm <name> <perm...>   - remove perms from group
 * 
 */
public class FactionCommandHandler extends CommandHandler
{
    public FactionCommandHandler(CLGroups plugin)
    {
        registerSubCommand("help", new FactionHelpCommand("clgroups.commands.faction.help", plugin, getCommands()));
        registerSubCommand("info", new FactionInfoCommand("clgroups.commands.faction.info", plugin));
        registerSubCommand("new", new FactionNewCommand("clgroups.commands.faction.new", plugin));
        registerSubCommand("rename", new FactionRenameCommand("clgroups.commands.faction.rename", plugin));
        registerSubCommand("setrep", new FactionSetrepCommand("clgroups.commands.faction.setrep", plugin));
        registerSubCommand("defaultrep", new FactionDefaultrepCommand("clgroups.commands.faction.defaultrep", plugin));
        registerSubCommand("showrep", new FactionShowrepCommand("clgroups.commands.faction.showrep", plugin));
        registerSubCommand("disband", new FactionDisbandCommand("clgroups.commands.faction.disband", plugin));
        registerSubCommand("gift", new FactionGiftCommand("clgroups.commands.faction.gift", plugin));
        registerSubCommand("merge", new FactionMergeCommand("clgroups.commands.faction.merge", plugin));
        registerSubCommand("list", new FactionListCommand("clgroups.commands.faction.list", plugin));
        registerSubCommand("color", new FactionColorCommand("clgroups.commands.faction.color", plugin));
        registerSubCommand("tag", new FactionTagCommand("clgroups.commands.faction.tag", plugin));
        registerSubCommand("group", new FactionGroupCommand("clgroups.commands.faction.group", plugin));
    }
}
