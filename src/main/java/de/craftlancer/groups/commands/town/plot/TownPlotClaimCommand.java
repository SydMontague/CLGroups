package de.craftlancer.groups.commands.town.plot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;
import de.craftlancer.groups.managers.PlotManager;

public class TownPlotClaimCommand extends GroupSubCommand
{
    
    public TownPlotClaimCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else
        {
            Player p = (Player) sender;
            Plot plot = PlotManager.getPlot(p.getLocation());
            Town t = PlayerManager.getGroupPlayer(p.getName()).getTown();
            
            if (t == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINTOWN);
            else if (plot.getOwner() != null || plot.getTown() != null)
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_CLAIM_OWNED);
            else if (!plot.isNextToTown(t))
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_CLAIM_NOTNEXTTOWN);
            else if (!t.hasPermission(p.getName(), "town.plot.claim"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
            else if (!PlotManager.checkPlotLimit(t))
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_CLAIM_LIMIT);
            else
            {
                t.addPlot(plot);
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_PLOT_CLAIM_SUCCESS);
            }
            
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town plot claim - Beansprucht ein Plot für deine Stadt");
    }
    
}
