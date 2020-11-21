package up.edu.heythatsmyfishcs301.fish;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import up.edu.heythatsmyfishcs301.R;
import up.edu.heythatsmyfishcs301.game.GameHumanPlayer;
import up.edu.heythatsmyfishcs301.game.GameMainActivity;
import up.edu.heythatsmyfishcs301.game.infoMsg.GameInfo;

/**
 *
 * The FishHumanPlayer handles all of the touch events and surface view interactions
 * that the human sees. Methods in this class handle touch events from the human
 * and update the Surface View accordingly.
 *
 * @author Kyle Sanchez
 * @author Ryan Enslow
 * @author Carina Pineda
 * @author Linda Nguyen
 **/
public class FishHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {
    //Tag for logging
    private static final String TAG = "FishHumanPlayer";

    // the most recent game state, given to us by the FishLocalGame
    private FishGameState gameState;

    // the android activity that we are running
    private GameMainActivity myActivity;

    // the surface view
    private FishView surfaceView;
    private ScoresDrawings scores;
    private FishPlaceView fishPlace;

    // create local board variable and local penguin variable
    private FishPenguin selectedPenguin;
    private FishPenguin[][] pieces;
    private Rect selectedRect;
    FishTile dest;

    // intial player scores
    int p1Score = 0;
    int p2Score = 0;

    // current player turn
    int turn;

    //Variables that connect the rect array to the piece array
    int px = 0;
    int py = 0;

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public FishHumanPlayer(String name) {
        super(name);
    }

    // Returns the top_gui of the game
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    //This method is called when the human player receives the copy of the game state.
    //This method is in charge of updating FishView I think
    @Override
    public void receiveInfo(GameInfo info) {

        // checks if SV is null
        if (surfaceView == null) return;

        if (fishPlace == null) return;

        // ignore the message if it's not a FishGameState message
        if (!(info instanceof FishGameState)){
            return;
        } else {
            // update the state
            // initialize currently selectedPenguin
            selectedPenguin = null;
            // gets gameState
            gameState = (FishGameState)info;

            // initialize piece array
            pieces = gameState.getPieceArray();

            // sets player turn
            turn = gameState.getPlayerTurn();

            // get current player score
            p1Score = gameState.getPlayer1Score();
            p2Score = gameState.getPlayer2Score();

            // set current player score
            scores.setP1Scores(p1Score);
            scores.setP2Score(p2Score);

            //send how many players there are to the placePenguin view
            fishPlace.setGamePhase(gameState.getGamePhase());
            fishPlace.setGameState(gameState);
            fishPlace.invalidate();

            // update the surface view
            surfaceView.setGameState(new FishGameState((FishGameState)info));
            surfaceView.invalidate();
            // invalidate the scores TextView to update
            scores.invalidate();


        }

    }


    //I don't know when this is called or what it does.
    @Override
    public void setAsGui(GameMainActivity activity) {
        // remember the activity
        myActivity = activity;
        activity.setContentView(R.layout.activity_main);
        surfaceView = activity.findViewById(R.id.fishView);
        fishPlace = activity.findViewById(R.id.fishPlaceView);
        scores = activity.findViewById(R.id.scoresDrawings);


        fishPlace.setOnTouchListener(this);
        surfaceView.setOnTouchListener(this);

        // if we have a game state, "simulate" that we have just received
        // the state from the game so that the GUI values are updated
        if (gameState != null) {
            receiveInfo(gameState);
        }
    }


    //This method controls all the touch events for the screen.
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        FishTile[][] b = surfaceView.getGameState().getBoardState();
        Rect[][] rectArr = fishPlace.getRects();



        //Local variables for the location of the touch.
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();


        if(gameState.getGamePhase() == 0){
            if(selectedRect == null){
                for(int i = 0; i < rectArr.length; i++){
                    for(int j = 0; j < rectArr[i].length; j++){
                        if(rectArr[i][j] != null && rectArr[i][j].contains(x,y)){
                            if(i == playerNum){
                                selectedRect = rectArr[i][j];
                                Log.d("Selected Rect", "Selected rect at (" + i + ", " + j + ")");
                                px = i;
                                py = j;
                            }
                        }
                    }
                }
            }
            else if(selectedRect != null){
                for(int i = 0; i < b.length; i++) {
                    for (int j = 0; j < b[i].length; j++) {
                        if(b[i][j] != null && b[i][j].getBoundingBox().contains(x,y)){
                            dest = b[i][j];
                            FishPlaceAction p = new FishPlaceAction(this, dest, gameState.getPieceArray()[px][py]);
                            game.sendAction(p);
                            selectedRect = null;
                            rectArr[px][py] = null;
                            fishPlace.setRects(rectArr);
                            px = 0;
                            py = 0;
                            fishPlace.invalidate();
                        }
                    }
                }
            }
        }

        else {
            //Iterate through the tiles in the 2d board array until you find the one that contains the place where it was touched.
            //There has to be a better way to do this :(
            for (int i = 0; i < b.length; i++) {
                for (int j = 0; j < b[i].length; j++) {
                    if (b[i][j] != null && b[i][j].getBoundingBox().contains(x, y)) {
                        //the player has clicked this bounding box.

                        Log.d("From FishView", "Touched the Fish View at: " + i + ", " + j);
                        Log.d("From human player", "Current player turn is " + turn);
                        if (selectedPenguin == null) {
                            if (b[i][j].getPenguin() == null) {
                                return false;
                            } else if (b[i][j].getPenguin().getPlayer() == playerNum) {
                                //The player has selected this penguin to move
                                selectedPenguin = b[i][j].getPenguin();
                                Log.d("From Human Player", "Selected a valid penguin");

                            } else {
                                //The player did not touch their own penguin
                                //Maybe throw toast
                                Log.d("From Human Player", "Player expected to touch a penguin, but did not");
                            }
                        } else {
                            FishMoveAction m = new FishMoveAction(this, selectedPenguin, b[i][j]);
                            game.sendAction(m);
                            Log.d("From Human Player", "Sent action to Local Game");
                        }
                    }
                }
            }
        }
        return false;
    }
}
