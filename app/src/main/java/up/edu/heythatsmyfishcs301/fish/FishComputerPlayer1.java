package up.edu.heythatsmyfishcs301.fish;

import android.util.Log;

import up.edu.heythatsmyfishcs301.game.GameComputerPlayer;
import up.edu.heythatsmyfishcs301.game.infoMsg.GameInfo;
import up.edu.heythatsmyfishcs301.game.infoMsg.NotYourTurnInfo;


/**
 * @author Kyle Sanchez
 * @author Ryan Enslow
 * @author Carina Pineda
 * @author Linda Nguyen
 *
 * This class contains the algorithm for how the computer player moves. It implements
 * a separate method for the computer to move and sends the move action to the gameState
 **/
public class FishComputerPlayer1 extends GameComputerPlayer {

    private FishTile[][] boardState;
    FishGameState copy = null;

    //Constructor for The Computer Player
    public FishComputerPlayer1(String name){
        super(name);
    }

    //Computer Player 1 sends a random action to the game state.
    @Override
    protected void receiveInfo(GameInfo info) {
        // if it was a "not your turn" message, just ignore it
        if (info instanceof NotYourTurnInfo){
            return;
        }

        //if the info is not of a FishGameState, ignore because it will cause problems otherwise.
        if (!(info instanceof FishGameState)) {
            return;
        }

        //Let copy be the copied state.
        copy = (FishGameState) info;

        if (copy.getPlayerTurn() != this.playerNum)
            return;

        //If the game phase is mid-game (Moving penguins)
        //for alpha release set to 0, change to 1 later
        if (copy.getGamePhase() == 0){
            boardState = copy.getBoardState();

            // using our copy of gamestate
            FishTile[][] pieceBoard = copy.getBoardState();

            // loop through the board to see there is a penguin on the tile. If there is, it checks if the
            //penguin belongs to the computer. If it does, it calls the computerMovePenguin
            if(copy.getPlayerTurn() == 1){
                for(int i =0; i < pieceBoard.length; i++){
                    for(int j=0; j< pieceBoard[i].length;j++){
                        //computer player hard coded to play2 for alpha release
                        if(pieceBoard[i][j] != null){
                            if(pieceBoard[i][j].hasPenguin() && pieceBoard[i][j].getPenguin().getPlayer() == this.playerNum){
                                computerMovePenguin(pieceBoard[i][j].getPenguin());
                                return;
                            }
                        }
                    }

                }
            }
            // not sure if needed
            Log.d("Move","Computer Player Moving");
        }
        //If the game phase is set up (Placing Penguins)
        else{
            //FishPlaceAction placeAction = new FishPlaceAction(this);
            //this.game.sendAction(placeAction);
        }
    }


