package de.craftlancer.groups.commands.plot;

import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.Plot;
import de.craftlancer.groups.commands.GroupSubCommand;

public class PlotListCommand extends GroupSubCommand
{
    
    public PlotListCommand(String permission, CLGroups plugin)
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
            Set<Plot> plots = getPlugin().getPlots(p);
            
            sender.sendMessage(GroupLanguage.COMMAND_PLOT_LIST_HEADER);
            StringBuilder str = new StringBuilder();
            for (Plot g : plots)
                str.append(g.getPosiString() + ", ");
            if (str.length() > 2)
                str.delete(str.length() - 2, str.length());
            sender.sendMessage(str.toString());
        }
    }
        
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/plot list - Zeigt alle deine Plots an");
    }
    
}
