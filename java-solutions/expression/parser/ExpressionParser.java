package expression.parser;

import expression.*;

import java.util.List;

public class ExpressionParser extends BaseParser implements TripleParser {
    private final int MAX_LEVEL = 10;

    public ExpressionParser() {}

    @Override
    public TripleExpression parse(String expression) {
        sourceInit(new StringSource(expression));
        take();
        skipWhitespaces();
        return parseLevel(0);
    }

    private PrioritizedExpression parseLevel(int level) {
        PrioritizedExpression first;
        if (level < MAX_LEVEL) {
            first = parseLevel(level + 1);
        } else {
            first = parsePrimary();
        }
        while (true) {
            boolean opChar = false;
            for (char op : getBinOp(level)) {
                if (take(op)) {
                    opChar = true;
                    skipWhitespaces();
                    first = chToBinExp(first, parseLevel(level + 1), op);
                    skipWhitespaces();
                }
            }
            if (!opChar) {
                return first;
            }
        }
    }

    private PrioritizedExpression parsePrimary() {
        if (take('(')) {
            skipWhitespaces();
            PrioritizedExpression e = parseLevel(0);
            skipWhitespaces();
            take(')');
            skipWhitespaces();
            return e;
        } else if (between('0', '9')) {
            return new Const(takeNum(false));
        } else if (between('x', 'z')) {
            return new Variable(takeVar());
        } else {
            for (String op : getUnOp()) {
                if (take(op)) {
                    return strToUnExp(op);
                }
            }
            throw new IllegalArgumentException("Unknown primary");
        }
    }

    private PrioritizedExpression chToBinExp(PrioritizedExpression first, PrioritizedExpression second, char op) {
        return switch(op) {
            case '+' -> new Add(first, second);
            case '-' -> new Subtract(first, second);
            case '*' -> new Multiply(first, second);
            case '/' -> new Divide(first, second);
            case '&' -> new BitwiseAnd(first, second);
            case '^' -> new BitwiseXor(first, second);
            case '|' -> new BitwiseOr(first, second);
            default -> throw new IllegalArgumentException("Unknown symbol");
        };
    }

    private PrioritizedExpression strToUnExp(String op) {
        return switch (op) {
            case("l1") -> {
                skipWhitespaces();
                yield new LeadingOnes(parsePrimary());
            }
            case("t1") -> {
                skipWhitespaces();
                yield new TrailingOnes(parsePrimary());
            }
            case("-") -> {
                if (between('0', '9')) {
                    yield new Const(takeNum(true));
                } else {
                    skipWhitespaces();
                    yield new Negate(parsePrimary());
                }
            }
            default -> throw new UnsupportedOperationException("Unknown unary operation");
        };
    }

    private List<Character> getBinOp(int level) {
        return switch(level) {
            case 1 -> List.of('|');
            case 2 -> List.of('^');
            case 3 -> List.of('&');
            case 5 -> List.of('+', '-');
            case 6 -> List.of('*', '/');
            default -> List.of();
        };
    }

    private List<String> getUnOp() {
        return List.of("-", "l1", "t1");
    }
}