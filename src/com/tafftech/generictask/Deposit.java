package com.tafftech.generictask;

import com.tafftech.node.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class Deposit extends Task{
    private final String[] itemsToKeep;
    public Deposit(ClientContext ctx, String... itemsToKeep) {
        super(ctx);
        this.itemsToKeep = itemsToKeep;
    }

    @Override
    public boolean validate() {
        return (ctx.bank.inViewport() || ctx.bank.opened())
                && ctx.backpack.select().name(itemsToKeep).count(true) < ctx.backpack.select().count(true);
    }

    @Override
    public void execute() {
        if(!ctx.bank.opened() && ctx.bank.open())
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.bank.opened();
                }
            });
        else
            return;

        if(ctx.backpack.select().name(itemsToKeep).isEmpty() && ctx.bank.depositInventory()){
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.backpack.select().isEmpty();
                }
            });
        } else {
            final List<String> namesList = Arrays.asList(itemsToKeep);
            ctx.backpack.select().select(new Filter<Item>() {
                @Override
                public boolean accept(Item item) {
                    return !namesList.contains(item.name());
                }
            }).forEach(new Consumer<Item>() {
                @Override
                public void accept(Item item) {
                    ctx.bank.deposit(item.id(), 0);
                    Condition.sleep(750);
                }
            });
        }
    }

}
