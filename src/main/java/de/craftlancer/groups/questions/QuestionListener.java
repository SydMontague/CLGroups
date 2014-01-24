package de.craftlancer.groups.questions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.craftlancer.groups.CLGroups;

public class QuestionListener implements Listener
{
    private CLGroups plugin;
    private static Set<Question> questions = new HashSet<Question>();
    private long timeOut = 6000000L; // 1 minute
    private long delay = 10L;
    
    public QuestionListener(CLGroups plugin)
    {
        this.plugin = plugin;
        new QuestionTask(questions, timeOut).runTaskTimer(plugin, delay, delay);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e)
    {
        Question question = null;
        for (Question q : questions)
            if (q.isAsked(e.getPlayer()))
                if (question == null || q.getLifetime() > question.getLifetime())
                    question = q;
        
        if (question == null)
            return;
        
        if (e.getMessage().equalsIgnoreCase("yes"))
            new QuestionExecuteTask(question, false).runTask(plugin);
        else if (e.getMessage().equalsIgnoreCase("no"))
            new QuestionExecuteTask(question, true).runTask(plugin);
        else
            return;
        
        e.setCancelled(true);
        questions.remove(question);
    }
    
    public static void addQuestion(Question q)
    {
        questions.add(q);
        q.ask();
    }
    
    private class QuestionExecuteTask extends BukkitRunnable
    {
        private final boolean cancel;
        private final Question question;
        
        public QuestionExecuteTask(Question question, boolean cancel)
        {
            this.cancel = cancel;
            this.question = question;
        }
        
        @Override
        public void run()
        {
            if (cancel)
                question.cancel();
            else
                question.execute();
        }
        
    }
}
