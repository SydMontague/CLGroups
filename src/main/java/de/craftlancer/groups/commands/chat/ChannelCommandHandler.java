package de.craftlancer.groups.commands.chat;

import de.craftlancer.core.command.CommandHandler;
import de.craftlancer.groups.CLGroups;

/*
 * ch l(okal) - lokal
 * ch g(lobal) - global
 * ch t(own) - town
 * ch f(raktion) - faction
 * ch help
 */
public class ChannelCommandHandler extends CommandHandler
{
    public ChannelCommandHandler(CLGroups plugin)
    {
        registerSubCommand("help", new ChannelHelpCommand("clgroups.commands.chat.help", plugin, getCommands()));
        registerSubCommand("local", new ChannelLocalCommand("clgroups.commands.chat.local", plugin), "l", "lokal");
        registerSubCommand("global", new ChannelGlobalCommand("clgroups.commands.chat.global", plugin), "g");
        registerSubCommand("town", new ChannelTownCommand("clgroups.commands.chat.town", plugin), "t");
        registerSubCommand("faction", new ChannelFactionCommand("clgroups.commands.chat.faction", plugin), "f", "fraktion");
    }
}
