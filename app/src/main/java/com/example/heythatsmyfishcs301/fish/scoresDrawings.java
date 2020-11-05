package com.example.heythatsmyfishcs301.fish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.heythatsmyfishcs301.R;

public class scoresDrawings extends SurfaceView {

    Bitmap orangeFish = null;
    Bitmap redFish = null;
    Bitmap blueFish = null;
    Bitmap resizedOrange = null;
    Bitmap resizedRed = null;
    Bitmap resizedBlue = null;
    int numOrangeFish = 12;
    int numRedFish = 23;
    private Paint black = new Paint();

    public scoresDrawings(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        black.setColor(Color.BLACK);
        black.setTextSize(50);

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
        SurfaceView sfvTrack = (SurfaceView)findViewById(R.id.scoresDrawings);
        sfvTrack.setZOrderOnTop(true);
        SurfaceHolder sfhTrackHolder = sfvTrack.getHolder();
        ((SurfaceHolder) sfhTrackHolder).setFormat(PixelFormat.TRANSPARENT);

        /**
         *External Citation
         * Date: 9/18/20
         * Problem: Needed to draw the fish png files to the surfaceview
         * Resource: Class lecture for creating the bitmap, and also https://stackoverflow.com/questions/17839388/creating-a-scaled-bitmap-with-createscaledbitmap-in-android
         * to scale the images
         * Created by: Dr. Andrew Nuxoll (9/17/2020) and Geobits (7/24/2013)
         *
         * Solution: We referenced the examples from lecture and the stackoverflow tutorial to create a bitmap of our image and then resize it to fit the surfaceview
         */
        orangeFish = BitmapFactory.decodeResource(getResources(), R.drawable.orangefish);
        resizedOrange = Bitmap.createScaledBitmap(orangeFish, 300, 300, false);

        redFish = BitmapFactory.decodeResource(getResources(), R.drawable.redfish);
        resizedRed = Bitmap.createScaledBitmap(redFish, 300, 300, false);

        blueFish = BitmapFactory.decodeResource(getResources(), R.drawable.bluefish);
        resizedBlue = Bitmap.createScaledBitmap(blueFish, 300, 300, false);



    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.resizedOrange, 1.0f, 1.0f, null);
        canvas.drawBitmap(this.resizedRed, 0.0f, 300.0f, null);

        /**
         *External Citation
         * Date: 9/18/20
         * Problem: Forgot how to turn an int into a string
         * Resource: https://www.geeksforgeeks.org/different-ways-for-integer-to-string-conversions-in-java/
         * Created by: Geeksforgeeks (12/08/2020)
         *
         * Solution: Used the Integer.toString method shown on the website
         */
        canvas.drawText(Integer.toString(numOrangeFish), (float)140.0, (float)160.0, black);
        canvas.drawText(Integer.toString(numRedFish), (float)140.0, (float)465.0, black);
    }

}