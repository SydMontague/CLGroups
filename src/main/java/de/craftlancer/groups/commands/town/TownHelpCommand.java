package de.craftlancer.groups.commands.town;

import java.util.Map;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.command.HelpCommand;
import de.craftlancer.core.command.SubCommand;
import de.craftlancer.groups.CLGroups;

public class TownHelpCommand extends HelpCommand
{
    public TownHelpCommand(String permission, CLGroups plugin, Map<String, SubCommand> map)
    {
        super(permission, plugin, map);
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town help [Befehl] - diese Liste");
        sender.sendMessage("/town info [Stadt] - info of town [Stadt]");
        sender.sendMessage("/town new <Name> - Erstellt eine neue Stadt");
        sender.sendMessage("/town invite <Spieler> - lade einen Spieler in die Stadt ein");
        sender.sendMessage("/town kick <Spieler> - wirf einen Spieler aus der Stadt");
        sender.sendMessage("/town list - Liste aller Städte");
        sender.sendMessage("/town disband - löse deine Stadt auf");
        sender.sendMessage("/town welcome <Nachricht> - setz die Willkommensnachricht.");
        sender.sendMessage("/town farewell <Nachricht> - setz die Abschiedsnachricht.");
        sender.sendMessage("/town login <Nachricht> - setz die Loginnachricht.");
        sender.sendMessage("/town leave - Stadt verlassen; Löscht alle eigenen Plots");
        sender.sendMessage("/town group - Zeigt alle Gruppenbefehle an");
        sender.sendMessage("/town plot - Zeigt alle Plotbefehle an");
    }
}
