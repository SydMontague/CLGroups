package de.craftlancer.groups.commands.town.group;

import java.util.Map;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.command.HelpCommand;
import de.craftlancer.core.command.SubCommand;
import de.craftlancer.groups.CLGroups;

public class TownGroupHelpCommand extends HelpCommand
{
    public TownGroupHelpCommand(String permission, CLGroups plugin, Map<String, SubCommand> map)
    {
        super(permission, plugin, map);
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("<Arguemtn> - Pflichtarguement || <Argument...> - 1 bis unendlich");
        sender.sendMessage("/town group new <Rang> - erstelle einen neuen Stadtrang");
        sender.sendMessage("/town group delete <Rang> - lösche einen Stadtrang");
        sender.sendMessage("/town group list - zeige alle Stadtränge an");
        sender.sendMessage("/town group info <Rang> - Infos über einen Stadtrang");
        sender.sendMessage("/town group add <Rang> <Spieler...> - weise einen Spieler einen Stadtrang hinzu");
        sender.sendMessage("/town group remove <Rang> <Spieler...> - entferne einen Spieler von einem Stadtrang");
        sender.sendMessage("/town group addperm <Rang> <Rechte...> - füge einem Stadtrang Rechte hinzu");
        sender.sendMessage("/town group removeperm <Rang> <Rechte...> - lösche Rechte von einem Stadtrang");
    }
    
}
