package up.edu.heythatsmyfishcs301;

import org.junit.Test;

import up.edu.heythatsmyfishcs301.fish.FishGameState;
import up.edu.heythatsmyfishcs301.fish.FishPenguin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FishGameStateUnitTest {

    @Test
    public void setGamePhase(){
        FishGameState f = new FishGameState(1);
        f.setGamePhase(2);
        assertEquals(f.getGamePhase(),2);
    }

    @Test public void addScore(){
        FishGameState f = new FishGameState(1);
        f.setPlayer1Score(60);
        f.setPlayerTurn(0);
        //f.addScore(f.getPlayerTurn(),44);
        assertEquals(f.getPlayer1Score(),60);
    }

    @Test
    public void setPlayer1Score(){
        FishGameState f = new FishGameState(1);
        f.setPlayer1Score(12);
        assertEquals(f.getPlayer1Score(), 12);
    }

    @Test
    public void setPlayer2Score(){
        FishGameState f = new FishGameState(1);
        f.setPlayer1Score(10);
        assertEquals(f.getPlayer1Score(), 10);
    }

    @Test
    public void getPlayer1SCore(){
        FishGameState f = new FishGameState(1);
        f.setPlayer1Score(10);
        int score = f.getPlayer1Score();
        assertEquals(score, 10);
    }

    @Test
    public void reachable(){
        FishGameState f = new FishGameState(4);
        f.setGamePhase(1);
        FishPenguin p = new FishPenguin(0);
        p.setOnBoard(true);
        p.setXPos(5);
        p.setYPos(5);

        assertTrue(f.reachable(p,f.getBoardState()[6][4]));
        assertTrue(f.reachable(p,f.getBoardState()[4][5]));
        assertTrue(f.reachable(p,f.getBoardState()[5][4]));
    }
}
