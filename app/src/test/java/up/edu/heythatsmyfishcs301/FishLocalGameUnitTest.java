package up.edu.heythatsmyfishcs301;


import org.junit.Assert;
import org.junit.Test;

import up.edu.heythatsmyfishcs301.fish.FishGameState;
import up.edu.heythatsmyfishcs301.fish.FishLocalGame;

import static org.junit.Assert.assertEquals;

public class FishLocalGameUnitTest {

    @Test
    public void FishLocalGame(){
        // Minimum number of players for this game is 2
        FishGameState gameState = new FishGameState(2);

        int firstPlayerTurn = gameState.getPlayerTurn();

        // Player 0 represent the first player in a 2 player game
        assertEquals(0,firstPlayerTurn);

        // Change turn after move is made in localGame
        gameState.changeTurn();

        int secondPlayerTurn = gameState.getPlayerTurn();
        assertEquals(1,secondPlayerTurn);

    }
}
