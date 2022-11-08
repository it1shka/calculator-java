import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        final var app = new Application();
        app
                .createWindow(300, 450)
                .createInputDisplay(20, 20, 3, 5)
                .createResultDisplay(3, 20, 40, 10)
                .createInputBoard(6, 4);

        // zeroth row
        app
                .createStringButton("(")
                .createStringButton(")")
                .createSaveButton()
                .createLoadButton();

        // first row
        app
                .createClearButton()
                .createDeleteButton()
                .createStringButton("^", " ^ ")
                .createStringButton("÷", " / ");

        // second row
        for (var i = 7; i < 10; i++) {
            app.createStringButton(Integer.toString(i));
        }
        app.createStringButton("x", " * ");

        // third row
        for (var i = 4; i < 7; i++) {
            app.createStringButton(Integer.toString(i));
        }
        app.createStringButton("-", " - ");

        // fourth row
        for (var i = 1; i < 4; i++) {
            app.createStringButton(Integer.toString(i));
        }
        app.createStringButton("+", " + ");

        // fifth
        app
                .createStringButton("0")
                .createStringButton("00")
                .createStringButton(".")
                .createEqualsButton();

        app.run();
    }

    private static final DecimalFormat format;
    static {
        final var formatSymbols = DecimalFormatSymbols.getInstance(Locale.ENGLISH);
        format = new DecimalFormat("0", formatSymbols);
        format.setMaximumFractionDigits(15);
    }

    public static String formatDouble(double value) {
        return format.format(value);
    }
}
