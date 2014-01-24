package de.craftlancer.groups.commands.town;

import org.bukkit.command.CommandSender;

import de.craftlancer.core.command.SubCommandHandler;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.commands.town.build.TownBuildBuyCommand;
import de.craftlancer.groups.commands.town.build.TownBuildListCommand;
import de.craftlancer.groups.commands.town.build.TownBuildPlaceCommand;
import de.craftlancer.groups.commands.town.build.TownBuildRemoveCommand;
import de.craftlancer.groups.commands.town.build.TownBuildUpgradeCommand;

public class TownBuildCommand extends SubCommandHandler
{

    public TownBuildCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
        registerSubCommand("buy", new TownBuildBuyCommand("clgroups.commands.town.build.buy", plugin));
        registerSubCommand("place", new TownBuildPlaceCommand("clgroups.commands.town.build.place", plugin));
        registerSubCommand("remove", new TownBuildRemoveCommand("clgroups.commands.town.build.remove", plugin));
        registerSubCommand("upgrade", new TownBuildUpgradeCommand("clgroups.commands.town.build.upgrade", plugin));
        registerSubCommand("list", new TownBuildListCommand("clgroups.commands.town.build.list", plugin));
        // TODO Auto-generated constructor stub
    }

    @Override
    public void help(CommandSender sender)
    {
        // TODO Auto-generated method stub
        
    }
    
}
