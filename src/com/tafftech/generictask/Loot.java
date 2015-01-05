package com.tafftech.generictask;

import com.tafftech.node.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GroundItem;

import java.util.concurrent.Callable;

/**
 * Created by TaffTech
 */
public class Loot extends Task {

    private final String[] names;
    public Loot(ClientContext ctx, String... names) {
        super(ctx);
        this.names = names;
    }

    @Override
    public boolean validate() {
        return ctx.backpack.select().count() < 28 && !ctx.groundItems.select().name(names).isEmpty();
    }

    @Override
    public void execute() {
        final GroundItem item = ctx.groundItems.nearest().poll();
        if(item.inViewport() && item.interact("Take", item.name())){
            final int oldCount = ctx.backpack.select().name(item).count(true);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.backpack.select().name(item).count(true) > oldCount || !item.valid();
                }
            });
        } else {
            if(!item.inViewport()){
                ctx.camera.turnTo(item);
            }
            if(!item.inViewport()){
                if(ctx.movement.step(item)){
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return item.inViewport();
                        }
                    });
                }
            }
        }
    }
}
