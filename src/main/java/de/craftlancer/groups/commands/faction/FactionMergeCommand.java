package de.craftlancer.groups.commands.faction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.core.command.SubCommand;
import de.craftlancer.groups.CLGroups;

public class FactionMergeCommand extends SubCommand
{
    
    public FactionMergeCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        // TODO Faction Merge Befehl
        return null;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction merge <Fraktion> - Fusioniere deine Fraktion mit einer anderen");
        sender.sendMessage("Momentan nicht möglich");
    }
    
}
