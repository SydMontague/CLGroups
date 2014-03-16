package de.craftlancer.groups.commands.town.plot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Group;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlotManager;

public class TownPlotInfoCommand extends GroupSubCommand
{
    
    public TownPlotInfoCommand(String permission, CLGroups plugin)
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
        
        if (plot.getTown() == null)
            return GroupLanguage.COMMAND_TOWN_PLOT_NOT_OWN;
        if (!plot.getTown().hasPermission(p.getName(), "town.plot.info"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        
        StringBuilder pstr = new StringBuilder();
        StringBuilder gstr = new StringBuilder();
        for (String s : plot.getPlayers())
            pstr.append(s + ", ");
        if (pstr.length() > 2)
            pstr.delete(pstr.length() - 2, pstr.length());
        for (Group s : plot.getGroups())
            gstr.append(s.getName() + ", ");
        if (gstr.length() > 2)
            gstr.delete(gstr.length() - 2, gstr.length());
        
        sender.sendMessage("Plot info für " + plot.getPosiString());
        sender.sendMessage("Owner: " + plot.getOwnerName() + " Flag: " + plot.getFlag());
        sender.sendMessage("Players: " + pstr);
        sender.sendMessage("Groups: " + gstr);
        return null;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town plot info - Zeigt die Ploteinstellungen an");
    }
    
}