    /**
     * This AI is not very smart. It starts by trying to move to the right horizontally. From there, if moving to the right is not valid, it will check
     * each direction going clockwise if moving there is a valid move. At the first instance of a valid move, it will make that move. It checks if a direction is
     * a valid move by making sure that the tile isn't null, the if the coordinate we're going to is within the array bounds, if the tile exists, and the tile
     * doesn't already have a penguin on it. To get access to the adjacent tiles, we just add or subtract to the x and y coordinates
     */
    public boolean computerMovePenguin(FishPenguin p) {
        FishTile[][] pieceBoard = copy.getBoardState();


        //If the move is legal, then add to the player's score the fish on the tile and remove the tile from the game. Then pass the turn.
        if (copy.getPlayerTurn() == 1) {

        //try to move horizontally to the right
        if (p.getY() + 1 <= 8 && (pieceBoard[p.getX()][p.getY() + 1] != null) ) {
            if(!(pieceBoard[p.getX()][p.getY() + 1].hasPenguin()) && (pieceBoard[p.getX()][p.getY() + 1].doesExist())) {
                addScore(copy.getPlayerTurn(), this.boardState[p.getX()][p.getY()].getNumFish());
                this.boardState[p.getX()][p.getY()].setExists(false);
                this.boardState[p.getX()][p.getY() + 1].setPenguin(p);
                p.setXPos(p.getX());
                p.setYPos(p.getY() + 1);


                FishPenguin selectedPenguin = this.boardState[p.getX()][p.getY()].getPenguin();
                //FishComputerMoveAction m = new FishComputerMoveAction(this, selectedPenguin, this.boardState[p.getX()][p.getY()], copy.getPlayer2Score());
                FishMoveAction m = new FishMoveAction(this, selectedPenguin, this.boardState[p.getX()][p.getY()]);
                Log.d("Move", "Computer Player Moving horizontally to the right");
                Log.d("Computer Moved", "Computer moved to (" + p.getX() + "," + p.getY() + ")");
                game.sendAction(m);
                return true;
            }
        }


        //try to move diagonally down to the right
            if(p.getX() + 1 < 8 && pieceBoard[p.getX() + 1][p.getY()] != null){
                if (!(pieceBoard[p.getX() + 1][p.getY()].hasPenguin()) && (pieceBoard[p.getX() + 1][p.getY()].doesExist())) {
                    addScore(copy.getPlayerTurn(), this.boardState[p.getX()][p.getY()].getNumFish());
                    this.boardState[p.getX()][p.getY()].setExists(false);
                    this.boardState[p.getX() + 1][p.getY()].setPenguin(p);
                    p.setXPos(p.getX() + 1);
                    p.setYPos(p.getY());

                    FishPenguin selectedPenguin = this.boardState[p.getX()][p.getY()].getPenguin();
                    //FishComputerMoveAction m = new FishComputerMoveAction(this, selectedPenguin,this.boardState[p.getX()][p.getY()], copy.getPlayer2Score());
                    FishMoveAction m = new FishMoveAction(this, selectedPenguin, this.boardState[p.getX()][p.getY()]);
                    Log.d("Move","Computer Player Moving diagonally down to the right");
                    Log.d("Computer Moved", "Computer moved to (" + p.getX() + "," + p.getY() + ")");
                    game.sendAction(m);

                    return true;
                }
            }


            //try to move diagonally down to the left
            if(p.getX() + 1 < 8 && p.getY() - 1 >= 0 && pieceBoard[p.getX() + 1][p.getY() - 1] != null){
                if (!(pieceBoard[p.getX() + 1][p.getY() - 1].hasPenguin()) && (pieceBoard[p.getX() + 1][p.getY() - 1].doesExist())) {
                    addScore(copy.getPlayerTurn(), this.boardState[p.getX()][p.getY()].getNumFish());
                    this.boardState[p.getX()][p.getY()].setExists(false);
                    this.boardState[p.getX() + 1][p.getY() - 1].setPenguin(p);
                    p.setXPos(p.getX() + 1);
                    p.setYPos(p.getY() - 1);

                    FishPenguin selectedPenguin = this.boardState[p.getX()][p.getY()].getPenguin();
                    //FishComputerMoveAction m = new FishComputerMoveAction(this, selectedPenguin,this.boardState[p.getX()][p.getY()], copy.getPlayer2Score());
                    FishMoveAction m = new FishMoveAction(this, selectedPenguin, this.boardState[p.getX()][p.getY()]);
                    Log.d("Move","Computer Player Moving diagonally down to the left");
                    Log.d("Computer Moved", "Computer moved to (" + p.getX() + "," + p.getY() + ")");
                    game.sendAction(m);
                    return true;
                }
            }


        //try to move horizontally to the left
            if(p.getY() - 1 >= 0 && pieceBoard[p.getX()][p.getY() - 1] != null){
                if (!(pieceBoard[p.getX()][p.getY() - 1].hasPenguin()) && (pieceBoard[p.getX()][p.getY() - 1].doesExist())) {
                    addScore(copy.getPlayerTurn(), this.boardState[p.getX()][p.getY()].getNumFish());
                    this.boardState[p.getX()][p.getY()].setExists(false);
                    this.boardState[p.getX()][p.getY() - 1].setPenguin(p);
                    p.setXPos(p.getX());
                    p.setYPos(p.getY() - 1);

                    FishPenguin selectedPenguin = this.boardState[p.getX()][p.getY()].getPenguin();
                    //FishComputerMoveAction m = new FishComputerMoveAction(this, selectedPenguin,this.boardState[p.getX()][p.getY()], copy.getPlayer2Score());
                    FishMoveAction m = new FishMoveAction(this, selectedPenguin, this.boardState[p.getX()][p.getY()]);
                    Log.d("Move","Computer Player Moving horizontally to the left");
                    Log.d("Computer Moved", "Computer moved to (" + p.getX() + "," + p.getY() + ")");
                    game.sendAction(m);
                    return true;
                }
            }


        //try to move diagonally up to the left
            if(p.getX() - 1 >= 0 && pieceBoard[p.getX() - 1][p.getY()] != null){
                if (!(pieceBoard[p.getX() - 1][p.getY()].hasPenguin()) && (pieceBoard[p.getX() - 1][p.getY()].doesExist())) {
                    addScore(copy.getPlayerTurn(), this.boardState[p.getX()][p.getY()].getNumFish());
                    this.boardState[p.getX()][p.getY()].setExists(false);
                    this.boardState[p.getX() - 1][p.getY()].setPenguin(p);
                    p.setXPos(p.getX() - 1);
                    p.setYPos(p.getY());

                    FishPenguin selectedPenguin = this.boardState[p.getX()][p.getY()].getPenguin();
                    //FishComputerMoveAction m = new FishComputerMoveAction(this, selectedPenguin,this.boardState[p.getX()][p.getY()], copy.getPlayer2Score());
                    FishMoveAction m = new FishMoveAction(this, selectedPenguin, this.boardState[p.getX()][p.getY()]);
                    Log.d("Move","Computer Player Moving diagonally up to the left");
                    Log.d("Computer Moved", "Computer moved to (" + p.getX() + "," + p.getY() + ")");
                    game.sendAction(m);
                    return true;
                }
            }


        //try to move diagonally up to the right
            if(p.getX() - 1 >= 0 && p.getY() + 1 <= 8 && pieceBoard[p.getX() - 1][p.getY() + 1] != null){
                if (!(pieceBoard[p.getX() - 1][p.getY() + 1].hasPenguin()) && (pieceBoard[p.getX() - 1][p.getY() + 1].doesExist())) {
                    addScore(copy.getPlayerTurn(), this.boardState[p.getX()][p.getY()].getNumFish());
                    this.boardState[p.getX()][p.getY()].setExists(false);
                    this.boardState[p.getX() - 1][p.getY() + 1].setPenguin(p);
                    p.setXPos(p.getX() - 1);
                    p.setYPos(p.getY() + 1);

                    FishPenguin selectedPenguin = this.boardState[p.getX()][p.getY()].getPenguin();
                    //FishComputerMoveAction m = new FishComputerMoveAction(this, selectedPenguin,this.boardState[p.getX()][p.getY()], copy.getPlayer2Score());
                    FishMoveAction m = new FishMoveAction(this, selectedPenguin, this.boardState[p.getX()][p.getY()]);
                    Log.d("Move","Computer Player Moving diagonally up to the right");
                    Log.d("Computer Moved", "Computer moved to (" + p.getX() + "," + p.getY() + ")");
                    game.sendAction(m);
                    return true;
                }
            }
    }
        return false;
    }

    //method to sum up the scores of the scores of the computer player. It only sends it to a copy of the gamestate
    //so a different method outside of this class is needed to send the scores to the actual gamestate
    private void addScore(int pT, int s) {
        switch (pT) {
            case 0:
                copy.setPlayer1Score(copy.getPlayer1Score() + s);
                break;
            case 1:
                copy.setPlayer2Score(copy.getPlayer2Score() + s);
                break;
            case 2:
                copy.setPlayer3Score(copy.getPlayer3Score() + s);
                break;
            case 3:
                copy.setPlayer4Score(copy.getPlayer4Score() + s);
                break;
        }
    }

}