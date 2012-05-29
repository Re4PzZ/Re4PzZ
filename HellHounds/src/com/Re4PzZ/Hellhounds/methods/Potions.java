package com.Re4PzZ.Hellhounds.methods;

import com.Re4PzZ.Hellhounds.Main;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * @author Re4PzZ
 *         www.re4pzz.bplaced.net
 */
public class Potions {

    public static void drink(int[] Potion) {
        for(int i = 0; i<Potion.length; i++){
            if(Inventory.getCount(Potion[i]) > 0){
                for(Item item : Inventory.getItems()){
                    if(item.getId() == Potion[i]){
                        if(item.getWidgetChild().click(true)){
                            Time.sleep(Random.nextInt(500, 1500));
                        }
                    }
                }
            }
        }
    }

    public static void check() {
        for (int i = 0; i < Main.skills.length; i++) {
            if (Skills.getLevel(Main.skills[i]) <= (Random.nextDouble(Skills.getRealLevel(Main.skills[i]) * 1, Skills.getRealLevel(Main.skills[i]) * 1.10))) {
                if (Main.usePotion[i] == true) {
                    drink(Main.potions[i]);
                }
            }
        }
    }
}