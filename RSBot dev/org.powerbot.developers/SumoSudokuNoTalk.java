import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import org.powerbot.game.bot.event.MessageEvent;
import org.powerbot.game.bot.event.listener.MessageListener;
import org.powerbot.game.bot.event.listener.PaintListener;

@Manifest(name = "Sumo's Sudoku No Talk", description = "Start at sudoku puzzle..", version = 1.0, authors = {"Sumo"})

public class SumoSudokuNoTalk extends ActiveScript implements PaintListener, MessageListener{

    final Antiban antiban = new Antiban();
    final SudokuStart sudoku = new SudokuStart();

    //Antiban times so it doesn't check extremely often.
    private long lastAnitban = (long) System.currentTimeMillis() / 1000;
    private long nextAntibanCheck =(long) (System.currentTimeMillis() / 1000) + 15;

    private long startTime = System.currentTimeMillis();
    private long elapsedTimeMillis;
    private long elapsedTimeHours;
    private long elapsedTimeMinutes;

    private int sudokusSolved = 0;

    //The model id of runes. Used to grab the number below.
    private int earthRuneId = 8979;
    private int waterRuneId = 8987;
    private int airRuneId = 8975;
    private int fireRuneId = 8980;
    private int mindRuneId = 8982;
    private int lawRuneId = 8981;
    private int chaosRuneId = 8977;
    private int deathRuneId = 8978;
    private int bodyRuneId = 8976;

    //The number I assign each rune to solve the sudoku.
    private int earthRuneNum = 1;
    private int waterRuneNum = 2;
    private int airRuneNum = 3;
    private int fireRuneNum = 4;
    private int mindRuneNum = 5;
    private int lawRuneNum = 6;
    private int chaosRuneNum = 7;
    private int deathRuneNum = 8;
    private int bodyRuneNum = 9;

    //The id of runes that you can click
    private int clickableEarth = 202;
    private int clickableWater = 203;
    private int clickableAir = 204;
    private int clickableFire = 206;
    private int clickableMind = 205;
    private int clickableBody = 207;
    private int clickableDeath = 208;
    private int clickableChaos = 209;
    private int clickableLaw = 210;
    private int clickableClear = 211;

    //Number of runes on the board.
    private int runeSlots = 81;

    //Ali Morrisane's Id.
    private int aliMorrison = 1862;

    ArrayList<Integer> filledSlots = new ArrayList<Integer>();

    //Has the bot started solving the sudoku and finished talking to ali moris?
    private boolean sudokuStarted = false;
    private boolean sudokuFinishedTalkingTo = false;

    //Status for paint.
    private String status;



    @Override
    public void messageReceived(MessageEvent arg0) {
        String messageText = arg0.getMessage();

        if (messageText.contains("still locked")){
            System.out.println("Failed to solve");
        }else if(messageText.contains("locking mechanism")){
            sudokusSolved++;
        }

    }

    @Override
    public void onRepaint(Graphics arg0) {
        Graphics2D g = (Graphics2D) arg0;

        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("Sumo's Sudoku Solver " + getVersion(), 10, 35);
        g.setFont(new Font("Arial", Font.PLAIN, 12));

        elapsedTimeMillis = System.currentTimeMillis() - startTime;
        elapsedTimeHours = elapsedTimeMillis / (1000 * 60 * 60);
        elapsedTimeMillis -= elapsedTimeHours * (1000 * 60 * 60);
        elapsedTimeMinutes = elapsedTimeMillis / (1000 * 60);
        elapsedTimeMillis -= elapsedTimeMinutes * (1000 * 60);
        g.drawString("Status: " + status, 8, 50);
        g.drawString("Sudoku's Solved: " + sudokusSolved, 8, 65);
        g.drawString("Running for " + elapsedTimeHours + " hours and " + elapsedTimeMinutes + " minutes", 8, 80);

    }

    public double getVersion(){
        return 1.0;
    }

    @Override
    protected void setup() {
        provide(new Strategy(antiban, antiban));
        provide(new Strategy(sudoku, sudoku));
    }

    private int getNumFromModelId(int modelid){
        if (modelid == earthRuneId){
            return earthRuneNum;
        }else if(modelid == waterRuneId){
            return waterRuneNum;
        }else if (modelid == airRuneId){
            return airRuneNum;
        }else if(modelid == fireRuneId){
            return fireRuneNum;
        }else if (modelid == mindRuneId){
            return mindRuneNum;
        }else if (modelid == lawRuneId){
            return lawRuneNum;
        }else if (modelid == chaosRuneId){
            return chaosRuneNum;
        }else if (modelid == deathRuneId){
            return deathRuneNum;
        }else if (modelid == bodyRuneId){
            return bodyRuneNum;
        }

        return 0;
    }

