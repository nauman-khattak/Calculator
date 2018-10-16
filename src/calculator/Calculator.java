package calculator;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Calculator extends JFrame {

    /**
     * @param args the command line arguments
     */
    public Calculator() {
        new CalculatorSplashScreen(4);
        CalculatorViewController calculatorViewController = new CalculatorViewController();
        this.setUndecorated(false);
        this.add(calculatorViewController);
        this.setMinimumSize(new Dimension(380, 520));
        this.setTitle("Calculator");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}