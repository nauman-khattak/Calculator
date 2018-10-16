package calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;

public class CalculatorSplashScreen {

    String nameAndNumber = "name & student number";

    int duration;
    SplashScreen mySplash;
    Graphics2D splashGraphics;
    Rectangle2D.Double splashTextArea;
    Rectangle2D.Double splashProgressArea;

    public CalculatorSplashScreen(int duration) {
        this.duration = duration;
        splashInit();
        appInit(duration);
        if (mySplash != null) {
            mySplash.close();
        }
    }

    public void splashInit() {
        mySplash = SplashScreen.getSplashScreen();
        System.out.println(mySplash == null);
        if (mySplash != null) {
            Dimension ssDim = mySplash.getSize();
            int height = ssDim.height;
            int width = ssDim.width;
            
            splashTextArea = new Rectangle2D.Double(15., height * 0.88, width * .45, 32.);
            splashProgressArea = new Rectangle2D.Double(width * .55, height * .92, width * .4, 12);

            splashGraphics = mySplash.createGraphics();
            Font font = new Font("Dialog", Font.PLAIN, 14);
            splashGraphics.setFont(font);
            splashProgress(0);
        }
    }

    public void splashText(String str) {
        if (mySplash != null && mySplash.isVisible()) {
            splashGraphics.setPaint(Color.LIGHT_GRAY);
            splashGraphics.fill(splashTextArea);
            splashGraphics.setPaint(Color.BLACK);
            splashGraphics.drawString(str, (int) (splashTextArea.getX() + 10), (int) (splashTextArea.getY() + 15));
            mySplash.update();
        }
    }

    public void splashProgress(int pct) {
        if (mySplash != null && mySplash.isVisible()) {
            splashGraphics.setPaint(Color.LIGHT_GRAY);
            splashGraphics.fill(splashProgressArea);

            splashGraphics.setPaint(Color.BLUE);
            splashGraphics.draw(splashProgressArea);

            int x = (int) splashProgressArea.getMinX();
            int y = (int) splashProgressArea.getMinY();
            int wid = (int) splashProgressArea.getWidth();
            int hgt = (int) splashProgressArea.getHeight();

            int doneWidth = Math.round(pct * wid / 100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, wid - 1));
            splashGraphics.setPaint(Color.GREEN);
            splashGraphics.fillRect(x, y + 1, doneWidth, hgt - 1);
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
