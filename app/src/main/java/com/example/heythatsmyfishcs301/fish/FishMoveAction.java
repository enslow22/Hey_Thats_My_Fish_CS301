package com.example.heythatsmyfishcs301.fish;

import android.util.Log;

import com.example.heythatsmyfishcs301.game.GamePlayer;
import com.example.heythatsmyfishcs301.game.actionMsg.GameAction;

import static java.lang.Math.abs;

/**
 * @author Kyle Sanchez
 * @author Ryan Enslow
 * @author Carina Pineda
 * @author Linda Nguyen
 **/
public class FishMoveAction extends GameAction {

    //Instance Variables for a move action:
    private FishPenguin penguin;
    private FishTile destination;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     * @param penguin the penguin that is going to be moved
     * @param tile the final destination for the penguin
     */
    public FishMoveAction(GamePlayer player, FishPenguin penguin, FishTile tile) {
        super(player);
        this.penguin = penguin;
        this.destination = tile;
    }

    public FishPenguin getPenguin(){
        return this.penguin;
    }

    public FishTile getDestination(){
        return this.destination;
    }
}
