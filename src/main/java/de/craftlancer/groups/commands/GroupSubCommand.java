package de.craftlancer.groups.commands;

import de.craftlancer.core.command.SubCommand;
import de.craftlancer.groups.CLGroups;

public abstract class GroupSubCommand extends SubCommand
{
    public GroupSubCommand(String permission, CLGroups plugin, boolean console)
    {
        super(permission, plugin, console);
    }
    
    @Override
    public CLGroups getPlugin()
    {
        return (CLGroups) super.getPlugin();
    }
}
