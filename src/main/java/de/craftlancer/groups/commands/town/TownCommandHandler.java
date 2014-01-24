package de.craftlancer.groups.commands.town;

import de.craftlancer.core.command.CommandHandler;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.commands.town.admin.TownAdminCommand;

/*
 * town                         - info of your own town
 * town help                    - help
 * town info [town]             - info of the town you're standing in or of town [town]
 * town new <name>              - create new town with name <name>
 * town invite <player>         - invite <player> to your town
 * town kick <player>           - kick <player> out of your town
 * town list                    - list all towns
 * town disband                 - disband the town
 * town welcome <msg>           - sets a welcome message, shown to players who cross your borders
 * town farewell <msg>          - sets a farewell message, shown to players who leave your borders
 * town login <msg>             - sets a login message for all town members
 * town leave                   - leave your current town, you lose all rights and plots!
 * town plot claim              - claim plot for your town
 * town plot unclaim            - unclaim a plot from your town
 * town plot info               - info for this plot
 * town plot flag <flag>        - set a flag, used for permissions - default is "default", which is owned by the "member" group
 * town plot add <player...|groups...>    - add player to plot's buildlist
 * town plot remove <player...|groups...> - remove player to plot's buildlist
 * town plot assign <player>    - set <player> as owner of this plot
 * town plot sale <price>       - sets a plot for sale
 * town plot unsale             - removes a plot from sale
 * town group new <name>        - create a new town-group
 * town group delete <name>     - delete a existing town-group
 * town group list              - list all town groups
 * town group info <name>       - get info of town-group
 * town group add <name> <player...>        - add players to town-group
 * town group remove <name> <player...>     - remove players from town-group
 * town group addperm <name> <perm...>      - add perms to group
 * town group removeperm <name> <perm...>   - remove perms from group
 * 
 * t:group - add a town group
 * f:group - add a faction group
 */
public class TownCommandHandler extends CommandHandler
{
    public TownCommandHandler(CLGroups plugin)
    {
        registerSubCommand("help", new TownHelpCommand("clgroups.commands.town.help", plugin, getCommands()));
        registerSubCommand("info", new TownInfoCommand("clgroups.commands.town.info", plugin));
        registerSubCommand("new", new TownNewCommand("clgroups.commands.town.new", plugin));
        registerSubCommand("rename", new TownRenameCommand("clgroups.commands.town.rename", plugin));
        registerSubCommand("invite", new TownInviteCommand("clgroups.commands.town.invite", plugin));
        registerSubCommand("kick", new TownKickCommand("clgroups.commands.town.kick", plugin));
        registerSubCommand("leave", new TownLeaveCommand("clgroups.commands.town.leave", plugin));
        registerSubCommand("list", new TownListCommand("clgroups.commands.town.list", plugin));
        registerSubCommand("disband", new TownDisbandCommand("clgroups.commands.town.disband", plugin));
        registerSubCommand("welcome", new TownWelcomeCommand("clgroups.commands.town.welcome", plugin));
        registerSubCommand("farewell", new TownFarewellCommand("clgroups.commands.town.farewell", plugin));
        registerSubCommand("login", new TownLoginCommand("clgroups.commands.town.login", plugin));
        registerSubCommand("plot", new TownPlotCommand("clgroups.commands.town.plot", plugin));
        registerSubCommand("group", new TownGroupCommand("clgroups.commands.town.group", plugin));
        registerSubCommand("build", new TownBuildCommand("clgroups.commands.town.build", plugin));
        registerSubCommand("admin", new TownAdminCommand("clgroups.commands.town.admin", plugin));
    }
}
