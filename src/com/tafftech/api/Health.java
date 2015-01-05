package com.tafftech.api;

import org.powerbot.script.rt6.ClientContext;

public class Health {
    public static double getCurrentPercent(ClientContext ctx){
        return ctx.combatBar.health()/(double)ctx.combatBar.maximumHealth();
    }
}
