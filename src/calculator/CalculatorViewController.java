package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorViewController extends JPanel {

    private JTextField display1;
    private JTextField display2; //initial value of display2 is 0.0
    private JLabel mode_error_label; //intital value of this label is F
    private JButton dotButton;
    ButtonGroup buttonGroup;
    String[] keypadText; //Array for holding text displayed on keypad buttons 
    String[] actionCommandText; //Array for holding action commands of keypad buttons
    String result;
    boolean resultDisplayed = false;
    boolean operatorAdded = false;
    boolean backspaceDisabled = false; //this will check if backspace button is disabled or enabled
    boolean errorCheck = false; //a helper to check if error occured or not. If error occured then keypad buttons and equal button will not response to click events.
    boolean clearPressed = false; //a helper to check if its time to save display1 text in a String display1Text
    String display1Text = ""; //this will store display1 text when result is displayed on display2

    //Controller class is handling all all events generated due to interation with GUI.
    //Controller class is private inner class of CalculatorViewController and it implements ActionListener Interface
    //instantiating Controller class
    Controller controller = new Controller();
    CalculatorModel calculatorModel = new CalculatorModel(this);

    public CalculatorViewController() {

        //setting layout of CalculatorViewController panel to BorderLayout
        this.setLayout(new BorderLayout());
        //setting border of CalculatorViewController panel to MatteBorder with insets 5,5,5,5 and BLACK color
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));

        //Mode/error label with initial value set to F
        //Can be seen at the top left corner of the application
        mode_error_label = new JLabel("F", JLabel.CENTER);
        mode_error_label.setPreferredSize(new Dimension(46, 55));
        mode_error_label.setBackground(Color.YELLOW);
        mode_error_label.setOpaque(true);
        mode_error_label.setFont(new Font(mode_error_label.getFont().getName(), Font.BOLD, mode_error_label.getFont().getSize() + 10));
        mode_error_label.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 5, Color.BLACK));

        //Backspace button and can be seen at the top right corner of the application
        //Can respond to Alt+B key combination
        JButton backButton = new JButton(Character.toString('\u21DA'));
        backButton.setPreferredSize(new Dimension(45, 55));
        backButton.setBackground(Color.YELLOW);
        backButton.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 1, Color.BLACK));
        backButton.setFont(new Font(backButton.getFont().getName(), Font.BOLD, backButton.getFont().getSize() + 10));
        backButton.setToolTipText("Backspace (Alt-B)");
        backButton.setActionCommand("backspace");
        backButton.addActionListener(controller);
        backButton.setMnemonic('B');

        display1 = new JTextField(16);
        display1.setEditable(false);
        display1.setHorizontalAlignment(JTextField.RIGHT);
        display1.setBackground(Color.WHITE);
        display1.setBorder(BorderFactory.createEmptyBorder());

        display2 = new JTextField(16);
        display2.setEditable(false);
        display2.setHorizontalAlignment(JTextField.RIGHT);
        display2.setBackground(Color.WHITE);
        display2.setBorder(BorderFactory.createEmptyBorder());
        display2.setText("0.0");
        //Box for holding display1 & display2.
        //This is vertical Box so components are placed on top of each other.
        //display1 is added first so its above display2.
        //display2 is below display1
        Box displayBox = Box.createVerticalBox();
        displayBox.add(display1);
        displayBox.add(display2);

        //mode/error label, displayBox & backButton are added to Panel named upperPanel
        //They are placed in positions WEST, CENTRE & EAST so that they behave like they are on a single horizontal line
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.add(mode_error_label, BorderLayout.WEST);
        upperPanel.add(displayBox, BorderLayout.CENTER);
        upperPanel.add(backButton, BorderLayout.EAST);
        upperPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.black));

        //Checkbox for setting mode to Int
        JCheckBox modeCheckBox = new JCheckBox("Int");
        modeCheckBox.setPreferredSize(new Dimension(40, 0));
        modeCheckBox.setBackground(Color.green);
        modeCheckBox.setActionCommand("checkbox");
        modeCheckBox.addActionListener(controller);

        // .0 radio button
        JRadioButton _0RadioButton = new JRadioButton(".0", false);
        _0RadioButton.setBackground(Color.YELLOW);
        _0RadioButton.setActionCommand(".0");
        _0RadioButton.addActionListener(controller);

        // .00 radio button
        JRadioButton _00RadioButton = new JRadioButton(".00", true);
        _00RadioButton.setBackground(Color.YELLOW);
        _00RadioButton.setActionCommand(".00");
        _00RadioButton.addActionListener(controller);

        // sci radio button
        JRadioButton sciRadioButton = new JRadioButton("Sci", false);
        sciRadioButton.setBackground(Color.YELLOW);
        sciRadioButton.setActionCommand("sci");
        sciRadioButton.addActionListener(controller);

        // .0, .00 & sci radio buttons are added to a button group
        // if a radio button is enabled the button group will disable others
        // only one button is allowed to be enabled at a time inside the group
        buttonGroup = new ButtonGroup();
        buttonGroup.add(_0RadioButton);
        buttonGroup.add(_00RadioButton);
        buttonGroup.add(sciRadioButton);
        buttonGroup.add(modeCheckBox);

        // Horizontal box for holding error/mode checkbox and all three radio buttons.
        Box lowerBox = Box.createHorizontalBox();
        lowerBox.add(modeCheckBox);
        lowerBox.add(Box.createGlue());
        lowerBox.add(_0RadioButton);
        lowerBox.add(_00RadioButton);
        lowerBox.add(sciRadioButton);
        lowerBox.setBackground(Color.BLACK);
        lowerBox.setOpaque(true);
        lowerBox.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, Color.black));

        // Panel for holding Box named lowerBox
        // Panel layout is FlowLayout
        // FlowLayout is default layout of JPanel unless set otherwise
        JPanel lowerPanel = new JPanel();
        lowerPanel.add(lowerBox);
        lowerPanel.setBackground(Color.BLACK);
        lowerPanel.setOpaque(true);
        lowerPanel.setBorder(BorderFactory.createEmptyBorder());

        // Vertical Box for holding upperPanel and lowerPanel
        Box superBox = Box.createVerticalBox();
        superBox.add(upperPanel);
        superBox.add(lowerBox);

        // Adding box to the north region of CalculatorViewController panel
        this.add(superBox, BorderLayout.NORTH);

        // keypad panel with GridLayout
        // parameters of this GridLayout are (rows, columns, horizontal gap, vertical gap)
        JPanel keyPadPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        keyPadPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Array for holding text to be displayed on keypad buttons
        keypadText = new String[]{"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", Character.toString('\u00B1'), "+"};

        // Array for holding action command of keypad buttons
        actionCommandText = new String[]{"seven", "eight", "nine", "divide", "four", "five", "six", "multiply", "one", "two", "three", "minus", "zero", "dot", "negate", "add"};

        //loop for creating keypad buttons
        //the createButton() method creates the button, sets its text, action command, foreground color, background color and adds actionListener to the button
        //the method then returns the button
        for (int i = 0; i < keypadText.length; i++) {
            if (keypadText[i].matches("[0-9]")) {
                keyPadPanel.add(createButton(keypadText[i], actionCommandText[i], Color.BLACK, Color.BLUE, controller));
            }
            if (".".equals(keypadText[i])) {
                dotButton = createButton(keypadText[i], actionCommandText[i], Color.BLACK, Color.BLUE, controller);
                keyPadPanel.add(dotButton);
            }
            if (keypadText[i].matches("[-+*/]")) {
                keyPadPanel.add(createButton(keypadText[i], actionCommandText[i], Color.BLACK, Color.CYAN, controller));
            }
            if (Character.toString('\u00B1').equals(keypadText[i])) {
                keyPadPanel.add(createButton(keypadText[i], actionCommandText[i], Color.BLACK, Color.PINK, controller));
            }

        } // end of for loop

        // creating equal buttons
        JButton equalsBtn1 = new JButton("=");
        JButton equalsBtn2 = new JButton("=");
        equalsBtn1.setBackground(Color.YELLOW);
        equalsBtn2.setBackground(Color.YELLOW);
        equalsBtn1.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 5, Color.black));
        equalsBtn2.setBorder(BorderFactory.createMatteBorder(5, 5, 0, 0, Color.black));
        equalsBtn1.setFont(new Font(equalsBtn1.getFont().getName(), Font.BOLD, equalsBtn1.getFont().getSize() + 10));
        equalsBtn2.setFont(new Font(equalsBtn2.getFont().getName(), Font.BOLD, equalsBtn2.getFont().getSize() + 10));
        equalsBtn1.setPreferredSize(new Dimension(46, 55));
        equalsBtn2.setPreferredSize(new Dimension(46, 55));
        equalsBtn1.setActionCommand("equal");
        equalsBtn2.setActionCommand("equal");
        equalsBtn1.addActionListener(controller);
        equalsBtn2.addActionListener(controller);

        // equalsBtn1 is added to the WEST region of the CalculatorViewController panel
        this.add(equalsBtn1, BorderLayout.WEST);

        // equalsBtn2 is added to the EAST region of the CalculatorViewController panel
        this.add(equalsBtn2, BorderLayout.EAST);

        // creating clear buttons
        JButton clearBtn1 = new JButton("C");
        JButton clearBtn2 = new JButton("C");
        clearBtn1.setBackground(Color.red);
        clearBtn2.setBackground(Color.red);
        clearBtn1.setFont(new Font(clearBtn1.getFont().getName(), Font.BOLD, clearBtn1.getFont().getSize() + 10));
        clearBtn2.setFont(new Font(clearBtn2.getFont().getName(), Font.BOLD, clearBtn2.getFont().getSize() + 10));
        clearBtn1.setPreferredSize(new Dimension(0, 45));
        clearBtn2.setPreferredSize(new Dimension(0, 45));
        clearBtn1.setActionCommand("clear");
        clearBtn2.setActionCommand("clear");
        clearBtn1.addActionListener(controller);
        clearBtn2.addActionListener(controller);

        //panel with borderLayout to hold clear buttons and keypad Panel
        JPanel clearBtnsAndkeypad = new JPanel(new BorderLayout());

        //clearBtn1 is added to the NORTH region, keyPadPanel to the CENTER & clearBtn2 to the SOUTH region of the panel
        clearBtnsAndkeypad.add(clearBtn1, BorderLayout.NORTH);
        clearBtnsAndkeypad.add(keyPadPanel, BorderLayout.CENTER);
        clearBtnsAndkeypad.add(clearBtn2, BorderLayout.SOUTH);
        clearBtnsAndkeypad.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.black));

        //clearBtnsAndkeypad panel is finally added to the CENTER region of the CalculatorViewController panel
        this.add(clearBtnsAndkeypad, BorderLayout.CENTER);

    } // end of CalculatorViewController constructor

    // method for buttons creation
    private JButton createButton(String text, String ac, Color fg, Color bg, ActionListener handler) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(fg);

        // if ac parameter is null then no need to set ActionCommand
        if (ac != null) {
            button.setActionCommand(ac);
        }

        button.setFont(new Font(button.getFont().getFontName(), button.getFont().getStyle(), 20));
        button.addActionListener(handler);
        return button;
    } // end createButton method

    // This class handles all events generated by the GUI
    private class Controller implements ActionListener {

        //everytime button, radioButton, checkBox is clicked this method is called.
        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            switch (actionCommand) {
                case "one":
                case "two":
                case "three":
                case "four":
                case "five":
                case "six":
                case "seven":
                case "eight":
                case "nine":
                case "zero":
                case "dot":
                    if ("dot".equals(actionCommand)) {
                        try {
                            Float.valueOf(display2.getText().concat("."));
                        } catch (Exception ex) {
                            if (ex.getMessage().equalsIgnoreCase("multiple points")) {
                                return;
                            }
                        }
                    }
                    if (errorCheck) {
                        return;
                    }
                    if (display2.getText().equals("0.0") || operatorAdded || (!actionCommand.equals("zero") && "0".equals(display2.getText()))) {
                        display2.setText(keypadText[Arrays.asList(actionCommandText).indexOf(actionCommand)]);
                    } else if (resultDisplayed) {
                        display2.setText(keypadText[Arrays.asList(actionCommandText).indexOf(actionCommand)]);
                        display1.setText("");
                        resultDisplayed = false;
                    } else {
                        display2.setText(display2.getText().concat(keypadText[Arrays.asList(actionCommandText).indexOf(actionCommand)]));
                    }
                    if (operatorAdded) {
                        operatorAdded = false;
                    }
                    if (backspaceDisabled) {
                        backspaceDisabled = false;
                    }
                    break;
                case "divide":
                case "multiply":
                case "add":
                case "minus":
                    if (errorCheck) {
                        return;
                    }
                    if ("minus".equals(actionCommand)) {
                        if ((display2.getText().equals("0") || display2.getText().equals("0.0") || display2.getText().length() == 0) && !display2.getText().contains("-")) {
                            display2.setText("-");
                            return;
                        }
                    }
                    if (display2.getText().equals("0.0") && display1.getText().length() == 0) {
                        return;
                    }
                    display1.setText(display2.getText().concat(keypadText[Arrays.asList(actionCommandText).indexOf(actionCommand)]));
                    display1Text = display1.getText();
                    operatorAdded = true;
                    backspaceDisabled = true;
                    break;
                case "negate":
                    if (errorCheck) {
                        return;
                    }
                    if (display2.getText().charAt(0) == '-') {
                        display2.setText(display2.getText().substring(1));
                    } else {
                        display2.setText("-".concat(display2.getText()));
                    }
                    break;
                case "checkbox":
                    if (mode_error_label.getText().equalsIgnoreCase("F")) {
                        mode_error_label.setText("I");
                        mode_error_label.setBackground(Color.GREEN);
                        dotButton.setEnabled(false);
                        dotButton.setBackground(new Color(178, 156, 250));
                        display1.setText("");
                        display2.setText("0");
                    }
                    calculatorModel.divisionCounter = 0;
                    break;
                case "backspace":
                    if (backspaceDisabled || errorCheck || display2.getText().length() == 0) {
                        return;
                    }
                    if (display2.getText().length() <= 2 && String.valueOf(display2.getText().charAt(0)).equals("-")) {
                        if ("checkbox".equals(buttonGroup.getSelection().getActionCommand())) {
                            display2.setText("0");
                        } else {
                            display2.setText("0.0");
                        }
                        return;
                    }
                    if (!display2.getText().equals("0.0")) {
                        display2.setText(display2.getText().substring(0, display2.getText().length() - 1));
                    }

                    break;
                case ".0":
                case ".00":
                case "sci":
                    if (errorCheck) {
                        return;
                    }
                    mode_error_label.setText("F");
                    mode_error_label.setBackground(Color.YELLOW);
                    dotButton.setBackground(Color.BLUE);
                    dotButton.setEnabled(true);
                    display1.setText("");
                    display2.setText("0.0");
                    calculatorModel.divisionCounter = 0;
                    break;
                case "equal":
                    if (errorCheck) {
                        return;
                    }
                    if (display1Text.length() == 0 && display1.getText().length() == 0) {
                        return;
                    }
                    calculatorModel.setOperand1(display1Text.substring(0, display1Text.length() - 1));
//                    System.out.println("operand1 = " + display1Text.substring(0, display1Text.length() - 1)); //logging

                    calculatorModel.setArithmeticOperation(display1Text.substring(display1Text.length() - 1));
//                    System.out.println("operator = " + display1Text.substring(display1Text.length() - 1)); //logging

                    calculatorModel.setOperand2(display2.getText());
//                    System.out.println("operand2 = " + display2.getText()); //logging

                    calculatorModel.setOperationalMode(mode_error_label.getText());
                    calculatorModel.setFloatingPointPrecision(buttonGroup.getSelection().getActionCommand());
                    result = calculatorModel.getResult();
                    display2.setText(result);
                    if (clearPressed) {
                        display1Text = display1.getText();
                        clearPressed = false;
                    }
                    display1.setText("");
                    if (calculatorModel.getErrorState()) {
                        mode_error_label.setText("E");
                        mode_error_label.setBackground(Color.red);
                        errorCheck = true;
                    }
                    calculatorModel.setErrorState(false);
                    backspaceDisabled = true;
                    resultDisplayed = true;
                    break;
                case "clear":
                    if (buttonGroup.getSelection().getActionCommand().equals("checkbox")) {
                        display2.setText("0");
                    } else {
                        display2.setText("0.0");
                    }
                    if (errorCheck) {
                        if (buttonGroup.getSelection().getActionCommand().equals("checkbox")) {
                            mode_error_label.setText("I");
                            mode_error_label.setBackground(Color.green);
                        } else {
                            mode_error_label.setText("F");
                            mode_error_label.setBackground(Color.yellow);
                        }
                    }
                    errorCheck = false;
                    resultDisplayed = false;
                    operatorAdded = false;
                    backspaceDisabled = true;
                    clearPressed = true;
                    display1Text = "";
                    display1.setText("");
                    calculatorModel.divisionCounter = 0;
                    break;
            }
        }
    } // end Controller class
} // end CalculatorViewController class
