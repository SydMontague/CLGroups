package de.craftlancer.groups.commands.town;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.command.SubCommandHandler;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.commands.town.group.TownGroupAddCommand;
import de.craftlancer.groups.commands.town.group.TownGroupAddpermCommand;
import de.craftlancer.groups.commands.town.group.TownGroupDeleteCommand;
import de.craftlancer.groups.commands.town.group.TownGroupHelpCommand;
import de.craftlancer.groups.commands.town.group.TownGroupInfoCommand;
import de.craftlancer.groups.commands.town.group.TownGroupListCommand;
import de.craftlancer.groups.commands.town.group.TownGroupNewCommand;
import de.craftlancer.groups.commands.town.group.TownGroupRemoveCommand;
import de.craftlancer.groups.commands.town.group.TownGroupRemovepermCommand;

/*
 * town group new <name>        - create a new town-group
 * town group delete <name>     - delete a existing town-group
 * town group list              - list all town groups
 * town group info <name>       - get info of town-group
 * town group add <name> <player...>        - add players to town-group
 * town group remove <name> <player...>     - remove players from town-group
 * town group addperm <name> <perm...>      - add perms to group
 * town group removeperm <name> <perm...>   - remove perms from group
 */
public class TownGroupCommand extends SubCommandHandler
{
    public TownGroupCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
        registerSubCommand("help", new TownGroupHelpCommand("clgroups.commands.town.group.help", plugin, getCommands()));
        registerSubCommand("new", new TownGroupNewCommand("clgroups.commands.town.group.new", plugin));
        registerSubCommand("delete", new TownGroupDeleteCommand("clgroups.commands.town.group.delete", plugin));
        registerSubCommand("list", new TownGroupListCommand("clgroups.commands.town.group.list", plugin));
        registerSubCommand("info", new TownGroupInfoCommand("clgroups.commands.town.group.info", plugin));
        registerSubCommand("add", new TownGroupAddCommand("clgroups.commands.town.group.add", plugin));
        registerSubCommand("remove", new TownGroupRemoveCommand("clgroups.commands.town.group.remove", plugin));
        registerSubCommand("addperm", new TownGroupAddpermCommand("clgroups.commands.town.group.addperm", plugin));
        registerSubCommand("removeperm", new TownGroupRemovepermCommand("clgroups.commands.town.group.removeperm", plugin));
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("<Argument> - Pflichtarguement || <Argument...> - 1 bis unendlich");
        sender.sendMessage("/town group new <Rang> - erstelle einen neuen Stadtrang");
        sender.sendMessage("/town group delete <Rang> - lösche einen Stadtrang");
        sender.sendMessage("/town group list - zeige alle Stadtrange an");
        sender.sendMessage("/town group info <Rang> - Infos über einen Stadtrang");
        sender.sendMessage("/town group add <Rang> <Spieler...> - weise einen Spieler einen Stadtrang hinzu");
        sender.sendMessage("/town group remove <Rang> <Spieler...> - entferne einen Spieler von einem Stadtrang");
        sender.sendMessage("/town group addperm <Rang> <Rechte...> - füge einem Stadtrang Rechte hinzu");
        sender.sendMessage("/town group removeperm <Rang> <Rechte...> - lösche Rechte von einem Stadtrang");
        
    }
}
