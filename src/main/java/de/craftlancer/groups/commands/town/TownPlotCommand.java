package de.craftlancer.groups.commands.town;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.command.SubCommandHandler;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.commands.town.plot.TownPlotAddCommand;
import de.craftlancer.groups.commands.town.plot.TownPlotAssignCommand;
import de.craftlancer.groups.commands.town.plot.TownPlotClaimCommand;
import de.craftlancer.groups.commands.town.plot.TownPlotFlagCommand;
import de.craftlancer.groups.commands.town.plot.TownPlotHelpCommand;
import de.craftlancer.groups.commands.town.plot.TownPlotInfoCommand;
import de.craftlancer.groups.commands.town.plot.TownPlotRemoveCommand;
import de.craftlancer.groups.commands.town.plot.TownPlotUnclaimCommand;

public class TownPlotCommand extends SubCommandHandler
{
    
    public TownPlotCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
        registerSubCommand("help", new TownPlotHelpCommand("clgroups.commands.town.plot.help", plugin, getCommands()));
        registerSubCommand("claim", new TownPlotClaimCommand("clgroups.commands.town.plot.claim", plugin));
        registerSubCommand("unclaim", new TownPlotUnclaimCommand("clgroups.commands.town.plot.unclaim", plugin));
        registerSubCommand("info", new TownPlotInfoCommand("clgroups.commands.town.plot.info", plugin));
        registerSubCommand("flag", new TownPlotFlagCommand("clgroups.commands.town.plot.flag", plugin));
        registerSubCommand("add", new TownPlotAddCommand("clgroups.commands.town.plot.add", plugin));
        registerSubCommand("remove", new TownPlotRemoveCommand("clgroups.commands.town.plot.remove", plugin));
        registerSubCommand("assign", new TownPlotAssignCommand("clgroups.commands.town.plot.assign", plugin));
        //registerSubCommand("sale", new TownPlotSaleCommand("clgroups.commands.town.plot.sale", plugin));
        //registerSubCommand("unsale", new TownPlotUnsaleCommand("clgroups.commands.town.plot.unsale", plugin));
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town plot claim - Beansprucht ein Plot für deine Stadt");
        sender.sendMessage("/town plot unclaim - Löscht den beanspruchten Plot");
        sender.sendMessage("/town plot info - Zeigt die Ploteinstellungen an");
        sender.sendMessage("/town plot flag <Flag> - Setzt einen Flag für den plot -> town.plot.flag.<Flag>");
        sender.sendMessage("/town plot add <Spieler/Rang...> - Schalte Ränge/Spieler für den Plot frei ");
        sender.sendMessage("/town plot remove <Spieler...|Rang...> - entziehe Rängen/Spielern die Plotrechte");
        sender.sendMessage("/town plot assign <Spieler> - Setzt einen Plotbesitzer fest");
        sender.sendMessage("/town plot sale <Preis> - Setz ein Plot zum Verkauf");
        sender.sendMessage("/town plot unsale - Entfernt ein Plot vom Verkauf");
    }
    
}
