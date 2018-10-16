package calculator;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Calculator extends JFrame {

    /**
     * @param args the command line arguments
     */
    public Calculator() {
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

//import java.awt.BorderLayout;
//import java.awt.FlowLayout;
//import java.awt.GridLayout;
///*from  ww w. j a v  a2  s .co  m*/
//import javax.swing.ButtonGroup;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JRadioButton;
//
//public class Calculator {
//  public static void main(String[] args) {
//    JFrame frame = new JFrame();
//    frame.setLayout(new BorderLayout());
//    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//    JPanel northPanel = new JPanel(new GridLayout(2, 1));
//
//    JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//    welcomePanel.add(new JLabel("Welcome"));
//
//    northPanel.add(welcomePanel);
//
//    JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//
//    JRadioButton button1 = new JRadioButton("Button 1", true);
//    JRadioButton button2 = new JRadioButton("Button 2", false);
//
//    ButtonGroup group = new ButtonGroup();
//    group.add(button1);
//    group.add(button2);
//
//    radioPanel.add(button1);
//    radioPanel.add(button2);
//
//    northPanel.add(radioPanel);
//
//    JPanel middlePanel = new JPanel(new GridLayout(3, 3));
//
//    for (int i = 0; i < 3; i++) {
//      for (int j = 0; j < 3; j++) {
//        middlePanel.add(new JButton("Button " + i + j));
//      }
//    }
//
//    JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//
//    southPanel.add(new JLabel("Whose turn:"));
//    southPanel.add(new JButton("Reset"));
//
//    frame.add(northPanel, BorderLayout.NORTH);
//    frame.add(middlePanel, BorderLayout.CENTER);
//    frame.add(southPanel, BorderLayout.SOUTH);
//
//    frame.pack();
//    frame.setVisible(true);
//  }
//}
