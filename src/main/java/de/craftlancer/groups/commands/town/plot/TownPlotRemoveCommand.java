package de.craftlancer.groups.commands.town.plot;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlotManager;

public class TownPlotRemoveCommand extends GroupSubCommand
{
    
    public TownPlotRemoveCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        if (args.length < 3)
            return GroupLanguage.COMMAND_GENERAL_ARGUMENTS;
        
        Player p = (Player) sender;
        Plot plot = PlotManager.getPlot(p.getLocation());
        Town t = plot.getTown();
        Faction f = t.getFaction();
        
        if (plot.getTown() == null || plot.getOwner() != null)
            return GroupLanguage.COMMAND_TOWN_PLOT_NOT_OWN;
        if (!plot.getTown().hasPermission(p.getName(), "town.plot.remove"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        
        for (int i = 2; i < args.length; i++)
            if (args[i].startsWith("t:"))
                plot.removeGroup(t.getGroup(args[i].substring(2)));
            else if (args[i].startsWith("f:"))
                plot.removeGroup(f.getGroup(args[i].substring(2)));
            else
                plot.removePlayer(args[i]);
        
        return GroupLanguage.COMMAND_TOWN_PLOT_REMOVE_SUCCESS;
    }
    
    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 0:
            case 1:
            case 2:
                return null;
            default:
                Player p = (Player) sender;
                Plot plot = PlotManager.getPlot(p.getLocation());
                Town t = plot.getTown();
                Faction f = t != null ? t.getFaction() : null;
                
                List<String> tmp = new LinkedList<String>();
                if (t != null)
                    tmp.addAll(Utils.getMatches(args[args.length - 1], t.getGroupNames()));
                if (f != null)
                    tmp.addAll(Utils.getMatches(args[args.length - 1], f.getGroupNames()));
                for (Player op : getPlugin().getServer().getOnlinePlayers())
                    tmp.add(op.getName());
                return tmp;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town plot remove <Spieler...|Rang...> - entziehe Rängen/Spielern die Plotrechte");
    }
    
}
