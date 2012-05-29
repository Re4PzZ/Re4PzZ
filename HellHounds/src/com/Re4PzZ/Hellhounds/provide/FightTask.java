package com.Re4PzZ.Hellhounds.provide;

import com.Re4PzZ.Hellhounds.Main;
import com.Re4PzZ.Hellhounds.methods.General;
import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * @author Re4PzZ
 *         www.re4pzz.bplaced.net
 */
public class FightTask implements Task, Condition {
    @Override
    public boolean validate() {
        return General.inNpcArea() && General.isReady();
    }

    @Override
    public void run() {
        Filter attackingGhoul = new Filter<NPC>() {
            public boolean accept(NPC npc) {
                return npc.getName().equals("Hellhound")
                        && npc.getInteracting() == Players.getLocal();
            }
        };

        Filter freeGhoulToAttack = new Filter<NPC>() {
            public boolean accept(NPC npc) {
                return !npc.isInCombat() && npc.getName().equals("Hellhound");
            }
        };

        if (NPCs.getNearest(attackingGhoul) != null)
            Main.Hellhound = NPCs.getNearest(attackingGhoul);
        else
            Main.Hellhound = NPCs.getNearest(freeGhoulToAttack);

        if (Main.Hellhound.isOnScreen()) {
            if (Main.Hellhound.getInteracting() == null
                    && !underAttack() && Main.Hellhound.isOnScreen()
                    && Main.Hellhound != null) {
                Mouse.move(Main.Hellhound.getCentralPoint().x, Main.Hellhound.getCentralPoint().y);
                Time.sleep(50);
                if(!Menu.select("Attack")){
                    Mouse.move(Main.Hellhound.getCentralPoint().x, Main.Hellhound.getCentralPoint().y);
                    Time.sleep(50);
                    Menu.select("Attack");
                }
            }
        } else {
            if(Calculations.distance(Players.getLocal().getLocation(), Main.Hellhound.getLocation()) <=5){
                Camera.turnTo(Main.Hellhound);
            }else{
                Walking.walk(Main.Hellhound.getLocation());
            }
        }
        Time.sleep(Random.nextInt(1000, 1200));
        while (Players.getLocal().isMoving() && !Main.Hellhound.isInCombat()) {
            Time.sleep(100);
        }
    }

    public static boolean underAttack() {
        Filter ghoul = new Filter<NPC>() {
            public boolean accept(NPC npc) {
                return npc.getId() == 49;
            }
        };
        for (NPC attackingGhoul : NPCs.getLoaded(ghoul)) {
            if (attackingGhoul.getInteracting() == Players.getLocal()) {
                return true;
            }
        }
        return false;
    }

    public static void eat() {
        if (Players.getLocal().getHpPercent() < 80 && Inventory.getCount(Main.Food) >0) {
            for(Item food : Inventory.getItems()){
                if(food.getId() == Main.Food){
                    if(food.getWidgetChild().click(true)){
                        Time.sleep(Random.nextInt(500, 2500));
                    }
                }
            }
        }
    }
}
