package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteOrder;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorViewController extends JPanel {

    private JTextField display1;
    private JTextField display2;
    private JLabel mode_error_label;
    private JButton dotButton;

    public CalculatorViewController() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));

        mode_error_label = new JLabel("F", JLabel.CENTER);
        mode_error_label.setPreferredSize(new Dimension(46, 55));
        mode_error_label.setBackground(Color.YELLOW);
        mode_error_label.setOpaque(true);
        mode_error_label.setFont(new Font(mode_error_label.getFont().getName(), Font.BOLD, mode_error_label.getFont().getSize() + 10));
        mode_error_label.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 5, Color.BLACK));

        JButton backButton = new JButton(Character.toString('\u21DA'));
        backButton.setPreferredSize(new Dimension(45, 55));
        backButton.setBackground(Color.YELLOW);
        backButton.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Color.BLACK));
        backButton.setFont(new Font(backButton.getFont().getName(), Font.BOLD, backButton.getFont().getSize() + 10));
        backButton.setToolTipText("Backspace (Alt-B)");

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

        Box displayBox = Box.createVerticalBox();
        displayBox.add(display1);
        displayBox.add(display2);

        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.add(mode_error_label, BorderLayout.WEST);
        upperPanel.add(displayBox, BorderLayout.CENTER);
        upperPanel.add(backButton, BorderLayout.EAST);
        upperPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.black));

        JCheckBox modeCheckBox = new JCheckBox("Int");
        modeCheckBox.setPreferredSize(new Dimension(40, 0));
        modeCheckBox.setBackground(Color.green);

        JRadioButton _0RadioButton = new JRadioButton(".0", false);
        _0RadioButton.setBackground(Color.YELLOW);
        JRadioButton _00RadioButton = new JRadioButton(".00", true);
        _00RadioButton.setBackground(Color.YELLOW);
        JRadioButton sciRadioButton = new JRadioButton("Sci", false);
        sciRadioButton.setBackground(Color.YELLOW);

        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(_0RadioButton);
        radioButtonGroup.add(_00RadioButton);
        radioButtonGroup.add(sciRadioButton);

        Box lowerBox = Box.createHorizontalBox();
        lowerBox.add(modeCheckBox);
        lowerBox.add(Box.createGlue());
        lowerBox.add(_0RadioButton);
        lowerBox.add(_00RadioButton);
        lowerBox.add(sciRadioButton);
        lowerBox.setBackground(Color.BLACK);
        lowerBox.setOpaque(true);
        lowerBox.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, Color.black));

        JPanel lowerPanel = new JPanel();
        lowerPanel.add(lowerBox);
        lowerPanel.setBackground(Color.BLACK);
        lowerPanel.setOpaque(true);
        lowerPanel.setBorder(BorderFactory.createEmptyBorder());

        Box superBox = Box.createVerticalBox();
        superBox.add(upperPanel);
        superBox.add(lowerBox);

        this.add(superBox, BorderLayout.PAGE_START);

        JPanel keyPadPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        keyPadPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        Controller controller = new Controller();

        String[] keypadText = new String[]{"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", Character.toString('\u00B1'), "+"};
        String[] actionCommand = new String[]{"seven", "eight", "nine", "divide", "four", "five", "six", "multiply", "one", "two", "three", "minus", "zero", "dot", "negate", "add"};

        //loop for creating keypad buttons
        for (int i = 0; i < keypadText.length; i++) {
            if (keypadText[i].matches("[0-9]")) {
                keyPadPanel.add(createButton(keypadText[i], actionCommand[i], Color.BLACK, Color.BLUE, controller));
            }
            if (".".equals(keypadText[i])) {
                dotButton = createButton(keypadText[i], actionCommand[i], Color.BLACK, Color.BLUE, controller);
                keyPadPanel.add(dotButton);
            }
            if (keypadText[i].matches("[-+*/]")) {
                keyPadPanel.add(createButton(keypadText[i], actionCommand[i], Color.BLACK, Color.CYAN, controller));
            }
            if (Character.toString('\u00B1').equals(keypadText[i])) {
                keyPadPanel.add(createButton(keypadText[i], actionCommand[i], Color.BLACK, Color.PINK, controller));
            }
        }

        JButton equalsBtn1 = new JButton("=");
        JButton equalsBtn2 = new JButton("=");
        equalsBtn1.setBackground(Color.magenta);
        equalsBtn2.setBackground(Color.magenta);
        equalsBtn1.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 5, Color.black));
        equalsBtn2.setBorder(BorderFactory.createMatteBorder(5, 5, 0, 0, Color.black));
        equalsBtn1.setFont(new Font(equalsBtn1.getFont().getName(), Font.BOLD, equalsBtn1.getFont().getSize() + 10));
        equalsBtn2.setFont(new Font(equalsBtn2.getFont().getName(), Font.BOLD, equalsBtn2.getFont().getSize() + 10));
        equalsBtn1.setPreferredSize(new Dimension(46, 55));
        equalsBtn2.setPreferredSize(new Dimension(46, 55));

        this.add(equalsBtn1, BorderLayout.WEST);
        this.add(equalsBtn2, BorderLayout.EAST);

        JButton clearBtn1 = new JButton("C");
        JButton clearBtn2 = new JButton("C");
        clearBtn1.setBackground(Color.red);
        clearBtn2.setBackground(Color.red);
        clearBtn1.setFont(new Font(clearBtn1.getFont().getName(), Font.BOLD, clearBtn1.getFont().getSize() + 10));
        clearBtn2.setFont(new Font(clearBtn2.getFont().getName(), Font.BOLD, clearBtn2.getFont().getSize() + 10));
        clearBtn1.setPreferredSize(new Dimension(0, 45));
        clearBtn2.setPreferredSize(new Dimension(0, 45));

        JPanel clearBtnsAndkeypad = new JPanel(new BorderLayout());

        clearBtnsAndkeypad.add(clearBtn1, BorderLayout.NORTH);
        clearBtnsAndkeypad.add(keyPadPanel, BorderLayout.CENTER);
        clearBtnsAndkeypad.add(clearBtn2, BorderLayout.SOUTH);
        clearBtnsAndkeypad.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.black));

        this.add(clearBtnsAndkeypad, BorderLayout.CENTER);
    }

    private JButton createButton(String text, String ac, Color fg, Color bg, ActionListener handler) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(fg);
        if (ac != null) {
            button.setActionCommand(ac);
        }
        button.setFont(new Font(button.getFont().getFontName(), button.getFont().getStyle(), 20));
        button.addActionListener(handler);
        return button;
    }

    private class Controller implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommandString = e.getActionCommand();
            display2.setText(actionCommandString);
        }

    }
}