    private int getClicakbelIdFromNum(int num){
        if (num == earthRuneNum){
            return clickableEarth;
        }else if (num == waterRuneNum){
            return clickableWater;
        }else if (num == airRuneNum){
            return clickableAir;
        }else if (num == fireRuneNum){
            return clickableFire;
        }else if (num == mindRuneNum){
            return clickableMind;
        }else if (num == lawRuneNum){
            return clickableLaw;
        }else if (num == chaosRuneNum){
            return clickableChaos;
        }else if (num == deathRuneNum){
            return clickableDeath;
        }else if (num == bodyRuneNum){
            return clickableBody;
        }

        return 0;
    }



    private class SudokuStart extends Strategy implements Task{

        @Override
        public void run() {
            status = "Starting Sudoku";
            sudokuStarted = true;
            Widget sud = Widgets.get(288);
            WidgetChild[] sudChilds = sud.getChildren();

            int count = 0;

            int[] widgetIndex = new int[81];

            for (WidgetChild wchild : sudChilds){

                if (wchild.getType() == 6 && count < 81){
                    widgetIndex[count] = wchild.getIndex();
                    count++;
                    filledSlots.add(wchild.getModelId());
                }
            }

            int x = 0;
            int y = 0;
            int z = 1;

            int[] row1 = new int[9];
            int[] row2 = new int[9];
            int[] row3 = new int[9];
            int[] row4 = new int[9];
            int[] row5 = new int[9];
            int[] row6 = new int[9];
            int[] row7 = new int[9];
            int[] row8 = new int[9];
            int[] row9 = new int[9];

            int[] zrow1 = new int[9];
            int[] zrow2 = new int[9];
            int[] zrow3 = new int[9];
            int[] zrow4 = new int[9];
            int[] zrow5 = new int[9];
            int[] zrow6 = new int[9];
            int[] zrow7 = new int[9];
            int[] zrow8 = new int[9];
            int[] zrow9 = new int[9];

            for (int i : filledSlots){
                x++;
                if (z == 1){
                    row1[y] = getNumFromModelId(i);
                    zrow1[y] = getNumFromModelId(i);
                }else if (z == 2){
                    row2[y] = getNumFromModelId(i);
                    zrow2[y] = getNumFromModelId(i);
                }else if (z == 3){
                    row3[y]= getNumFromModelId(i);
                    zrow3[y]= getNumFromModelId(i);
                }else if (z == 4){
                    row4[y] = getNumFromModelId(i);
                    zrow4[y] = getNumFromModelId(i);
                }else if (z == 5){
                    row5[y] = getNumFromModelId(i);
                    zrow5[y] = getNumFromModelId(i);
                }else if (z == 6){
                    row6[y] = getNumFromModelId(i);
                    zrow6[y] = getNumFromModelId(i);
                }else if (z == 7){
                    row7[y] = getNumFromModelId(i);
                    zrow7[y] = getNumFromModelId(i);
                }else if (z == 8){
                    row8[y] = getNumFromModelId(i);
                    zrow8[y] = getNumFromModelId(i);
                }else if (z == 9){
                    row9[y] = getNumFromModelId(i);
                    zrow9[y] = getNumFromModelId(i);
                }
                y++;
                if (y > 8){
                    y = 0;
                }

                if (x % 9 == 0){
                    z++;
                }
            }


            int[][] preboard = {row1, row2, row3, row4, row5, row6, row7, row8, row9};
            int[][] board = {zrow1, zrow2, zrow3, zrow4, zrow5, zrow6, zrow7, zrow8, zrow9};

            SudokuSolve solve = new SudokuSolve();
            if (!solve.start(board)){
                System.out.println("Failed to solve!");
            }

            /*System.out.println("\n");

                   for (int[] p : preboard){
                       System.out.print("{");
                       for (int q : p){
                           System.out.print(q + ",");
                       }
                       System.out.print("}\n");
                   }

                   System.out.println("\n\n");

                   for (int[] p : board){
                       System.out.print("{");
                       for (int q : p){
                           System.out.print(q + ",");
                       }
                       System.out.print("}\n");
                   }*/


            //int c = 0;
            //int col = 0;
            //int totalCount = 0;

            status = "Completing Sudoku";

            //Start at first square increment by one to end

            /*for (int[] a : preboard){
                       for (int b : a){
                           if (b == 0 && isRunning()){
                               int numToSet = board[c][col];
                               int widgetId = getClicakbelIdFromNum(numToSet);
                               sud.getChild(widgetId).click(true);
                               Time.sleep(Random.nextInt(500, 1000));
                               sudChilds[widgetIndex[totalCount]].click(true);
                               Time.sleep(Random.nextInt(500, 1000));
                           }
                           totalCount++;
                           col++;
                       }
                       col = 0;
                       c++;
                   }*/

            //Randomly select a square to fill.

            ArrayList<Integer> slotsToFill = new ArrayList<Integer>();

            int tCount = 0;

            for (int[] a : preboard){
                for (int b : a){
                    if (b == 0 && isRunning()){
                        slotsToFill.add(tCount);
                        //System.out.println("Tcount = " + tCount);
                    }
                    tCount++;
                }
            }

            while (slotsToFill.size() > 0 && isRunning()){
                int RandNum = Random.nextInt(0, slotsToFill.size()-1);
                int Rand = slotsToFill.get(RandNum);
                int rowToSet = getRow(Rand);
                int colToSet = getCol(Rand);
                int numToSet = board[rowToSet][colToSet];
                int widgetId = getClicakbelIdFromNum(numToSet);
                sud.getChild(widgetId).click(true);
                Time.sleep(Random.nextInt(500, 1000));
                sudChilds[widgetIndex[Rand]].click(true);
                slotsToFill.remove(RandNum);
                Time.sleep(Random.nextInt(500, 1000));
            }

            Time.sleep(Random.nextInt(1000, 1500));

            Widgets.get(288).getChild(9).click(true);

            Time.sleep(Random.nextInt(2500, 3000));

            Widgets.get(1188, 3).click(true);

            Time.sleep(Random.nextInt(750, 1000));

            //Sudoku Solved Open Casket
            sudokuFinishedTalkingTo = false;
            sudokuStarted = false;

            filledSlots.clear();

        }

