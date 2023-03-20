package up.edu.heythatsmyfishcs301;

import org.junit.Test;

import up.edu.heythatsmyfishcs301.fish.FishPenguin;
import up.edu.heythatsmyfishcs301.fish.FishTile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.graphics.Rect;

public class FishTileUnitTest {

    @Test
    public void FishTileLocation(){
        FishTile fishTile = new FishTile(4,4);
        int fishTileXLoc = fishTile.getX();
        int fishTileYLoc = fishTile.getY();
        // Assert xLoc is 4
        assertEquals(4,fishTileXLoc);
        // Assert yLoc is 4
        assertEquals(4,fishTileYLoc);
    }

    @Test
    public void FishTileExists(){
        FishTile fishTile = new FishTile(4,4);
        boolean doesExist = fishTile.doesExist();

        // Should be true after location is created
        assertTrue(doesExist);
    }

    @Test
    public void FishTileNoPenguin(){
        FishTile fishTile = new FishTile(4,4);
        boolean noPenguin = fishTile.hasPenguin();

        // Assert tile doesn't have a penguin by default
        assertEquals(false,noPenguin);
    }

    @Test
    public void FishTileHasPenguin(){
        FishTile fishTile = new FishTile(4,4);
        // Create new penguin owned by player 1
        FishPenguin penguin = new FishPenguin(1);
        // Set the tile with the penguin and update boolean
        fishTile.setPenguin(penguin);
        fishTile.setHasPenguin(true);

        boolean hasPenguin = fishTile.hasPenguin();

        // Assert tile does have a penguin after setting one there
        assertEquals(true,hasPenguin);
    }

    @Test
    public void FishSetNumFish(){
        FishTile fishTile = new FishTile(4,4);
        // Set number of fish for this tile to be 3
        fishTile.setNumFish(3);
        int numFish = fishTile.getNumFish();

        // Assert that tile should have 3 fish after setting it
        assertEquals(3,numFish);

    }

    @Test
    public void FishTileHitBox(){
        FishTile fishTile = new FishTile(4,4);
        Rect rect = new Rect();
        fishTile.setBoundingBox(rect);
        Rect tileHitBox = fishTile.getBoundingBox();

        // Assert that the new tile we are creating does have a hit box and isn't a null hit box
        assertNotNull(tileHitBox);

    }
}
