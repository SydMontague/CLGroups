package de.craftlancer.groups.commands.faction.group;

import java.util.Map;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.command.HelpCommand;
import de.craftlancer.core.command.SubCommand;
import de.craftlancer.groups.CLGroups;

public class FactionGroupHelpCommand extends HelpCommand
{
    
    public FactionGroupHelpCommand(String permission, CLGroups plugin, Map<String, SubCommand> map)
    {
        super(permission, plugin, map);
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("<Arguemtn> - Pflichtarguement || <Argument...> - 1 bis unendlich");
        sender.sendMessage("/faction group new <Rang> - erstelle einen neuen Fraktionsrang");
        sender.sendMessage("/faction group delete <Rang> - lösche einen Fraktionsrang");
        sender.sendMessage("/faction group list - zeige alle Fraktionsränge an");
        sender.sendMessage("/faction group info <Rang> - Infos über einen Fraktionsrang");
        sender.sendMessage("/faction group add <Rang> <Spieler...> - weise einen Spieler einen Fraktionsrang hinzu");
        sender.sendMessage("/faction group remove <Rang> <Spieler...> - entferne einen Spieler von einem Fraktionsrang");
        sender.sendMessage("/faction group addperm <Rang> <Rechte...> - füge einem Fraktionsrang Rechte hinzu");
        sender.sendMessage("/faction group removeperm <Rang> <Rechte...> - lösche Rechte von einem Fraktionsrang");
    }
    
}
