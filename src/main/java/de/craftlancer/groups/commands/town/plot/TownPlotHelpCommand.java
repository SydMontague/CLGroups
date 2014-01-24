package de.craftlancer.groups.commands.town.plot;

import java.util.Map;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.command.HelpCommand;
import de.craftlancer.core.command.SubCommand;
import de.craftlancer.groups.CLGroups;

public class TownPlotHelpCommand extends HelpCommand
{
    public TownPlotHelpCommand(String permission, CLGroups plugin, Map<String, SubCommand> map)
    {
        super(permission, plugin, map);
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
