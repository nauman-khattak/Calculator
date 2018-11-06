package calculator;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Calculator extends JFrame {

    public Calculator() {

        //instantiating CalculatorSplashScreen with time duration argument of 4 secs
        new CalculatorSplashScreen(4);

        //instantiating CalculatorViewController
        CalculatorViewController calculatorViewController = new CalculatorViewController();

        //settings properties of Frame and adding calculatorViewController Panel to it.
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
