package com.Re4PzZ.Hellhounds.util;

import com.Re4PzZ.Hellhounds.Main;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Skills;

import java.awt.*;

/**
 * @author: Re4PzZ(Alexander M.)
 * www.re4pzz.bplaced.net/
 */
public class Paint {

    public static void loadGeneralPaintData(Graphics graphics){
        Graphics2D g = (Graphics2D)graphics;
        g.setRenderingHints(Main.antialiasing);
        long secs = (System.currentTimeMillis() - Main.minTime) / 1000;
        long mins = ((System.currentTimeMillis() - Main.hrTime) / 60000);
        long hrs = (Main.secsStart / 3600);
        Main.secsStart = (System.currentTimeMillis() - Main.startTime) / 1000;
        if (secs >= 60) {
            Main.minTime = System.currentTimeMillis();
        }

        if (mins >= 60) {
            Main.hrTime = System.currentTimeMillis();
        }

        if (!Main.paintSTR) {
            if(Main.showSTR){
                Main.showSTR = false;
                Main.showDEF = true;
            }
            Main.currentSTR = Skills.getExperience(Skills.STRENGTH) - Main.startSTR;
            if (Main.currentSTR > 0) {
                Main.paintSTR = true;
            }
        } else {
            Main.currentSTR = Skills.getExperience(Skills.STRENGTH) - Main.startSTR;
            int STRXPperSec = 0;
            if ((mins > 0 || hrs > 0 || secs > 0) && Main.currentSTR > 0) {
                STRXPperSec = ((int) Main.currentSTR) / (int) (secs + (mins * 60) + (hrs * 60 * 60));

            }
            int STRXPperMin = STRXPperSec * 60;
            Main.STRXPperHour = STRXPperMin * 60;
            Main.currentSTRLVL = Skills.getRealLevel(Skills.STRENGTH);
            Main.STRpr = getPercentToNextLevel(Skills.STRENGTH);
        }
        if (!Main.paintDEF) {
            if(Main.showDEF){
                Main.showDEF = false;
                Main.showHP = true;
            }
            Main.currentDEF = Skills.getExperience(Skills.DEFENSE) - Main.startDEF;
            if (Main.currentDEF > 0) {
                Main.paintDEF = true;
            }
        } else {
            Main.currentDEF = Skills.getExperience(Skills.DEFENSE) - Main.startDEF;
            int DEFXPperSec = 0;
            if ((mins > 0 || hrs > 0 || secs > 0) && Main.currentDEF > 0) {
                DEFXPperSec = ((int) Main.currentDEF) / (int) (secs + (mins * 60) + (hrs * 60 * 60));
            }
            int DEFXPperMin = DEFXPperSec * 60;
            Main.DEFXPperHour = DEFXPperMin * 60;
            Main.currentDEFLVL = Skills.getRealLevel(Skills.DEFENSE);
            Main.DEFpr = getPercentToNextLevel(Skills.DEFENSE);
        }
        if (!Main.paintHP) {
            Main.currentHP = Skills.getExperience(Skills.CONSTITUTION) - Main.startHP;
            if (Main.currentHP > 0) {
                Main.paintHP = true;
            }
        } else {
            Main.currentHP = Skills.getExperience(Skills.CONSTITUTION) - Main.startHP;
            int HPXPperSec = 0;
            if ((mins > 0 || hrs > 0 || secs > 0) && Main.currentHP > 0) {
                HPXPperSec = ((int) Main.currentHP) / (int) (secs + (mins * 60) + (hrs * 60 * 60));

            }
            int HPXPperMin = HPXPperSec * 60;
            Main.HPXPperHour = HPXPperMin * 60;
            Main.currentHPLVL = Skills.getRealLevel(Skills.CONSTITUTION);
            Main.HPpr = getPercentToNextLevel(Skills.CONSTITUTION);
        }
        if (!Main.paintATT) {
            if(Main.showATT){
                Main.showATT = false;
                Main.showRNG = true;
            }
            Main.currentATT = Skills.getExperience(Skills.ATTACK) - Main.startATT;
            if (Main.currentATT > 0) {
                Main.paintATT = true;
            }
        } else {
            Main.currentATT = Skills.getExperience(Skills.ATTACK) - Main.startATT;
            int ATTXPperSec = 0;
            if ((mins > 0 || hrs > 0 || secs > 0) && Main.currentATT > 0) {
                ATTXPperSec = ((int) Main.currentATT) / (int) (secs + (mins * 60) + (hrs * 60 * 60));

            }
            int ATTXPperMin = ATTXPperSec * 60;
            Main.ATTXPperHour = ATTXPperMin * 60;
            Main.currentATTLVL = Skills.getRealLevel(Skills.ATTACK);
            Main.ATTpr = getPercentToNextLevel(Skills.ATTACK);

        }

        if (!Main.paintRNG) {
            if(Main.showRNG){
                Main.showRNG = false;
                Main.showMAG = true;
            }
            Main.currentRNG = Skills.getExperience(Skills.RANGE) - Main.startRNG;
            if (Main.currentRNG > 0) {
                Main.paintRNG = true;
            }
        } else {
            Main.currentRNG = Skills.getExperience(Skills.RANGE) - Main.startRNG;
            int RNGXPperSec = 0;
            if ((mins > 0 || hrs > 0 || secs > 0) && Main.currentRNG > 0) {
                RNGXPperSec = ((int) Main.currentRNG) / (int) (secs + (mins * 60) + (hrs * 60 * 60));
            }
            int RNGXPperMin = RNGXPperSec * 60;
            Main.RNGXPperHour = RNGXPperMin * 60;
            Main.currentRNGLVL = Skills.getRealLevel(Skills.RANGE);
            Main.RNGpr = getPercentToNextLevel(Skills.RANGE);
        }

        if (!Main.paintMAG) {
            if(Main.showMAG){
                Main.showMAG = false;
                Main.showSTR = true;
            }
            Main.currentMAG = Skills.getExperience(Skills.MAGIC) - Main.startMAG;
            if (Main.currentMAG > 0) {
                Main.paintMAG = true;
            }
        } else {
            Main.currentMAG = Skills.getExperience(Skills.MAGIC) - Main.startMAG;
            int MAGXPperSec = 0;
            if ((mins > 0 || hrs > 0 || secs > 0) && Main.currentMAG > 0) {
                MAGXPperSec = ((int) Main.currentMAG) / (int) (secs + (mins * 60) + (hrs * 60 * 60));

            }
            int MAGXPperMin = MAGXPperSec * 60;
            Main.MAGXPperHour = MAGXPperMin * 60;
            Main.currentMAGLVL = Skills.getRealLevel(Skills.MAGIC);
            Main.MAGpr = getPercentToNextLevel(Skills.MAGIC);

        }

        if(Main.showHP){
            Main.SkillPaint = Main.HPpaint;
            Main.currentXP = Skills.getExperience(Skills.CONSTITUTION);
            Main.Gained = Main.currentHP;
            Main.nextLVL = getExpToNextLevel(Skills.CONSTITUTION);
            Main.LVLGained = Skills.getRealLevel(Skills.CONSTITUTION) - Main.HPLVL;
            Main.LVLpr = getPercentToNextLevel(Skills.CONSTITUTION);

        }else if(Main.showATT){
            Main.SkillPaint = Main.ATTpaint;
            Main.currentXP = Skills.getExperience(Skills.ATTACK);
            Main.Gained = Main.currentATT;
            Main.nextLVL = getExpToNextLevel(Skills.ATTACK);
            Main.LVLGained = Skills.getRealLevel(Skills.ATTACK) - Main.ATTLVL;
            Main.LVLpr = getPercentToNextLevel(Skills.ATTACK);

        }else if(Main.showSTR){
            Main.SkillPaint = Main.STRpaint;
            Main.currentXP = Skills.getExperience(Skills.STRENGTH);
            Main.Gained = Main.currentSTR;
            Main.nextLVL = getExpToNextLevel(Skills.STRENGTH);
            Main.LVLGained = Skills.getRealLevel(Skills.STRENGTH) - Main.STRLVL;
            Main.LVLpr = getPercentToNextLevel(Skills.STRENGTH);

        }else if(Main.showDEF){
            Main.SkillPaint = Main.DEFpaint;
            Main.currentXP = Skills.getExperience(Skills.DEFENSE);
            Main.Gained = Main.currentDEF;
            Main.nextLVL = getExpToNextLevel(Skills.DEFENSE);
            Main.LVLGained = Skills.getRealLevel(Skills.DEFENSE) - Main.DEFLVL;
            Main.LVLpr = getPercentToNextLevel(Skills.DEFENSE);

        }else if(Main.showMAG){
            Main.SkillPaint = Main.MAGpaint;
            Main.currentXP = Skills.getExperience(Skills.MAGIC);
            Main.Gained = Main.currentMAG;
            Main.nextLVL = getExpToNextLevel(Skills.MAGIC);
            Main.LVLGained = Skills.getRealLevel(Skills.MAGIC) - Main.MAGLVL;
            Main.LVLpr = getPercentToNextLevel(Skills.MAGIC);

        }else if(Main.showRNG){
            Main.SkillPaint = Main.RNGpaint;
            Main.currentXP = Skills.getExperience(Skills.RANGE);
            Main.Gained = Main.currentRNG;
            Main.nextLVL = getExpToNextLevel(Skills.RANGE);
            Main.LVLGained = Skills.getRealLevel(Skills.RANGE) - Main.RNGLVL;
            Main.LVLpr = getPercentToNextLevel(Skills.RANGE);

        }else{
            System.out.println("Error loading skills!");
        }
        Main.XPph = (int) ((Main.Gained) * 3600000D / (System.currentTimeMillis() - Main.startTime));
        Main.x = 0;
        Main.y = Widgets.get(137).getChild(0).getAbsoluteY();
        Main.xp = new Rectangle(Main.x+470, Main.y+35, 35, 35);
        Main.info = new Rectangle(Main.x+470, Main.y+70, 35, 35);
        Main.left = new Rectangle(Main.x+215, Main.y+43, 15, 15);
        Main.right = new Rectangle(Main.x+290, Main.y+43, 15,15);

        long timeToLevel = 0;
        String timeToLevel2 = "Calculating...";
        if (Main.XPph > 0) {
            timeToLevel = (Main.nextLVL * 60 / Main.XPph);

            if (timeToLevel >= 60) {
                long thours = (int) timeToLevel / 60;
                long tmin = (timeToLevel - (thours * 60));
                timeToLevel2 = thours + " h, " + tmin
                        + " min";
            } else {
                timeToLevel2 = timeToLevel + " Minutes";
            }
        }
        g.setColor(Main.color2);
        g.setStroke(Main.stroke1);
        g.setFont(Main.font);

        if(Main.showTitle) {
            g.setFont(Main.font);
            g.drawImage( Main.Titlepaint, Main.x, Main.y, null);
        }

        if(Main.showXP){
            g.drawImage(Main.XPpaint, Main.x, Main.y, null);
            g.drawImage(Main.SkillPaint, Main.x-15, Main.y, null);
            g.drawRect(Main.x+215, Main.y+43, 15, 15);
            g.drawRect(Main.x+290, Main.y+43, 15, 15);
            g.drawString("" + Main.currentXP, Main.x+127, Main.y+78);
            g.drawString("" + Main.Gained, Main.x+123, Main.y+100);
            g.drawString("" + Main.nextLVL, Main.x+374, Main.y+78);
            g.drawString("" + Main.LVLGained + "(" + Main.LVLpr + "%)", Main.x+370, Main.y+121);
            g.drawString("" + Main.XPph, Main.x+135, Main.y+121);
            g.drawString("" + timeToLevel2, Main.x+395, Main.y+100);
        }

        int CurrentXP = Main.currentATT + Main.currentSTR + Main.currentDEF +
                Main.currentRNG + Main.currentMAG + Main.currentHP;
        int AllXPph = (int) ((CurrentXP) * 3600000D / (System.currentTimeMillis() - Main.startTime));
        int CurrentLevels = (Skills.getRealLevel(Skills.DEFENSE) - Main.DEFLVL) + (Skills.getRealLevel(Skills.CONSTITUTION) - Main.HPLVL) +
                (Skills.getRealLevel(Skills.ATTACK) - Main.ATTLVL) + (Skills.getRealLevel(Skills.STRENGTH) - Main.STRLVL) +
                (Skills.getRealLevel(Skills.MAGIC) - Main.MAGLVL) + (Skills.getRealLevel(Skills.RANGE) - Main.RNGLVL);

        Main.Killcount = CurrentXP / 468;
        if(Main.showInfo){
            g.drawImage( Main.Infopaint, Main.x, Main.y, null);
            g.drawString(""+ hrs + ":" + mins + ":" + secs, Main.x+115, Main.y+56);
            g.drawString("" + CurrentXP, Main.x+108, Main.y+78);
            g.drawString("" + AllXPph, Main.x+123, Main.y+100);
            g.drawString("" + CurrentLevels, Main.x+111, Main.y+121);
            g.drawString("" + Main.Killcount, Main.x+290, Main.y+56);
            g.drawString("" + Main.ClueScrolldrop, Main.x+424, Main.y+78);
            g.drawString("" + Main.Effigydrop, Main.x+395, Main.y+100);
            g.drawString("" + Main.Charmdrop, Main.x+393, Main.y+121);

        }

    }
    
    public static int getExpToNextLevel(int Skill){
        int real = Skills.getRealLevel(Skill);
        int xp = Skills.getExperience(Skill);
        return Skills.getExperienceRequired((real + 1)) - xp;
    }
    
    public static int getPercentToNextLevel(int Skill){
        int real = Skills.getRealLevel(Skill);
        int xp = getExpToNextLevel(Skill);
        int total = Skills.getExperienceToLevel(real, real +1);
        return (int) (((float) xp / (float) total) * 100);    }
}
