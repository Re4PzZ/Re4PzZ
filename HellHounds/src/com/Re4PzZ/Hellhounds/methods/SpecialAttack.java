package com.Re4PzZ.Hellhounds.methods;

import com.Re4PzZ.Hellhounds.Main;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 * @author: Re4PzZ(Alexander M.)
 * www.re4pzz.bplaced.net/
 */
public class SpecialAttack {
    public static void getSpec(){
        int id = Equipment.getItem(Equipment.WEAPON).getID();
        switch (id) {
            case 861:
                Main.specialPercent = 600;
                break;
            case 817:
                Main.specialPercent = 600;
                break;
            case 1305:
                Main.specialPercent = 800;
                break;
            case 4151:
                Main.specialPercent = 550;
                break;
            case 4153:
                Main.specialPercent = 550;
                break;
            case 1377:
                Main.specialPercent = 1000;
                break;
            case 4587:
                Main.specialPercent = 600;
                break;
            case 5698:
                Main.specialPercent = 250;
                break;
            case 1413:
                Main.specialPercent = 250;
                break;
            case 11700:
                Main.specialPercent = 650;
                break;
            case 11694:
                Main.specialPercent = 500;
                break;
            case 11696:
                Main.specialPercent = 1000;
                break;
            case 11698:
                Main.specialPercent = 500;
                break;
            case 11730:
                Main.specialPercent = 1000;
                break;
            default:
                Main.specialPercent = 1000;
                break;
        }
    }
    public static void special() {
        if (Main.combat.isSpecialEnabled()) {
            return;
        } else {
            WidgetChild specialBar = Widgets.get(884).getChild(4);
            int c = 0;
            while (Tabs.getCurrent().getIndex() != 0 && c < 1) {
                Tabs.ATTACK.open();
                Time.sleep(600, 700);
                c++;
            }
            specialBar.click(true);
            for (int i = 0; i < 50; i++) {
                if (Combat.isSpecialEnabled()) {
                    i = 50;
                }
                Main.sleep(100);
            }
        }
    }
}
