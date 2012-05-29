package com.Re4PzZ.Hellhounds.methods;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * @author Re4PzZ
 *         www.re4pzz.bplaced.net
 */
public class General {

    public static void drop(final int id) {
        for (final Item item : Inventory.getItems()) {
            if (item.getId() == id && item.getWidgetChild().interact("Drop")) {
                Time.sleep(Random.nextInt(600, 900));
            }
        }
    }

    public static boolean inNpcArea() {
        Area NpcArea = new Area(new Tile(2846,9818, -1), new Tile(2874,9854, -1));
        if (NpcArea.contains(Players.getLocal().getLocation()) && Players.getLocal().getLocation().getPlane() == -1) {
            return true;
        }
        return false;
    }

    public static boolean isReady() {
        return false;
    }

    public static boolean inBankArea() {
        Area BankArea = new Area(new Tile[] { new Tile(2991, 3390, 0), new Tile(2936, 3389, 0), new Tile(2936, 3352, 0),
                new Tile(2987, 3352, 0) });
        if (BankArea.contains(Players.getLocal().getLocation()) && Players.getLocal().getLocation().getPlane() == 0) {
            return true;
        }
        return false;
    }
}
