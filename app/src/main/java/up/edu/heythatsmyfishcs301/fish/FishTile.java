package up.edu.heythatsmyfishcs301.fish;

import android.graphics.Rect;

/**
 * @author Kyle Sanchez
 * @author Ryan Enslow
 * @author Carina Pineda
 * @author Linda Nguyen
 **/

public class FishTile {

    private boolean exists;         //True if the tile is still on the board
    private boolean hasPenguin;     //True if the tile contains a penguin
    private FishPenguin penguin;    //Contains the penguin object currently on this tile
    private int x;                  //Contains the x coordinate in the array
    private int y;                  //Contains the y coordinate in the array
    private Rect boundingBox;       //Contains the hit box of the tile
    private int numFish;            //Contains the number of points on the tile

    /**
     * This is the constructor that will be called pretty much every time
     *
     * @param x: x location in the 2d array
     * @param y: y location in the 2d array
     */
    public FishTile(int x, int y){
        this.x = x;
        this.y = y;
        this.exists = true;
        this.hasPenguin = false;
        this.penguin = null;
        this.boundingBox = null;
    }

    /**
     * Copy Constructor: Creates a deep copy
     * @param t: Tile to be copied
     */
    public FishTile(FishTile t){
        this.x = t.x;
        this.y = t.y;
        this.exists = t.exists;
        this.hasPenguin = t.hasPenguin;
        this.penguin = new FishPenguin(t.penguin);
        this.boundingBox = new Rect(t.boundingBox);
    }

    /**
     * GETTER METHODS
     */
    public int getX(){
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean doesExist(){
        return this.exists;
    }

    public boolean hasPenguin(){
        return hasPenguin;
    }

    public FishPenguin getPenguin(){
        return this.penguin;
    }

    public Rect getBoundingBox(){
        return this.boundingBox;
    }

    public int getNumFish(){
        return this.numFish;
    }


    /**
     * SETTER METHODS
     */
    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setExists(boolean b){
        this.exists = b;
    }

    public void setHasPenguin(boolean b){
        this.hasPenguin = b;
    }

    public void setPenguin(FishPenguin p){
        this.penguin = (p);
    }

    public void setBoundingBox(Rect r){
        this.boundingBox = new Rect(r);
    }

    public void setNumFish(int i){
        this.numFish = i;
    }


} //FishTile class
