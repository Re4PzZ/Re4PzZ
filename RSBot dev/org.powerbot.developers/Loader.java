import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.bot.event.listener.PaintListener;

import java.awt.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

@Manifest(name = "Package loader.", authors = "Andy", description = "Loads packaged script for testing.", website = "http://scripted.it")
public class Loader extends ActiveScript implements PaintListener {
    private ActiveScript script = null;
    private ArrayList<Strategy> strategies;
    private final static String packageOrigin = "file:///C:/Users/Alexander M/Documents/My Programms/IntelliJ/Re4PzZ/bincom/Re4PzZ/Hellhounds"; //Path to origination of package


    protected void setup() {
        try {
            final URLClassLoader loader = new URLClassLoader(new URL[]{new URL(packageOrigin)});              //Creates package loader
            this.script = ((ActiveScript) loader.loadClass("Main").newInstance());      //Main class name
            final Method main = this.script.getClass().getMethod("provideStrategies");                                      //Make sure your main class includes public ArrayList<Action> provideStrategies()
            strategies = (ArrayList<Strategy>) main.invoke(this.script);                                                                      //Receives the Actions for your script
            if (strategies == null) {
                stop();                                                                                                                                                            //If none returned, stop script
            } else {
                for (final Strategy strategy : strategies) {                                                                                              //Loops through actions
                    System.out.println("Loading " + strategy.getClass().getName());                                    //Debug
                    provide(strategy);                                                                                                                                      //Provides the action to rsbots script framework                                                                                                //Accepts the strategy to rsbots action listener
                }
            }

            final Method threads = this.script.getClass().getMethod("provideRunnables");                              //Implement public ArrayList<Runnable> provideRunnables() even if you dont need it
            final ArrayList<Runnable> runnables = (ArrayList<Runnable>) threads.invoke(this.script);          //Receives the runnables from the script
            if (runnables != null) {
                for (Runnable r : runnables) {                                                                                                                  //Loops through runnables
                    if (r instanceof Runnable)                                                                                                                      //Ensures actually is an instance of runnable
                        new Thread(r).start();                                                                                                                  //Starts the thread
                }
            }
        } catch (Exception notIgnored) {
            notIgnored.printStackTrace();
        }
    }

    public void onRepaint(Graphics graphics) {
        if ((this.script != null) && ((this.script instanceof PaintListener)))                                                  //Calls paintlistener if exists.
            ((PaintListener) this.script).onRepaint(graphics);
    }

    public void onStop() {
        if (script != null)
            script.onStop();                                                                                                                                                          //Calls the scripts onstop method.
    }
}