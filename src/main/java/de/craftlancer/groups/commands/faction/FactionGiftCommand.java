package de.craftlancer.groups.commands.faction;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.craftlancer.core.Utils;
import de.craftlancer.groups.CLGroups;
import de.craftlancer.groups.Faction;
import de.craftlancer.groups.GroupLanguage;
import de.craftlancer.groups.GroupPlayer;
import de.craftlancer.groups.Town;
import de.craftlancer.groups.commands.GroupSubCommand;
import de.craftlancer.groups.managers.FactionManager;
import de.craftlancer.groups.managers.PlayerManager;
import de.craftlancer.groups.managers.TownManager;
import de.craftlancer.groups.questions.FactionGiftLeaderQuestion;
import de.craftlancer.groups.questions.QuestionListener;

public class FactionGiftCommand extends GroupSubCommand
{
    
    public FactionGiftCommand(String permission, CLGroups plugin)
    {
        super(permission, plugin, false);
    }
    
    @Override
    protected String execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!checkSender(sender))
            return GroupLanguage.COMMAND_GENERAL_UNABLE;
        if (args.length < 3)
            return GroupLanguage.COMMAND_GENERAL_ARGUMENTS;
        
        GroupPlayer gp = PlayerManager.getGroupPlayer(sender.getName());
        Faction f = FactionManager.getFaction(args[2]);
        Town t = TownManager.getTown(args[1]);
        
        if (f == null)
            return GroupLanguage.COMMAND_GENERAL_NOFACTION;
        if (t == null)
            return GroupLanguage.COMMAND_GENERAL_NOTOWN;
        if (!t.getFaction().equals(f))
            return GroupLanguage.COMMAND_FACTION_GIFT_NOTYOURTOWN;
        if (t.getFaction().getTowns().size() == 1)
            return GroupLanguage.COMMAND_FACTION_GIFT_LASTTOWN;
        if (!gp.getFaction().hasPermission(gp.getName(), "faction.gifttown"))
            return GroupLanguage.COMMAND_GENERAL_FACTION_PERMISSION;
        
        QuestionListener.addQuestion(new FactionGiftLeaderQuestion(getPlugin(), sender, t, f));
        return null;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 2:
                
                return Utils.getMatches(args[1], PlayerManager.getGroupPlayer(sender.getName()).getFaction().getTownNames());
            case 3:
                return Utils.getMatches(args[2], FactionManager.getFactionNames());
            default:
                return null;
        }
    }
    
    @Override
    public void help(CommandSender sender)
    {
        sender.sendMessage("/faction gift <Stadt> <Fraktion> - übergib eine Stadt an eine andere Fraktion");
    }
    
}
