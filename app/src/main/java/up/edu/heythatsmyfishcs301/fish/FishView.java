package up.edu.heythatsmyfishcs301.fish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import up.edu.heythatsmyfishcs301.R;
import java.util.ArrayList;

/**
 *Descriptions: FishView Class contains all of the components that makes the 1-fish, 2-fish, 3-fish,
 * red-Penguin, and orange-Penguin. It is controlled by the Human Player and draws all of the
 * components such as the board,1-fish, 2-fish, 3-fish, red-Penguin, and orange-Penguin.
 * This class also assigns the hitboxes to the tile objects.
 *
 * @author Kyle Sanchez
 * @author Ryan Enslow
 * @author Carina Pineda
 * @author Linda Nguyen
 **/
public class FishView extends SurfaceView {

    //instance variables necessary to draw the initial board state
    private FishGameState gameState;
    private int cWidth;
    private int cHeight;
    private HexagonDrawable hex = new HexagonDrawable(0xFFC3F9FF);
    private HexagonDrawable bigHex = new HexagonDrawable(0xFF5685C5);
    private Rect tile;
    private Rect bigTile;
    Bitmap redPenguin = null;
    Bitmap resizedRedPenguin = null;

    Bitmap oneFish = null;
    Bitmap twoFish = null;
    Bitmap threeFish = null;

    Bitmap rOneFish = null;
    Bitmap rTwoFish = null;
    Bitmap rThreeFish = null;

    private int randTile;

    Bitmap orangePenguin = null;
    Bitmap resizedOrangePenguin = null;


    private Paint testPaint = new Paint();

    ArrayList<Integer> fishArray = new ArrayList<>(60);


    public FishView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        gameState = new FishGameState();

        /**
         *External Citation
         * Date: 9/18/20
         * Problem: Wanted the background of the surfaceview to be transparent instead of black
         * Resource: https://stackoverflow.com/questions/5391089/how-to-make-surfaceview-transparent
         *
         * Created by: Jens Jensen (4/14/2014)
         *
         * Solution: We copied these lines of code into our constructor to get rid of the black background
         */
        SurfaceView sfvTrack = (SurfaceView) findViewById(R.id.fishView);
        sfvTrack.setZOrderOnTop(true);    // necessary
        SurfaceHolder sfhTrackHolder = sfvTrack.getHolder();
        ((SurfaceHolder) sfhTrackHolder).setFormat(PixelFormat.TRANSPARENT);

        testPaint.setColor(Color.WHITE);
        oneFish = BitmapFactory.decodeResource(getResources(), R.drawable.onefishtile);
        twoFish = BitmapFactory.decodeResource(getResources(), R.drawable.twofishtile);
        threeFish = BitmapFactory.decodeResource(getResources(), R.drawable.threefishtile);
        rOneFish = Bitmap.createScaledBitmap(oneFish, 90, 90, false);
        rTwoFish = Bitmap.createScaledBitmap(twoFish, 90, 90, false);
        rThreeFish = Bitmap.createScaledBitmap(threeFish, 90, 90, false);

        redPenguin = BitmapFactory.decodeResource(getResources(), R.drawable.redpenguin);
        resizedRedPenguin = Bitmap.createScaledBitmap(redPenguin, 115, 115, false);
        orangePenguin = BitmapFactory.decodeResource(getResources(), R.drawable.orangepenguin);
        resizedOrangePenguin = Bitmap.createScaledBitmap(orangePenguin, 115, 115, false);
    }


    //This method is called by something idk what yet but it draws stuff to the screen
    @Override
    public void onDraw(Canvas canvas) {
        cWidth = canvas.getWidth();
        cHeight = canvas.getHeight();

        //setBackgroundColor(Color.WHITE);
        //drawHex(canvas);
        drawBoard(canvas, gameState);
    }


    /**This method uses the dimensions of the Canvas to draw a board of an appropriate size
    *Each hexagon can fit into a Rect object as its bounds so we take a rect and draw a hexagon
    *inside of it
    *we also draw any other properties of the tile that need to be drawn (Fish and penguins).
    *We repeat this process for all 8 rows and the end result is a complete board.**/
    public void drawBoard(Canvas c, FishGameState g) {
        FishTile[][] board = g.getBoardState();
        FishPenguin[][] penguins = g.getPieceArray();

        int hexHeight = cHeight / 8;
        int hexWidth = cWidth / 8;
        int margin = 15;

        //This Rect object is where we draw the hexagon. We will move it kind of like a stencil
        // and then draw the hexagon inside it
        Rect bound = new Rect(0, 0, hexWidth, hexHeight);
        int counter = 0;


       /** Here we go through the array and if the tile is null, then it is a placeholder and we
        * skip it.
        * If it is a tile that exists, we draw it.
        * Increment the Rect bound object
        * At the end of the row, reset bound to the start**/
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] == null) {
                    continue;
                }
                //This will draw the hexagon, fish, and then the penguins on each tile
                if (board[i][j].doesExist()) {

                    bigHex.computeHex(bound);
                    bigHex.draw(c);
                    Rect s = new Rect(bound);

                    s.top += margin;
                    s.bottom -= margin;
                    s.right -= margin;
                    s.left += margin;

                    //We set the hitbox for the Tile
                    board[i][j].setBoundingBox(s);
                    hex.computeHex(s);
                    hex.draw(c);

                    //draw the fish on the hexagon
                    switch (board[i][j].getNumFish()) {
                        case 1:
                            //Draw 1 fish
                            c.drawBitmap(rOneFish, board[i][j].getBoundingBox().left + 10, board[i][j].getBoundingBox().top + 15, null);
                            break;
                        case 2:
                            //Draw 2 fish
                            c.drawBitmap(rTwoFish, board[i][j].getBoundingBox().left + 10, board[i][j].getBoundingBox().top + 15, null);
                            break;
                        case 3:
                            //Draw 3 fish
                            c.drawBitmap(rThreeFish, board[i][j].getBoundingBox().left + 10, board[i][j].getBoundingBox().top + 15, null);
                            break;
                    }

                    //if it is player 0 then it belongs to the orange penguin, if the player is 1, then the red penguin is assigned to that player
                    if (board[i][j].getPenguin() != null){
                        if (board[i][j].getPenguin().getPlayer() == 0){
                            c.drawBitmap(resizedOrangePenguin, board[i][j].getBoundingBox().left, board[i][j].getBoundingBox().top, null);
                        }
                        else if(board[i][j].getPenguin().getPlayer() == 1){
                            c.drawBitmap(resizedRedPenguin, board[i][j].getBoundingBox().left, board[i][j].getBoundingBox().top, null);
                        }
                    }
                }

                //increment the bounds
                bound.right += hexWidth;
                bound.left += hexWidth;
            }

            //Need to set the bound back to the start and up one more line.
            //If the next line is even, set the left and right bounds to 0
            //If it's odd, offset the tile .
            float spacing = 0.75f;

            bound.left = ((i + 1) % 2) * hexWidth / 2;
            bound.right = ((i + 1) % 2) * hexWidth / 2 + hexWidth;
            bound.bottom += hexHeight * spacing;
            bound.top += hexHeight * spacing;
        }
    }

    public FishGameState getGameState() { return this.gameState; }
    public void setGameState(FishGameState f) { this.gameState = new FishGameState(f); }
}