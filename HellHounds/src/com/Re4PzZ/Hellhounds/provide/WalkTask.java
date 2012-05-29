package com.Re4PzZ.Hellhounds.provide;

import com.Re4PzZ.Hellhounds.methods.General;
import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * @author Re4PzZ
 *         www.re4pzz.bplaced.net
 */
public class WalkTask implements Task, Condition {
    @Override
    public boolean validate() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void run() {
        SceneObject Bank = SceneEntities.getNearest(new Filter<SceneObject>() {
            @Override
            public boolean accept(SceneObject sceneObject) {
                return sceneObject.getId() == 11758;
            }
        });
        if(General.inBankArea()){
            if(General.isReady()){

            }else{
                if(Bank.isOnScreen()){


                }else{
                    if(Bank !=null){
                        if(Bank.getLocation().clickOnMap()){
                            while(Players.getLocal().isMoving()){
                                Time.sleep(Random.nextInt(250, 750));
                            }
                        }
                    }
                }
            }

        }
    }

    //Tile's
    Tile[] TilesToLadder = new Tile[] { new Tile(2933, 3355, 0), new Tile(2932, 3360, 0), new Tile(2932, 3365, 0),
            new Tile(2929, 3369, 0), new Tile(2924, 3371, 0), new Tile(2919, 3372, 0),
            new Tile(2915, 3375, 0), new Tile(2910, 3377, 0), new Tile(2905, 3379, 0),
            new Tile(2901, 3382, 0), new Tile(2896, 3383, 0), new Tile(2892, 3386, 0),
            new Tile(2887, 3388, 0), new Tile(2885, 3393, 0) };
    Tile[] TilesToBank = new Tile[] { new Tile(2965, 3381, 0), new Tile(2960, 3381, 0), new Tile(2955, 3381, 0),
            new Tile(2950, 3381, 0), new Tile(2948, 3376, 0), new Tile(2945, 3372, 0),
            new Tile(2945, 3367, 0) };
    Tile[] TilesToWall = new Tile[] { new Tile(2945, 3369, 0), new Tile(2944, 3374, 0), new Tile(2940, 3371, 0),
            new Tile(2938, 3366, 0), new Tile(2938, 3361, 0), new Tile(2936, 3356, 0) };
}
