package de.craftlancer.groups.commands.town.build;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.commands.GroupSubCommand;

public class TownBuildPlaceCommand extends GroupSubCommand
{
    
    public TownBuildPlaceCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else if (args.length < 4)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_ARGUMENTS);
        
        // TODO Auto-generated method stub
        getPlugin().getBuildingManager().startBuilding((Player) sender, getPlugin().getFeature("default"));
    }
    
    @Override
    public void help(CommandSender sender)
    {
        // TODO Auto-generated method stub
        
    }
    
}
