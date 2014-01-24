package de.craftlancer.groups.commands.faction;

import java.util.Map;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.command.HelpCommand;
import de.craftlancer.core.command.SubCommand;
import de.craftlancer.groups.CLGroups;

public class FactionHelpCommand extends HelpCommand
{
    public FactionHelpCommand(String permission, CLGroups plugin, Map<String, SubCommand> commands)
    {
        super(permission, plugin, commands);
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction color <Farbe> - Setzt die Farbe deiner Fraktion fest");
        sender.sendMessage("/faction defaultrep <Reputation> - Setz die Standardreputation deiner Fraktion");
        sender.sendMessage("/faction disband - Löst deine Fraktion auf");
        sender.sendMessage("/faction gift <Stadt> <Fraktion> - übergibt eine Stadt an eine andere Fraktion");
        sender.sendMessage("/faction help [Befehl] - Hilfe zu Fraktionsbefehlen");
        sender.sendMessage("/faction info <Fraktion> - Informationen Fraktionen");
        sender.sendMessage("/faction list - List aller Fraktionen");
        sender.sendMessage("/faction merge <Fraktion> - Fusioniere deine Fraktion mit einer anderen");
        sender.sendMessage("/faction new <Fraktionsname> <Stadtname> - Gründet eine neue Fraktion");
        sender.sendMessage("/faction setrep <Fraktion/Spieler> <Reputation> - Setz die Reputation zu einer Fraktion/einen Spieler fest");
        sender.sendMessage("/faction showrep [Fraktion/Spieler] - Zeige die Reputation eines Spieler oder einer Fraktionen an");
        sender.sendMessage("/faction tag <Tag> - Setzt den Tag deiner Fraktion");
        sender.sendMessage("/faction group - Zeigt alle Gruppenbefehle an");
    }
}
