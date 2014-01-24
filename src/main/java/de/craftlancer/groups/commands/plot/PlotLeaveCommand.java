package de.craftlancer.groups.commands.plot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;

public class PlotLeaveCommand extends GroupSubCommand
{
    public PlotLeaveCommand(String permission, CLGroups plugin)
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
            Plot plot = getPlugin().getPlot(p.getLocation());
            
            if (!plot.isOwner(p))
                sender.sendMessage(GroupLanguage.COMMAND_PLOT_NOTOWNER);
            else
            {
                plot.setOwner(null);
                sender.sendMessage(GroupLanguage.COMMAND_PLOT_LEAVE_SUCCESS);
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/plot leave - Plot entfernen/löschen");
    }
    
}
