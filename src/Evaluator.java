import java.util.Stack;

public class Evaluator {

    private final ExpressionReader reader;
    private final Stack<Double> output;
    private final Stack<String> operators;

    public Evaluator(String expression) {
        reader = new ExpressionReader(expression);
        output = new Stack<>();
        operators = new Stack<>();
    }

    private static boolean isNumber(String token) {
        return Character.isDigit(token.charAt(0));
    }

    @FunctionalInterface
    private interface BinaryOperator {
        double eval(double a, double b);
    }

    private static BinaryOperator getOperator(String op) {
        return switch (op) {
            case "+" -> Double::sum;
            case "-" -> (a, b) -> a - b;
            case "*" -> (a, b) -> a * b;
            case "/" -> (a, b) -> a / b;
            case "^" -> Math::pow;
            default -> (a, b) -> Double.NaN;
        };
    }

    private static int getPrecedence(String op) {
        return switch (op) {
            case "+", "-" -> 0;
            case "*", "/" -> 1;
            case "^" -> 2;
            default -> -1;
        };
    }

    private static boolean isLeftAssociative(String op) {
        return !op.equals("^");
    }

    public double execute() {
        try {
            while (!reader.eof()) {
                final var token = reader.read();

                if (isNumber(token)) {
                    final var value = Double.parseDouble(token);
                    output.add(value);
                    continue;
                }

                if (token.equals("(")) {
                    operators.push(token);
                    continue;
                }

                if (token.equals(")")) {
                    while (!operators.empty() && !operators.lastElement().equals("(")) {
                        final var op = operators.pop();
                        performOperation(op);
                    }
                    operators.pop();
                    continue;
                }

                final var myPrecedence = getPrecedence(token);
                while (!operators.empty()) {
                    final var topOperator = operators.lastElement();
                    final var precedence = getPrecedence(topOperator);
                    if (precedence > myPrecedence || (precedence == myPrecedence && isLeftAssociative(token))) {
                        performOperation(operators.pop());
                    } else break;
                }
                operators.push(token);
            }
            while (!operators.empty()) {
                performOperation(operators.pop());
            }
            return output.pop();
        } catch (Exception e) {
            // System.out.println(Arrays.toString(e.getStackTrace()));
            return Double.NaN;
        }
    }

    private void performOperation(String operator) {
        final var opFunc = getOperator(operator);
        final var b = output.pop();
        final var a = output.pop();
        final var result = opFunc.eval(a, b);
        output.push(result);
    }

}
