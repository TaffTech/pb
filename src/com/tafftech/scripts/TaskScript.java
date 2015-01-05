package com.tafftech.scripts;

import com.tafftech.node.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.rt6.ClientContext;

import java.util.Arrays;
import java.util.LinkedList;

public abstract class TaskScript extends PollingScript<ClientContext>{
    private LinkedList<Task> tasks = new LinkedList<Task>();
    protected abstract void initialise();

    @Override
    public void start(){
        initialise();
    }

    @Override
    public void poll() {
        for(Task t:tasks){
            if(t.validate()) {
                t.execute();
                break;
            }
        }
        Condition.sleep(500);
    }


    public void add(Task... t){
        tasks.addAll(Arrays.asList(t));
    }
}
