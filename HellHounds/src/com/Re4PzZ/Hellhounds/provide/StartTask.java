package com.Re4PzZ.Hellhounds.provide;

import com.Re4PzZ.Hellhounds.Main;
import com.Re4PzZ.Hellhounds.util.GUI;
import com.Re4PzZ.script.util.FileManager;
import com.Re4PzZ.script.util.SystemTrayIcon;
import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Re4PzZ
 *         www.re4pzz.bplaced.net
 */
public class StartTask implements Task, Condition {

    public GUI gui;

    @Override
    public boolean validate() {
        return Main.isStart;
    }

    @Override
    public void run() {
        if (SystemTrayIcon.addToTray()) {
            SystemTrayIcon.sendNotification("Welcome to ~ Hellhounds ~", TrayIcon.MessageType.INFO);
            Main.systemTray = true;
        }
        if(Skills.getRealLevel(Skills.AGILITY) < 80){
            SystemTrayIcon.sendNotification("Sry your Agility level is to low! This script supports only 80+ Agility atm :(", TrayIcon.MessageType.ERROR);

        }
        gui = new GUI();
        gui.setVisible(true);
        while (gui.isVisible()) {
            Time.sleep(50);
        }

        //General FileManager
        if(FileManager.checkForDir()){
            FileManager.loadImages();
        }

        //Paint Variables
        Main.startSTR = Skills.getExperience(Skills.STRENGTH);
        Main.startDEF = Skills.getExperience(Skills.DEFENSE);
        Main.startHP = Skills.getExperience(Skills.CONSTITUTION);
        Main.startATT = Skills.getExperience(Skills.ATTACK);
        Main.startRNG = Skills.getExperience(Skills.RANGE);
        Main.startMAG = Skills.getExperience(Skills.MAGIC);
        Main.ATTLVL = Skills.getRealLevel(Skills.ATTACK);
        Main.STRLVL = Skills.getRealLevel(Skills.STRENGTH);
        Main.DEFLVL = Skills.getRealLevel(Skills.DEFENSE);
        Main. HPLVL = Skills.getRealLevel(Skills.CONSTITUTION);
        Main.MAGLVL = Skills.getRealLevel(Skills.MAGIC);
        Main.RNGLVL = Skills.getRealLevel(Skills.RANGE);
        Main.startTime = System.currentTimeMillis();

        Main.isStart = false;
    }
}
