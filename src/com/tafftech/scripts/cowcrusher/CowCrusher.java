package com.tafftech.scripts.cowcrusher;

import com.tafftech.generictask.Attack;
import com.tafftech.generictask.Deposit;
import com.tafftech.generictask.Loot;
import com.tafftech.generictask.WalkTo;
import com.tafftech.scripts.TaskScript;
import org.powerbot.script.Filter;
import org.powerbot.script.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.Npc;

@Script.Manifest(name = "CowCrusher", description = "Kills cows, loots hides and raw beef at Burthorpe")
public class CowCrusher extends TaskScript {
    private static final Tile[] bankToCows = {new Tile(2883, 3535), new Tile(2882, 3533), new Tile(2881, 3531), new Tile(2881, 3529), new Tile(2881, 3526), new Tile(2881, 3524), new Tile(2880, 3522), new Tile(2880, 3520), new Tile(2880, 3517), new Tile(2880, 3514), new Tile(2880, 3511), new Tile(2880, 3508), new Tile(2880, 3506), new Tile(2881, 3504), new Tile(2880, 3501), new Tile(2880, 3498), new Tile(2880, 3496), new Tile(2881, 3494), new Tile(2882, 3494), new Tile(2883, 3492), new Tile(2883, 3489), new Tile(2885, 3488)};
    private static final Tile[] cowsToBank = {new Tile(2883, 3486), new Tile(2883, 3489), new Tile(2883, 3492), new Tile(2883, 3493), new Tile(2882, 3493), new Tile(2881, 3496), new Tile(2880, 3498), new Tile(2880, 3500), new Tile(2882, 3502), new Tile(2884, 3504), new Tile(2882, 3504), new Tile(2880, 3505), new Tile(2880, 3507), new Tile(2880, 3510), new Tile(2880, 3513), new Tile(2881, 3514), new Tile(2880, 3518), new Tile(2880, 3521), new Tile(2880, 3523), new Tile(2880, 3524), new Tile(2880, 3526), new Tile(2880, 3529), new Tile(2880, 3533), new Tile(2881, 3536), new Tile(2884, 3536), new Tile(2885, 3536), new Tile(2884, 3535), new Tile(2883, 3535)};

    @Override
    protected void initialise() {
        add(new Loot(ctx, "Cowhide", "Raw beef"));
        add(new Deposit(ctx));
        add(new WalkTo(ctx, cowsToBank) {
            @Override
            public boolean validate() {
                return ctx.backpack.select().count() == 28;
            }
        });
        add(new WalkTo(ctx, bankToCows) {
            @Override
            public boolean validate() {
                return ctx.backpack.select().count() < 28
                        && ctx.npcs.select().name("Cow").select(new Filter<Npc>() {
                    @Override
                    public boolean accept(Npc npc) {
                        return npc.inViewport();
                    }
                }).isEmpty();
            }
        });
        add(new Attack(ctx, "Cow"));
    }
}
