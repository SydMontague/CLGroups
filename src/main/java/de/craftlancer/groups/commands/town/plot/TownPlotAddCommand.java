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

public class TownPlotAddCommand extends GroupSubCommand
{
    
    public TownPlotAddCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 3)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else
        {
            Player p = (Player) sender;
            Plot plot = getPlugin().getPlot(p.getLocation());
            Town t = plot.getTown();
            Faction f = t.getFaction();
            
            if (plot.getTown() == null || plot.getOwner() != null)
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_NOT_OWN);
            else if (!plot.getTown().hasPermission(p.getName(), "town.plot.add"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else
            {
                for (int i = 2; i < args.length; i++)
                    if (args[i].startsWith("t:"))
                        plot.addGroup(t.getGroup(args[i].substring(2)));
                    else if (args[i].startsWith("f:"))
                        plot.addGroup(f.getGroup(args[i].substring(2)));
                    else
                        plot.addPlayer(args[i]);
                
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_ADD_SUCCESS);
            }
        }
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
                if (!(sender instanceof Player))
                    return null;
                Player p = (Player) sender;
                Plot plot = getPlugin().getPlot(p.getLocation());
                Town t = plot.getTown();
                Faction f = t.getFaction();
                
                List<String> tmp = new LinkedList<String>();
                tmp.addAll(Utils.getMatches(args[args.length - 1], t.getGroupNames()));
                tmp.addAll(Utils.getMatches(args[args.length - 1], f.getGroupNames()));
                for (Player op : getPlugin().getServer().getOnlinePlayers())
                    tmp.add(op.getName());
                return tmp;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town plot add <Spieler/Rang...> - Schalte Ränge/Spieler für den Plot frei ");
    }
    
}
