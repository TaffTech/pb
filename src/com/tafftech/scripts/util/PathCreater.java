package com.tafftech.scripts.util;

import com.tafftech.scripts.TaskScript;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Players;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by TaffTech
 */
@Script.Manifest(name = "Path Creater", description = "A tool to help create paths")
public class PathCreater extends PollingScript<ClientContext> {
    private LinkedList<Tile> tiles = new LinkedList<Tile>();

    @Override
    public void start(){
        tiles.add(currentTile());
    }

    @Override
    public void poll() {
        if(ctx.movement.distance(tiles.peekLast()) >= 3){
            tiles.add(currentTile());
        }
    }

    @Override
    public void stop(){
        tiles.add(currentTile());
        System.out.print("{");
        for(Tile t:tiles){
            System.out.print("new Tile(" + t.x() + ", " + t.y() + ")");
            if(t.equals(tiles.peekLast())){
                System.out.println("};");
            } else {
                System.out.print(", ");
            }
        }
    }


    private Tile currentTile(){
        return ctx.players.local().tile();
    }
}
