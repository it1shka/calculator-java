public class CalculatorState {

    private static CalculatorState instance;
    private String savedState;

    public static CalculatorState getInstance() {
        if (instance == null) {
            instance = new CalculatorState();
        }
        return instance;
    }

    private final StringBuilder builder;

    private CalculatorState() {
        builder = new StringBuilder();
        savedState = "";
    }

    public void append(String value) {
        builder.append(value);
    }

    public void delete() {
        builder.deleteCharAt(builder.length() - 1);
    }

    public void clear() {
        builder.setLength(0);
    }

    public double evaluate() {
        final var expression = builder.toString();
        final var result = new Evaluator(expression).execute();
        // clear();
        return result;
    }

    public String getState() {
        return builder.toString();
    }
    public void setState(String state) {
        builder.setLength(0);
        builder.append(state);
    }

    public void saveState() {
        savedState = getState();
    }

    public void loadState() {
        setState(savedState);
    }

}
