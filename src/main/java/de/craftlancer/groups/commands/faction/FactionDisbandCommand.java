package de.craftlancer.groups.commands.faction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.PlayerManager;
import de.craftlancer.groups.questions.FactionDisbandQuestion;
import de.craftlancer.groups.questions.QuestionListener;

public class FactionDisbandCommand extends GroupSubCommand
{
    
    public FactionDisbandCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected void execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            sender.sendMessage(GroupLanguage.COMMAND_GENERAL_UNABLE);
        else
        {
            GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
            Faction f = gp.getFaction();
            
            if (f == null)
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_NOTINFACTION);
            else if (!f.hasPermission(gp.getName(), "faction.disband"))
                sender.sendMessage(GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION);
            else
            {
                QuestionListener.addQuestion(new FactionDisbandQuestion(getPlugin(), sender, f));
            }
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction disband - Löse deine Fraktion auf");
    }
    
}