        private int getRow(int num){

            int floor = (int)num / 9;
            return floor;
        }

        private int getCol(int num){
            int floor = (int)(num + 1) / 9;
            int returnNum = num;
            for (int i = 0; i < floor; i++){
                returnNum-=9;
            }

            if (returnNum < 0){
                return 8;
            }

            return returnNum;
        }

        @Override
        public boolean validate(){
            return Widgets.get(288, clickableAir).isOnScreen() && !sudokuStarted;
        }

    }

    private class Antiban extends Strategy implements Task{

        @Override
        public void run() {
            String previousStatus = status;
            status = "Antiban";
            int ranNum = Random.nextInt(1,5);
            if (ranNum == 1){
                Camera.setAngle(Random.nextInt(1, 180));
            }else if(ranNum == 2){
                Camera.setAngle(Random.nextInt(180, 360));
            }else if (ranNum == 3){
                Camera.setPitch(Random.nextInt(25,60));
            }else if (ranNum == 4){
                Camera.setPitch(Random.nextInt(26, 90));
            }else{
                Time.sleep(Random.nextInt(250, 1000));
            }

            Time.sleep(1000);
            status = previousStatus;

            lastAnitban = (long)System.currentTimeMillis() / 1000;
            nextAntibanCheck = lastAnitban + (17 + Random.nextInt(1, 14));
        }

        @Override
        public boolean validate() {
            long now = (long)System.currentTimeMillis() / 1000;
            if (now >= nextAntibanCheck && Game.isLoggedIn()){
                return true;
            }

            return false;
        }

    }

    //Sudoku Solving thanks to http://www.colloquial.com/games/sudoku/java_sudoku.html

    public class SudokuSolve {

        /**
         * Print the specified Sudoku problem and its solution.  The
         * problem is encoded as specified in the class documentation
         * above.
         *
         * @param args The command-line arguments encoding the problem.
         */
        public boolean start(int[][]board) {
            int[][] matrix = board;
            //writeMatrix(matrix);
            if (solve(0,0,matrix)){    // solves in place
                return true;
            }


            return false;
        }

        boolean solve(int i, int j, int[][] cells) {
            if (i == 9) {
                i = 0;
                if (++j == 9)
                    return true;
            }
            if (cells[i][j] != 0)  // skip filled cells
                return solve(i+1,j,cells);

            for (int val = 1; val <= 9; ++val) {
                if (legal(i,j,val,cells)) {
                    cells[i][j] = val;
                    if (solve(i+1,j,cells))
                        return true;
                }
            }
            cells[i][j] = 0; // reset on backtrack
            return false;
        }

        boolean legal(int i, int j, int val, int[][] cells) {
            for (int k = 0; k < 9; ++k)  // row
                if (val == cells[k][j])
                    return false;

            for (int k = 0; k < 9; ++k) // col
                if (val == cells[i][k])
                    return false;

            int boxRowOffset = (i / 3)*3;
            int boxColOffset = (j / 3)*3;
            for (int k = 0; k < 3; ++k) // box
                for (int m = 0; m < 3; ++m)
                    if (val == cells[boxRowOffset+k][boxColOffset+m])
                        return false;

            return true; // no violations, so it's legal
        }

        int[][] parseProblem(String[] args) {
            int[][] problem = new int[9][9]; // default 0 vals
            for (int n = 0; n < args.length; ++n) {
                int i = Integer.parseInt(args[n].substring(0,1));
                int j = Integer.parseInt(args[n].substring(1,2));
                int val = Integer.parseInt(args[n].substring(2,3));
                problem[i][j] = val;
            }
            return problem;
        }
    }

}