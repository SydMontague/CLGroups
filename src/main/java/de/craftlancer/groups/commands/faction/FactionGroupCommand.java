package de.craftlancer.groups.commands.faction;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.command.SubCommandHandler;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.commands.faction.group.FactionGroupAddCommand;
import de.craftlancer.groups.commands.faction.group.FactionGroupAddPermCommand;
import de.craftlancer.groups.commands.faction.group.FactionGroupDeleteCommand;
import de.craftlancer.groups.commands.faction.group.FactionGroupHelpCommand;
import de.craftlancer.groups.commands.faction.group.FactionGroupInfoCommand;
import de.craftlancer.groups.commands.faction.group.FactionGroupListCommand;
import de.craftlancer.groups.commands.faction.group.FactionGroupNewCommand;
import de.craftlancer.groups.commands.faction.group.FactionGroupRemoveCommand;
import de.craftlancer.groups.commands.faction.group.FactionGroupRemovepermCommand;

public class FactionGroupCommand extends SubCommandHandler
{
    
    public FactionGroupCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, true);
        registerSubCommand("help", new FactionGroupHelpCommand("clgroups.commands.faction.group.help", plugin, getCommands()));
        registerSubCommand("new", new FactionGroupNewCommand("clgroups.commands.faction.group.new", plugin));
        registerSubCommand("delete", new FactionGroupDeleteCommand("clgroups.commands.faction.group.delete", plugin));
        registerSubCommand("list", new FactionGroupListCommand("clgroups.commands.faction.group.list", plugin));
        registerSubCommand("info", new FactionGroupInfoCommand("clgroups.commands.faction.group.info", plugin));
        registerSubCommand("add", new FactionGroupAddCommand("clgroups.commands.faction.group.add", plugin));
        registerSubCommand("remove", new FactionGroupRemoveCommand("clgroups.commands.faction.group.remove", plugin));
        registerSubCommand("addperm", new FactionGroupAddPermCommand("clgroups.commands.faction.group.addperm", plugin));
        registerSubCommand("removeperm", new FactionGroupRemovepermCommand("clgroups.commands.faction.group.removeperm", plugin));
        
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("<Argument> - Pflichtarguement || <Argument...> - 1 bis unendlich viele");
        sender.sendMessage("/faction group new <Rang> - erstelle einen neuen Fraktionsrang");
        sender.sendMessage("/faction group delete <Rang> - lösche einen Fraktionsrang");
        sender.sendMessage("/faction group list - zeige alle Fraktionsrönge an");
        sender.sendMessage("/faction group info <Rang> - Infos über einen Fraktionsrang");
        sender.sendMessage("/faction group add <Rang> <Spieler...> - weise einen Spieler einen Fraktionsrang hinzu");
        sender.sendMessage("/faction group remove <Rang> <Spieler...> - entferne einen Spieler von einem Fraktionsrang");
        sender.sendMessage("/faction group addperm <Rang> <Rechte...> - füge einem Fraktionsrang Rechte hinzu");
        sender.sendMessage("/faction group removeperm <Rang> <Rechte...> - lösche Rechte von einem Fraktionsrang");
    }
    
}
