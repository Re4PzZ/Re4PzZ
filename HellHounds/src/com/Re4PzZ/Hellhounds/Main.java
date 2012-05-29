package com.Re4PzZ.Hellhounds;

import com.Re4PzZ.Hellhounds.provide.AntibanTask;
import com.Re4PzZ.Hellhounds.provide.FightTask;
import com.Re4PzZ.Hellhounds.provide.StartTask;
import com.Re4PzZ.Hellhounds.provide.WalkTask;
import com.Re4PzZ.Hellhounds.util.GUI;
import com.Re4PzZ.script.util.SystemTrayIcon;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.bot.event.MessageEvent;
import org.powerbot.game.bot.event.listener.MessageListener;
import org.powerbot.game.bot.event.listener.PaintListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author Re4PzZ
 *         www.re4pzz.bplaced.net
 */
@Manifest(
        name = "~ HellHounds ~",
        authors = "Re4Pzz",
        version = 0.1,
        description = "Fights Hellhounds in Taverly and banks in Falador.",
        website = "http://re4pzz.bplaced.net/",
        premium = false
)
public class Main extends ActiveScript implements MessageListener, PaintListener, MouseListener {

    /*<=> <=> <=> <= <=> <=> <=> <=> <=> Variables declaration <=> <=> <=> <= <=> <=> <=> <=> <=> */

    public static String[] FoodNames = {"Food"};
    public static boolean pickUpCharms;
    public static int[] FoodIds;
    public static boolean isStart = true;
    public static String feedbackUrl = "";//TODO
    public static Timer errorTimer = new Timer(5000);
    public static boolean systemTray = false;
    public static final Timer Runtime = new Timer(0);
    public static Image icon;

    public GUI gui;

    //Potions
    public static int[] SuperAttackPotion = {145, 147, 149, 2436};
    public static int[] SuperStrengthPotion = {157, 159, 161, 2440};
    public static int[] SuperDefensePotion = {163, 165, 167, 2442};
    public static int[] AttackPotion = {121, 123, 125, 2428};
    public static int[] StrengthPotion = {113, 115, 117, 119};
    public static int[] DefensePotion = {2432, 133, 135, 137};
    public static int[] skills = {Skills.ATTACK, Skills.STRENGTH, Skills.DEFENSE, Skills.ATTACK, Skills.STRENGTH, Skills.DEFENSE};
    public static int[][] potions = {AttackPotion, StrengthPotion, DefensePotion, SuperAttackPotion, SuperStrengthPotion, SuperDefensePotion};
    public static boolean useNormalAttackPotion = false;
    public static boolean useNormalStrengthPotion = false;
    public static boolean useNormalDefensePotion = false;
    public static boolean useSuperAttackPotion = false;
    public static boolean useSuperStrengthPotion = false;
    public static boolean useSuperDefensePotion = false;
    public static boolean[] usePotion = {useNormalAttackPotion, useNormalStrengthPotion, useNormalDefensePotion,
            useSuperAttackPotion, useSuperStrengthPotion, useSuperDefensePotion};

    //Teleport
    public static int FaladorTeletab = 8009;

    public static NPC Hellhound;


    /*<=> <=> <=> <= <=> <=> <=> <=> <=> End of Variables declaration <=> <=> <=> <= <=> <=> <=> <=> <=>*/

    @Override
    protected void setup() {
        if(!Game.isLoggedIn()){
            log.warning("Please start logged in!");
            stop();
        }
        try {
            final StartTask start = new StartTask();
            provide(new Strategy(start, start));
            final FightTask fight = new FightTask();
            provide(new Strategy(fight, fight));
            final WalkTask walk = new WalkTask();
            provide(new Strategy(walk, walk));
            final AntibanTask antiban = new AntibanTask();
            provide(new Strategy(antiban, antiban));
            final SystemTrayIcon icon = new SystemTrayIcon();
            provide(new Strategy(icon, icon));
        } catch (Exception x) {
            if (!errorTimer.isRunning()) {
                x.printStackTrace();
                errorTimer.reset();
            }
        }
    }

