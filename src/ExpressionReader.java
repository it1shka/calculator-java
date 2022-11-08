import java.util.function.Function;

public class ExpressionReader {

    private final String expression;
    private int pointer;

    public ExpressionReader(String expression) {
        this.expression = expression;
        pointer = 0;
    }

    private char peek() {
        return expression.charAt(pointer);
    }

    private char next() {
        return expression.charAt(pointer++);
    }

    public boolean eof() {
        return pointer >= expression.length();
    }

    private String readWhile(Function<Character, Boolean> predicate) {
        var output = new StringBuilder();
        while (!eof() && predicate.apply(peek())) {
            output.append(next());
        }
        return output.toString();
    }

    private String readNumber() {
        var number = readWhile(Character::isDigit);
        if (!eof() && peek() == '.') {
            number += next();
            number += readWhile(Character::isDigit);
        }
        return number;
    }

    public String read() throws Exception {
        readWhile(Character::isWhitespace);
        if (eof()) return null;
        return switch (peek()) {
            case '+', '-', '/', '*', '^', '(', ')' -> Character.toString(next());
            default -> {
                if (Character.isDigit(peek())) {
                    yield readNumber();
                }
                throw new Exception("Unexpected character: " + peek());
            }
        };
    }

}
