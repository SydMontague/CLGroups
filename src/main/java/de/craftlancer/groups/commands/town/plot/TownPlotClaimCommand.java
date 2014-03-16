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
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        
        Player p = (Player) sender;
        Plot plot = PlotManager.getPlot(p.getLocation());
        Town t = PlayerManager.getGroupPlayer(p.getName()).getTown();
        
        if (t == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINTOWN;
        if (plot.getOwner() != null || plot.getTown() != null)
            return GroupLanguage.COMMAND_TOWN_PLOT_CLAIM_OWNED;
        if (!plot.isNextToTown(t))
            return GroupLanguage.COMMAND_TOWN_PLOT_CLAIM_NOTNEXTTOWN;
        if (!t.hasPermission(p.getName(), "town.plot.claim"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        if (!PlotManager.checkPlotLimit(t))
            return GroupLanguage.COMMAND_TOWN_PLOT_CLAIM_LIMIT;
        
        t.addPlot(plot);
        return GroupLanguage.COMMAND_TOWN_PLOT_CLAIM_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town plot claim - Beansprucht ein Plot für deine Stadt");
    }
    
}