    public void onStop() {
        SystemTrayIcon.removeTray();
    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {
        String text = messageEvent.getMessage();
        if (text.contains("Oh dear you are dead.")) {
            SystemTrayIcon.sendNotification("Your character has died.", TrayIcon.MessageType.WARNING);
        }
        if (text.contains(Players.getLocal().getName())) {
            SystemTrayIcon.sendNotification("Your name was said in game.", TrayIcon.MessageType.WARNING);
        }
        if (text.contains("You've just advanced a ")) {
            SystemTrayIcon.sendNotification(text, TrayIcon.MessageType.INFO);
        }
    }

    @Override
    public void onRepaint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.drawString("Runtime: " + Runtime.toElapsedString(), 100, 100);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        pointX = arg0.getPoint();
        if(right.contains(pointX)){
            if(showHP){
                showHP = false;
                showATT = true;
            }else if(showATT){
                showATT = false;
                showSTR = true;
            }else if(showSTR){
                showSTR = false;
                showDEF = true;
            }else if(showDEF){
                showDEF = false;
                showMAG = true;
            }else if(showMAG){
                showMAG = false;
                showRNG = true;
            }else if(showRNG){
                showRNG = false;
                showHP = true;
            }
        }
        if(left.contains(pointX)){
            if(showHP){
                showHP = false;
                showRNG = true;
            }else if(showATT){
                showATT = false;
                showHP = true;
            }else if(showSTR){
                showSTR = false;
                showATT = true;
            }else if(showDEF){
                showDEF = false;
                showSTR = true;
            }else if(showMAG){
                showMAG = false;
                showDEF = true;
            }else if(showRNG){
                showRNG = false;
                showMAG = true;
            }
        }
        if(xp.contains(pointX)){
            if(showTitle){
                showXP = true;
                showTitle = false;
            }else if(showXP){
                showXP = false;
                showTitle = true;
            }else if(showInfo){
                showXP = true;
                showInfo = false;
            }
        }
        if(info.contains(pointX)){
            if(showTitle){
                showInfo = true;
                showTitle = false;
            }else if(showXP){
                showXP = false;
                showInfo = true;
            }else if(showInfo){
                showTitle = true;
                showInfo = false;
            }
        }
        if(hidePaint.contains(pointX)){
            if(showHide){
                showHide = false;
                showTitle = true;
            }else{
                showHide = true;
                showTitle = false;
                showXP = false;
                showInfo = false;
            }
        }    }

    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    //Paint
    public static Point pointX;
    public static boolean showHP = true;
    public static boolean showATT = false;
    public static boolean showSTR = false;
    public static boolean showDEF = false;
    public static boolean showMAG = false;
    public static boolean showRNG = false;
    public static Rectangle left;
    public static Rectangle right;
    public static Rectangle hidePaint;
    public static Rectangle xp;
    public static Rectangle info;
    public static int startSTR, startDEF, startHP, startATT, startMAG, startRNG;
    public static int STRPH, DEFPH, HPPH, ATTPH, RNGPH, MAGPH, STRpr, DEFpr, HPpr, ATTpr;
    public static int RNGpr, MAGpr, currentSTR, currentDEF, currentHP, currentATT;
    public static int currentMAG, currentRNG, currentSTRLVL, currentRNGLVL;
    public static int currentMAGLVL, currentDEFLVL, currentHPLVL, currentATTLVL, HPXPperHour, ATTXPperHour, STRXPperHour, MAGXPperHour, RNGXPperHour, DEFXPperHour;
    public static int STRLVL, ATTLVL, HPLVL, DEFLVL, RNGLVL, MAGLVL;
    public static int currentXP, nextLVL, LVLGained, LVLpr, XPph, ttl;
    public static long minTime;
    public static long hrTime;
    public static long secsStart;
    public static long startTime;
    public static final RenderingHints antialiasing = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    public static boolean paintSTR = false;
    public static boolean paintATT = false;
    public static boolean paintDEF = false;
    public static boolean paintHP = false;
    public static boolean paintRNG = false;
    public static boolean paintMAG = false;
    public static int Gained = 0;
    public static int x, y;
    public static final Color color1 = new Color(153, 153, 153);
    public static final Color color2 = new Color(0, 0, 0);
    public static final BasicStroke stroke1 = new BasicStroke(1);
    public static final Font font = new Font("Arial", 1, 18);
    public static boolean showXP = false;
    public static boolean showTitle = true;
    public static boolean showInfo = false;
    public static boolean showHide = false;
    public static int Killcount = 0;

    //Loot
    public static String[] loot = {"ancient","Clue"};
    public static String[] lootCharms = {"ancient","Clue","charm"};
    public static String[] junk = {"Pie dish","Vial","bones"};
    public static int ClueScrolldrop = 0;
    public static int Charmdrop = 0;
    public static int Effigydrop = 0;
    public static int Food, FoodAmount;

    //Gereral FileManager
    public static String Script = "Hellhounds//";
    public static String[] Images = {"Title.png", "Info.png", "XP.png",
            "HP.png", "ATT.png", "STR.png",
            "DEF.png", "MAG.png", "RNG.png"
    };
    public static BufferedImage Titlepaint;
    public static BufferedImage Infopaint;
    public static BufferedImage XPpaint;
    public static BufferedImage HPpaint;
    public static BufferedImage ATTpaint;
    public static BufferedImage STRpaint;
    public static BufferedImage DEFpaint;
    public static BufferedImage MAGpaint;
    public static BufferedImage RNGpaint;
    public static BufferedImage SkillPaint;
    public static BufferedImage[] BufferdImages= {Titlepaint, Infopaint, XPpaint, HPpaint
            , ATTpaint, STRpaint, DEFpaint, MAGpaint
            , RNGpaint, SkillPaint};
}