package calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;

public class CalculatorSplashScreen {

    //set your name and student no. here
    //This will be displayed on splash screen
    String nameAndNumber = "name & student number";

    int duration; // duration of splash screen

    //The splash screen can be displayed at application startup, before the
    //Java Virtual Machine (JVM) starts. The splash screen is displayed as an
    // undecorated window containing an image.
    SplashScreen mySplash;

    //allows you to draw over the splash screen
    Graphics2D splashGraphics;

    //The Rectangle2D class describes a rectangle defined by a location (x,y) and dimension (w x h)
    Rectangle2D.Double splashTextArea; // Area for displaying Student name and Student number
    Rectangle2D.Double splashProgressArea; // Area for displaying progress Bar

    public CalculatorSplashScreen(int duration) {
        this.duration = duration;
        splashInit(); // initializes splash overlay drawing parameters
        appInit(duration); // this method takes the duration parameter and calls splashProgress() method to update progress bar
        if (mySplash != null) {
            mySplash.close();
        }
    }

    public void splashInit() {
        mySplash = SplashScreen.getSplashScreen();
        if (mySplash != null) {
            Dimension ssDim = mySplash.getSize();
            int height = ssDim.height;
            int width = ssDim.width;

            splashTextArea = new Rectangle2D.Double(15., height * 0.88, width * .45, 32.);
            splashProgressArea = new Rectangle2D.Double(width * .55, height * .92, width * .4, 12);

            // creates the Graphics environment for drawing splashTextArea and splashProgressArea
            splashGraphics = mySplash.createGraphics();
            Font font = new Font("Dialog", Font.PLAIN, 14);
            splashGraphics.setFont(font);

            //initially when splash screen is displayed the progress Bar's progress will be zero
            splashProgress(0);
        }
    }

    public void splashText(String nameAndNumber) {
        if (mySplash != null && mySplash.isVisible()) {
            splashGraphics.setPaint(Color.LIGHT_GRAY);
            splashGraphics.fill(splashTextArea);
            splashGraphics.setPaint(Color.BLACK);

            // Draw the Student name and number
            splashGraphics.drawString(nameAndNumber, (int) (splashTextArea.getX() + 10), (int) (splashTextArea.getY() + 15));
            // make sure its displayed
            mySplash.update();
        }
    }

    public void splashProgress(int pct) {
        if (mySplash != null && mySplash.isVisible()) {
            // erase the old one
            splashGraphics.setPaint(Color.LIGHT_GRAY);
            splashGraphics.fill(splashProgressArea);

            // draw an outline
            splashGraphics.setPaint(Color.BLUE);
            splashGraphics.draw(splashProgressArea);

            // Calculate the width corresponding to the correct percentage
            int x = (int) splashProgressArea.getMinX();
            int y = (int) splashProgressArea.getMinY();
            int wid = (int) splashProgressArea.getWidth();
            int hgt = (int) splashProgressArea.getHeight();
            int doneWidth = Math.round(pct * wid / 100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, wid - 1));

            // fill the done part one pixel smaller than the outline
            splashGraphics.setPaint(Color.GREEN);
            splashGraphics.fillRect(x, y + 1, doneWidth, hgt - 1);

            // make sure it's displayed
            mySplash.update();
        }
    }

    public void appInit(int duration) {
        splashText(nameAndNumber);
        for (int i = 0; i <= duration; i++) {
            int pctDone = (i) * 25; //pct = percentage
            splashProgress(pctDone);
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
