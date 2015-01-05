package com.tafftech.generictask;

import com.tafftech.api.Combat;
import com.tafftech.api.Health;
import com.tafftech.node.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.Actor;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;

import java.util.concurrent.Callable;

public class Attack extends Task {
    private final String name;
    private Npc target = ctx.npcs.nil();
    public Attack(ClientContext ctx, String name) {
        super(ctx);
        this.name = name;
    }

    @Override
    public boolean validate() {
        return !target.valid()
                || (Health.getCurrentPercent(ctx) > 20
                    && !Combat.isInCombat(ctx)
                    && !ctx.npcs.select().name(name).isEmpty());
    }

    @Override
    public void execute() {
        target = ctx.npcs.nearest().poll();
        if(target.inViewport()){
            if(target.interact("Attack", target.name())){
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return Combat.isInCombat(ctx);
                    }
                });
            }
        } else {
            ctx.camera.turnTo(target);
            if(!target.inViewport()){
                if(ctx.movement.step(target)){
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return target.inViewport();
                        }
                    });
                }
            }
        }
    }
}
