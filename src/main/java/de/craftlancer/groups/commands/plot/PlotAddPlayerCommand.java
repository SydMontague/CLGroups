package de.craftlancer.groups.commands.plot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlotManager;

public class PlotAddPlayerCommand extends GroupSubCommand
{
    
    public PlotAddPlayerCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        if (args.length < 2)
            return GroupLanguage.COMMAND_GENERAL_ARGUMENTS;
        
        Player p = (Player) sender;
        Plot plot = PlotManager.getPlot(p.getLocation());
        
        if (!plot.isOwner(p))
            return GroupLanguage.COMMAND_PLOT_NOTOWNER;
        
        for (int i = 1; i < args.length; i++)
            plot.addPlayer(args[i]);
        
        return GroupLanguage.COMMAND_PLOT_ADD_SUCCESS;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/plot add <Spieler...> - gebe Spielern Baurechte auf diesem Plot");
    }
    
}
