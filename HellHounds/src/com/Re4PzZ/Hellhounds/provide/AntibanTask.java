package com.Re4PzZ.Hellhounds.provide;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * @author Re4PzZ
 *         www.re4pzz.bplaced.net
 */
public class AntibanTask implements Task, Condition {

    public static final Filter<SceneObject> FILTER_VISIBLE = new Filter<SceneObject>() {
        public boolean accept(SceneObject obj) {
            return obj.isOnScreen();
        }
    };

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void run() {
        if (Random.nextInt(0, 5) == 0) {
            SceneObject[] obj = SceneEntities.getLoaded(FILTER_VISIBLE);
            switch (Random.nextInt(1, 5)) {
                case 1:
                    Camera.getAngleTo(Random.nextInt(2000, 4200));
                    break;
                case 2:
                    if (Random.nextInt(0, 5) == 0)
                        Camera.setPitch(true);
                    break;
                case 3:
                    if (Random.nextInt(0, 5) == 0)
                        Camera.setPitch(false);
                    break;
                case 4:
                    if (obj.length > 0) {
                        Camera.turnTo(obj[Random.nextInt(0, obj.length - 1)].getLocation());
                    }
                    break;
                default:
                    break;
            }
        }
        if (Random.nextInt(0, 5) == 0) {
            SceneObject[] objs = SceneEntities.getLoaded(FILTER_VISIBLE);
            switch (Random.nextInt(1, 7)) {
                case 1:
                    Mouse.move((int) ((Mouse.getLocation().x * 3.5) / 2.5) + Random.nextInt(10, 30),
                            ((int) ((Mouse.getLocation().y * 3.5) / 2.5) + Random.nextInt(10, 30)));
                    break;
                case 2:
                    Item[] items = Inventory.getItems();
                    items[Random.nextInt(0, items.length - 1)].getWidgetChild().hover();
                    break;
                case 3:
                    if (objs.length > 0) {
                        objs[Random.nextInt(0, objs.length - 1)].hover();
                    }
                    break;
                case 4:
                    Mouse.move((int) ((Mouse.getLocation().x * 3.5) / 2.5) + Random.nextInt(5, 30),
                            ((int) ((Mouse.getLocation().y * 3.5) / 2.5) + Random.nextInt(5, 30)));
                    break;
                case 5:
                    Mouse.putSide(Random.nextInt(900, 2000));
                default:
                    break;
            }
        }    }
}
