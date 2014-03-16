package de.craftlancer.groups.commands.faction;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Reputation;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;

public class FactionDefaultrepCommand extends GroupSubCommand
{
    public FactionDefaultrepCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        if (args.length < 2)
            return GroupLanguage.COMMAND_GENERAL_ARGUMENTS;
        if (!Reputation.getNames().contains(args[1].toUpperCase()))
            return GroupLanguage.COMMAND_FACTION_NOTAREPU;
        
        GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
        Faction f = gp.getFaction();
        Reputation repu = Reputation.valueOf(args[1].toUpperCase());
        
        if (f == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINFACTION;
        if (!f.hasPermission(sender.getName(), "faction.defaultrep"))
            return GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION;
        
        f.setDefaultRepu(repu);
        return GroupLanguage.COMMAND_FACTION_DEFAULTREP_SUCCESS;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 2:
                return Utils.getMatches(args[1].toUpperCase(), Reputation.getNames());
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction defaultrep <Reputation> - Setz die Standardreputation deiner Fraktion");
        sender.sendMessage("Mögliche Reputationen: WAR, ENEMY, NEUTRAL, FRIEND, ALLIED");
    }
    
}
