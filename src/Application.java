import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Application {

    private JFrame window;
    private JTextField inputDisplay;
    private JTextField resultDisplay;
    private JPanel inputBoard;

    public Application createWindow(int width, int height) {
        window = new JFrame("Calculator");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(width, height);
        final var layout = new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS);
        window.getContentPane().setLayout(layout);
        return this;
    }

    public Application createInputDisplay(int top, int left, int bottom, int right) {
        inputDisplay = new JTextField();
        inputDisplay.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputDisplay.getMinimumSize().height));
        inputDisplay.setBorder(new EmptyBorder(top, left, bottom, right));
        inputDisplay.setEditable(false);
        inputDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
        inputDisplay.setFont(new Font("Arial", Font.BOLD, 20));
        inputDisplay.setText("Enter your expression here:");
        window.add(inputDisplay);
        return this;
    }

    public Application createResultDisplay(int top, int left, int bottom, int right) {
        resultDisplay = new JTextField();
        resultDisplay.setMaximumSize(new Dimension(Integer.MAX_VALUE, resultDisplay.getMinimumSize().height));
        resultDisplay.setBorder(new EmptyBorder(top, left, bottom, right));
        resultDisplay.setEditable(false);
        resultDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
        resultDisplay.setFont(new Font("Arial", Font.ITALIC, 16));
        resultDisplay.setText("The result will be here");
        window.add(resultDisplay);
        return this;
    }

    public Application createInputBoard(int rows, int cols) {
        inputBoard = new JPanel();
        final var layout = new GridLayout(rows, cols);
        inputBoard.setLayout(layout);
        window.add(inputBoard);
        return this;
    }

    public Application createStringButton(String title, String toAppend) {
        final var button = new JButton(title);
        button.addActionListener(e -> {
            final var calc = CalculatorState.getInstance();
            calc.append(toAppend);
            inputDisplay.setText(calc.getState());

            final var result = calc.evaluate();
            resultDisplay.setText(Main.formatDouble(result));
        });
        inputBoard.add(button);
        return this;
    }

    public Application createStringButton(String number) {
        return createStringButton(number, number);
    }

    public Application createClearButton() {
        final var button = new JButton("C");
        button.addActionListener(e -> {
            CalculatorState.getInstance().clear();
            updateDisplay();
        });
        inputBoard.add(button);
        return this;
    }

    public Application createDeleteButton() {
        final var button = new JButton(">");
        button.addActionListener(e -> {
            CalculatorState.getInstance().delete();
            updateDisplay();
        });
        inputBoard.add(button);
        return this;
    }

    public Application createEqualsButton() {
        final var button = new JButton("=");
        button.addActionListener(e -> {
            final var result = CalculatorState.getInstance().evaluate();
            final var nextState = (Double.isNaN(result) || result == 0) ? "" : Main.formatDouble(result);
            CalculatorState.getInstance().setState(nextState);
            updateDisplay();
        });
        inputBoard.add(button);
        return this;
    }

    public Application createSaveButton() {
        final var button = new JButton("S");
        button.addActionListener(e -> {
            CalculatorState.getInstance().saveState();
        });
        inputBoard.add(button);
        return this;
    }

    public Application createLoadButton() {
        final var button = new JButton("L");
        button.addActionListener(e -> {
            CalculatorState.getInstance().loadState();
            updateDisplay();
        });
        inputBoard.add(button);
        return this;
    }

    public void run() {
        window.setVisible(true);
    }

    private void updateDisplay() {
        final var calc = CalculatorState.getInstance();

        final var input = calc.getState();
        final var result = calc.evaluate();

        inputDisplay.setText(input.length() > 0 ? input : "0");
        if (input.length() == 0) resultDisplay.setText("0");
        else resultDisplay.setText(Main.formatDouble(result));
    }

}
