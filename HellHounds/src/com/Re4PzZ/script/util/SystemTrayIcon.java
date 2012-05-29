package com.Re4PzZ.script.util;

import com.Re4PzZ.Hellhounds.Main;
import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

/**
 * @author Re4PzZ
 *         www.re4pzz.bplaced.net
 */
public class SystemTrayIcon implements Task, Condition {

    public static TrayIcon trayIcon = null;
    private static CheckboxMenuItem lItem = null;
    private static CheckboxMenuItem hItem = null;
    private static CheckboxMenuItem bItem = null;
    private static CheckboxMenuItem nItem = null;
    private static CheckboxMenuItem dItem = null;
    private static CheckboxMenuItem oItem = null;
    private static Timer hTimer = new Timer(3600000);
    private static Timer bTimer = new Timer(1800000);
    private static Timer mTimer = new Timer(15000);
    private int hrs, bhrs;
    private static long startMillis;
    private static boolean[] notifs = new boolean[6];
    private static BufferedReader br;
    private static String messageOfTheDay = null;
    private boolean gns = false;

    public static class Notifiers {
        static final int LEVEL_UPS = 0, HOURLY = 1, BIHOURLY = 2, NAME_HEARD = 3, DEATHS = 4, ON_FINISH = 5;
    }

    @Override
    public boolean validate() {
        return Main.systemTray;
    }

    @Override
    public void run() {
        try {
            // GLOBAL NOTIFICATION
            if (!mTimer.isRunning() && !gns) {
                sendNotification(messageOfTheDay, TrayIcon.MessageType.INFO);
                gns = true;
            }
            // HOURLY NOTIFICATION
            if (!hTimer.isRunning() && notifs[Notifiers.HOURLY] && hItem.getState()) {
                hrs++;
                switch (hrs) {
                    case 1:
                        sendNotification("Running for 1 hour!", TrayIcon.MessageType.INFO);
                        break;
                    default:
                        sendNotification("Running for " + hrs + " hours!", TrayIcon.MessageType.INFO);
                        break;
                }
                hTimer.reset();
            }
            // BIHOURLY NOTIFICATION
            if (!bTimer.isRunning() && notifs[Notifiers.BIHOURLY] && bItem.getState()) {
                bhrs++;
                switch (bhrs) {
                    case 1:
                        sendNotification("Running for 30 minutes!", TrayIcon.MessageType.INFO);
                        break;
                    default:
                        String bihours = bhrs % 2 == 0 ? Integer.toString(bhrs / 2) : Integer.toString(bhrs / 2) + ".5";
                        if (bihours.equals("1"))
                            sendNotification("Running for 1 hour!", TrayIcon.MessageType.INFO);
                        else
                            sendNotification("Running for " + bihours + " hours!", TrayIcon.MessageType.INFO);
                        break;
                }
                bTimer.reset();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean addToTray() {
        try {
            if (SystemTray.isSupported()) {
                SystemTray tray = SystemTray.getSystemTray();
                URL url = null;
                Image image = null;
                url = new URL("http://re4pzz.bplaced.com/images/miniicon.png");
                image = ImageIO.read(url);
                PopupMenu popup = new PopupMenu();
                Menu sub = new Menu("Set notifications...");
                lItem = new CheckboxMenuItem("Level-ups", true);
                hItem = new CheckboxMenuItem("Hourly", true);
                bItem = new CheckboxMenuItem("Bihourly", true);
                nItem = new CheckboxMenuItem("Name heard", true);
                dItem = new CheckboxMenuItem("Deaths", true);
                oItem = new CheckboxMenuItem("On finish", true);
                sub.add(lItem);
                sub.add(hItem);
                sub.add(bItem);
                sub.add(nItem);
                sub.add(dItem);
                sub.add(oItem);
                MenuItem sItem = new MenuItem("-");
                MenuItem fItem = new MenuItem("Leave feedback");
                fItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openURL(Main.feedbackUrl);
                    }
                });
                MenuItem aItem = new MenuItem("Visit Powerbot.org");
                aItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openURL("http://www.powerbot.org");
                    }
                });
                MenuItem xItem = new MenuItem("Visit Re4PzZ.net");
                xItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openURL("http://www.re4pzz.bplaced.net");
                    }
                });
                popup.add(sub);
                popup.add(sItem);
                popup.add(fItem);
                popup.add(aItem);
                popup.add(xItem);
                trayIcon = new TrayIcon(image, "~ Info ~", popup);
                ActionListener iconListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        openURL("http://www.re4pzz.bplaced.net");
                    }
                };
                trayIcon.addActionListener(iconListener);
                tray.add(trayIcon);
                url = new URL("http://www.re4pzz.bplaced.net/messageOfTheDay/");
                br = new BufferedReader(new InputStreamReader(url.openStream()));
                messageOfTheDay = br.readLine();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void sendNotification(String text, TrayIcon.MessageType messageType) {
        try {
            trayIcon.displayMessage("~ Info ~", text, messageType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openURL(final String url) {
        if (!Desktop.isDesktopSupported()) {
            return;
        }
        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
            return;
        }
        try {
            URI uri = new URI(url);
            desktop.browse(uri);
        } catch (Exception ignored) {
        }
    }

    public static void removeTray() {
        // ON FINISH NOTIFICATION
        if (notifs[Notifiers.ON_FINISH] && oItem.getState()) {
            sendNotification("Script has stopped.", TrayIcon.MessageType.INFO);
            Time.sleep(5000);
        }
        try {
            SystemTray.getSystemTray().remove(trayIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
