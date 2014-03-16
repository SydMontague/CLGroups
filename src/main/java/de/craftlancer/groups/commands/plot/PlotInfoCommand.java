package de.craftlancer.groups.commands.plot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Group;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlotManager;

public class PlotInfoCommand extends GroupSubCommand
{
    
    public PlotInfoCommand(String permission, CLGroups plugin)
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
        boolean isOwner = plot.isOwner(p);
        
        if (isOwner)
        {
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
            sender.sendMessage("Owner: " + plot.getOwnerName() + " Town: " + plot.getTownName());
            sender.sendMessage("Players: " + pstr);
        }
        else
        {
            sender.sendMessage("Plot info für " + plot.getPosiString());
            sender.sendMessage("Owner: " + plot.getOwnerName() + " Town: " + plot.getTownName());
            sender.sendMessage("Bauerlaubtnis: " + plot.canBuild(p));
        }
        
        return null;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/plot info [x,y] - zeigt Ploteinstellungen");
    }
    
}
