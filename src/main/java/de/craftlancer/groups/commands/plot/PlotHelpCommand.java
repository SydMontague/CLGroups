package de.craftlancer.groups.commands.plot;

import java.util.Map;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.command.HelpCommand;
import de.craftlancer.core.command.SubCommand;
import de.craftlancer.groups.CLGroups;

public class PlotHelpCommand extends HelpCommand
{
    public PlotHelpCommand(String permission, CLGroups plugin, Map<String, SubCommand> map)
    {
        super(permission, plugin, map);
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/plot add <Spieler...> - fügt Spieler einem Plot zu");
        sender.sendMessage("/plot remove <Spieler...> - entfernt Spieler vom Plot");
        sender.sendMessage("/plot help [Befehl] - Befehlsliste und  Befehlshilfe");
        sender.sendMessage("/plot info - zeigt Ploteinstellungen");
        sender.sendMessage("/plot claim - Plot kaufen");
        sender.sendMessage("/plot sale <Preis> - Setzt den Plot zum Verkauf");
        sender.sendMessage("/plot unsale - Entfernt den Verkaufsstatus vom Plot");
        sender.sendMessage("/plot leave - Plot entfernen/löschen");
        sender.sendMessage("/plot gift <Spieler> - Schenkt den Plot an einen Spieler");
        sender.sendMessage("/plot list - Zeigt alle deine Plots an");
    }
}
