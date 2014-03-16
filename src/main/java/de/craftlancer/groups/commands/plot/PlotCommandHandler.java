package de.craftlancer.groups.commands.plot;

import de.craftlancer.core.command.CommandHandler;
import de.craftlancer.groups.CLGroups;

/*
 * plot add <players...> - add players to plot
 * plot remove <players...> - add players to plot
 * plot help [command] - command list and command help
 * plot info [x,y] - show plot settings
 * plot buy - buy plot as player
 * plot sale <price> - set plot for sale as player
 * plot unsale - remove sale status from plot
 * plot leave - unclaim plot
 * plot gift <player> - gift plot to a player
 * plot list [page] - shows all owned plots and some settings
 * 
 * 
 * plot mindestabstand zu stadtplots
 * plot mindestabstand zu anderen plots (außer berechtigung)
 * max 4 plots für nicht-stadtspieler
 */
public class PlotCommandHandler extends CommandHandler
{
    public PlotCommandHandler(CLGroups plugin)
    {
        registerSubCommand("help", new PlotHelpCommand("clgroups.commands.plot.help", plugin, getCommands()));
        registerSubCommand("info", new PlotInfoCommand("clgroups.commands.plot.info", plugin));
        registerSubCommand("claim", new PlotClaimCommand("clgroups.commands.plot.buy", plugin), "buy");
        // registerSubCommand("sale", new
        // PlotSaleCommand("clgroups.commands.plot.sell", plugin));
        // registerSubCommand("unsale", new
        // PlotUnsaleCommand("clgroups.commands.plot.unsale", plugin));
        registerSubCommand("leave", new PlotLeaveCommand("clgroups.commands.plot.leave", plugin));
        registerSubCommand("gift", new PlotGiftCommand("clgroups.commands.plot.gift", plugin));
        registerSubCommand("add", new PlotAddPlayerCommand("clgroups.commands.plot.add", plugin));
        registerSubCommand("remove", new PlotRemovePlayerCommand("clgroups.commands.plot.remove", plugin));
        registerSubCommand("list", new PlotListCommand("clgroups.commands.plot.list", plugin));
    }
    
}
