package de.craftlancer.groups.commands.town;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.questions.QuestionListener;
import de.craftlancer.groups.questions.TownInviteQuestion;

public class TownInviteCommand extends GroupSubCommand
{
    public TownInviteCommand(String permission, CLGroups plugin)
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
        else if (getPlugin().getServer().getPlayerExact(args[1]) == null)
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOPLAYER);
        else
            for (int i = 1; i < args.length; i++)
            {
                GroupPlayer gp = getPlugin().getGroupPlayer(args[i]);
                Player p = getPlugin().getServer().getPlayerExact(args[i]);
                Town town = getPlugin().getGroupPlayer(sender.getName()).getTown();
                
                if (town == null)
                    sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINTOWN);
                else if (gp.getTown() != null)
                    sender.sendMessage(GroupLanguage.COMMAND_TOWN_INVITE_ALREADYINTOWN);
                else if (!town.hasPermission(sender.getName(), "town.invite"))
                    sender.sendMessage(GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION);
                else
                {
                    sender.sendMessage(GroupLanguage.COMMAND_TOWN_INVITE_SUCCESS);
                    p.sendMessage(String.format(GroupLanguage.COMMAND_TOWN_INVITE_QUESTION, town.getName(), town.getFaction().getName()));
                    QuestionListener.addQuestion(new TownInviteQuestion(getPlugin(), p, town));
                }
            }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town invite <Spieler> - lade einen Spieler in die Stadt ein");
    }
    
}
