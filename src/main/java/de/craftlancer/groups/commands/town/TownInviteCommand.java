package de.craftlancer.groups.commands.town;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;
import de.craftlancer.groups.questions.QuestionListener;
import de.craftlancer.groups.questions.TownInviteQuestion;

public class TownInviteCommand extends GroupSubCommand
{
    public TownInviteCommand(String permission, CLGroups plugin)
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
        if (getPlugin().getServer().getPlayerExact(args[1]) == null)
            return GroupLanguage.COMMAND_GENERAL_NOPLAYER;
        
        Town town = PlayerManager.getGroupPlayer(sender.getName()).getTown();
        if (town == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINTOWN;
        if (!town.hasPermission(sender.getName(), "town.invite"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        
        for (int i = 1; i < args.length; i++)
        {
            GroupPlayer gp = PlayerManager.getGroupPlayer(args[i]);
            Player p = getPlugin().getServer().getPlayerExact(args[i]);
            
            if (gp.getTown() != null)
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_INVITE_ALREADYINTOWN);
            else
            {
                sender.sendMessage(GroupLanguage.COMMAND_TOWN_INVITE_SUCCESS);
                p.sendMessage(String.format(GroupLanguage.COMMAND_TOWN_INVITE_QUESTION, town.getName(), town.getFaction().getName()));
                QuestionListener.addQuestion(new TownInviteQuestion(getPlugin(), p, town));
            }
        }
        return null;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town invite <Spieler> - lade einen Spieler in die Stadt ein");
    }
    
}
