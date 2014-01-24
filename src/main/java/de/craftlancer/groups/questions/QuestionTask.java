package de.craftlancer.groups.questions;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.scheduler.BukkitRunnable;

public class QuestionTask extends BukkitRunnable
{
    private Set<Question> questions;
    private long timeOut;
    
    public QuestionTask(Set<Question> questions, long timeOut)
    {
        this.questions = questions;
        this.timeOut = timeOut;
    }
    
    @Override
    public void run()
    {
        for (Question q : new HashSet<Question>(questions))
            if (q.getLifetime() > timeOut)
            {
                q.cancel();
                questions.remove(q);
            }
    }
    
}
