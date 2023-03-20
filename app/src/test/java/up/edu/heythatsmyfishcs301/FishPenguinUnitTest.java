package up.edu.heythatsmyfishcs301;

import org.junit.Test;

import up.edu.heythatsmyfishcs301.fish.FishPenguin;

import static org.junit.Assert.assertEquals;


public class FishPenguinUnitTest {

    @Test
    public void FishPenguin(){
        // When a new FishPenguin obj is created it has a player #, onBoard boolean and selected int
        // associated with it

        // Initialize new penguin belonging to player 1
        FishPenguin fishPenguin = new FishPenguin(1);

        boolean onBoard = fishPenguin.isOnBoard();
        int isSelected = fishPenguin.getSelected();

        // False by default (hasn't been placed yet)
        assertEquals(false,onBoard);

        // 0 by default hasn't been selected to be placed yet
        assertEquals(0,isSelected);

    }

    @Test
    public void FishPenguinOnBoard(){
        // Initialize new penguin belonging to player 1
        FishPenguin fishPenguin = new FishPenguin(1);

        // Mocking that a penguin is placed on the board
        fishPenguin.setOnBoard(true);
        boolean onBoard = fishPenguin.isOnBoard();

        // Assert true after penguin is "placed" on board
        assertEquals(true,onBoard);
    }

    @Test
    public void FishPenguinSelected(){
        // Initialize new penguin belonging to player 1
        FishPenguin fishPenguin = new FishPenguin(1);

        fishPenguin.setSelected(1);
        int isSelected = fishPenguin.getSelected();

        // Assert to 1  after penguin is "selected" by a player
        assertEquals(1,isSelected);

    }

}
