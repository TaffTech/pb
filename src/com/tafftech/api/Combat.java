package com.tafftech.api;

import org.powerbot.script.Filter;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;

public class Combat {
    public static boolean isInCombat(final ClientContext ctx){
        return ctx.combatBar.targetHealthPercent() > 0 || ctx.players.local().interacting() != null || !ctx.npcs.select().select(new Filter<Npc>() {
            @Override
            public boolean accept(Npc npc) {
                return npc.interacting().equals(ctx.players.local());
            }
        }).isEmpty();
    }
}
