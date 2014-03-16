package de.craftlancer.groups.commands.town;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;
import de.craftlancer.groups.questions.QuestionListener;
import de.craftlancer.groups.questions.TownDisbandQuestion;

public class TownDisbandCommand extends GroupSubCommand
{
    
    public TownDisbandCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        
        GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
        Town town = gp.getTown();
        if (town == null)
            return GroupLanguage.COMMAND_GENERAL_NOTINTOWN;
        if (town.getFaction().getTowns().size() == 1)
            return GroupLanguage.COMMAND_TOWN_DISBAND_LASTTOWN;
        if (!town.hasPermission(gp.getName(), "town.disband"))
            return GroupLanguage.COMMAND_GENERAL_TOWN_PERMISSION;
        
        QuestionListener.addQuestion(new TownDisbandQuestion(getPlugin(), sender, town));
        return GroupLanguage.QUESTION_TOWN_DISBAND_QUESTION;
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/town disband - löse deine Stadt auf");
    }
    
}
