package com.tafftech.generictask;

import com.tafftech.node.Task;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Path;

public abstract class WalkTo extends Task {
    private final Tile[] path;

    public WalkTo(ClientContext ctx, Tile... path){
        super(ctx);
        this.path = path;
    }

    public void execute(){
        Path p = ctx.movement.newTilePath(path);
        p.traverse();
    }
}
