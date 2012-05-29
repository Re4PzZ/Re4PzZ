package com.Re4PzZ.Hellhounds.methods;

import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.GroundItem;

/**
 * @author Re4PzZ
 *         www.re4pzz.bplaced.net
 */
public class Loot {

    public static void pickUp(final GroundItem item) {
        GroundItem g = GroundItems.getNearest(new Filter<GroundItem>() {
            @Override
            public boolean accept(GroundItem groundItem) {
                return groundItem.getGroundItem().getId() == item.getGroundItem().getId();
            }
        });
        if(g != null) {
            if (g.isOnScreen()) {
                if (!Players.getLocal().isMoving()) {
                    if (g.click(true)) {
                        Time.sleep(Random.nextInt(500, 2000));
                    }
                }
            } else {
                if (!Players.getLocal().isMoving()) {
                    if (Walking.walk(g.getLocation())) {
                        Time.sleep(Random.nextInt(500, 2000));
                    }
                }
            }
        }
    }
}
