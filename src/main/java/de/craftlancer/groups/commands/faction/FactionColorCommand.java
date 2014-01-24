package de.craftlancer.groups.commands.faction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.commands.GroupSubCommand;

public class FactionColorCommand extends GroupSubCommand
{
    private static final Map<String, ChatColor> colorMap = new HashMap<String, ChatColor>()
    {
        private static final long serialVersionUID = 1L;
        {
            put("AQUA", ChatColor.AQUA);
            put("BLACK", ChatColor.BLACK);
            put("BLUE", ChatColor.BLUE);
            put("DARK_AQUA", ChatColor.DARK_AQUA);
            put("DARK_BLUE", ChatColor.DARK_BLUE);
            put("DARK_GRAY", ChatColor.DARK_GRAY);
            put("DARK_GREEN", ChatColor.DARK_GREEN);
            put("DARK_PURPLE", ChatColor.DARK_PURPLE);
            put("DARK_RED", ChatColor.DARK_RED);
            put("GOLD", ChatColor.GOLD);
            put("GRAY", ChatColor.GRAY);
            put("GREEN", ChatColor.GREEN);
            put("LIGHT_PURPLE", ChatColor.LIGHT_PURPLE);
            put("RED", ChatColor.RED);
            put("WHITE", ChatColor.WHITE);
            put("YELLOW", ChatColor.YELLOW);
        }
    };
    
    public FactionColorCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 2)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        else
        {
            GroupPlayer gp = getPlugin().getGroupPlayer(sender.getName());
            Faction f = gp.getFaction();
            ChatColor color = colorMap.get(args[1].toUpperCase());
            
            if (f == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINFACTION);
            else if (color == null)
                sender.sendMessage(GroupLanguage.COMMAND_FACTION_COLOR_NOTACOLOR);
            else if (!f.hasPermission(gp.getName(), "faction.color"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION);
            else if (getPlugin().isColorTaken(color))
                sender.sendMessage(GroupLanguage.COMMAND_FACTION_COLOR_TAKEN);
            else
            {
                f.setColor(color);
                sender.sendMessage(GroupLanguage.COMMAND_FACTION_COLOR_SUCCESS);
            }
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 2:
                return Utils.getMatches(args[1], colorMap.keySet());
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction color <Farbe> - Setzt die Farbe deiner Fraktion fest");
        sender.sendMessage("Erlaubte Farben sind: " + ChatColor.AQUA + "AQUA" + ChatColor.WHITE + ", " + ChatColor.BLACK + "BLACK" + ChatColor.WHITE + ", " + ChatColor.BLUE + "BLUE" + ChatColor.WHITE + ", " + ChatColor.DARK_AQUA + "DARK_AQUA" + ChatColor.WHITE + ", " + ChatColor.DARK_BLUE + "DARK_BLUE" + ChatColor.WHITE + ", " + ChatColor.DARK_GRAY + "DARK_GRAY" + ChatColor.WHITE + ", " + ChatColor.DARK_GREEN + "DARK_GREEN" + ChatColor.WHITE + ", " + ChatColor.DARK_PURPLE + "DARK_PURPLE" + ChatColor.WHITE + ", " + ChatColor.DARK_RED + "DARK_RED" + ChatColor.WHITE + ", " + ChatColor.GOLD + "GOLD" + ChatColor.WHITE + ", " + ChatColor.GRAY + "GRAY" + ChatColor.WHITE + ", " + ChatColor.GREEN + "GREEN" + ChatColor.WHITE + ", " + ChatColor.LIGHT_PURPLE + "LIGHT_PURPLE" + ChatColor.WHITE + ", " + ChatColor.RED + "RED" + ChatColor.WHITE + ", " + ChatColor.WHITE + "WHITE" + ChatColor.WHITE + ", " + ChatColor.YELLOW + "YELLOW");
    }
    
}
