package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.StringSource;
import expression.parser.TripleParser;

import java.util.List;
import java.util.Objects;

public class ExpressionParser extends BaseParser implements TripleParser {
    private final int MAX_LEVEL = 10;
    private String currBinOp = "";
    private final List<Character> opChar = List.of('+', '-', '*', '/', 'l', 't',
            '&', '^', '|', 'm', 'i', 'n', 'a', 'x');
    private final List<String> opPref = List.of("+", "-", "*", "/",
            "l1", "t1", "&", "^", "|", "**", "//", "m", "mi", "min", "ma", "max");

    public ExpressionParser() {}

    @Override
    public TripleExpression parse(String expression) {
        sourceInit(new StringSource(expression));
        currBinOp = "";
        take();
        skipWhitespaces();
        System.err.println("Expression: " + expression);
        PrioritizedExpression exp = parseLevel(0);
        if (!eof()) {
            if (!isExpChar()) {
                throw new UnknownSymbolException(peek(), getPos());
            }
            throw new UnexpectedSymbolException(peek(), getPos());
        }
        return exp;
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
            if (currBinOp.isEmpty()) {
                takeBinOp();
            }
            for (String op : getBinOp(level)) {
                if (currBinOp.equals(op)) {
                    opChar = true;
                    skipWhitespaces();
                    currBinOp = "";
                    first = strToBinExp(first, parseLevel(level + 1), op);
                    skipWhitespaces();
                }
            }
            if (!opChar) {
                return first;
            }
        }
    }

    private PrioritizedExpression parsePrimary() {
        if (eof()) {
            throw new UnexpectedEOFException(getPos());
        }
        if (!isExpChar()) {
            throw new UnknownSymbolException(peek(), getPos());
        }
        if (take('(')) {
            int lastParPos = getPos();
            skipWhitespaces();
            if (!isExpChar()) {
                throw new UnknownSymbolException(peek(), getPos());
            }
            PrioritizedExpression e = parseLevel(0);
            skipWhitespaces();
            if (!eof() && !isExpChar()) {
                throw new UnknownSymbolException(peek(), getPos());
            }
            if (!take(')')) {
                throw new UnmatchedParenthesesException(lastParPos - 1);
            }
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
            if (!isExpChar()) {
                throw new UnknownSymbolException(peek(), getPos());
            }
            throw new UnexpectedSymbolException(peek(), getPos());
        }
    }

    private PrioritizedExpression strToBinExp(PrioritizedExpression first, PrioritizedExpression second, String op) {
        try {
            return switch(op) {
                case "+" -> new CheckedAdd(first, second);
                case "-" -> new CheckedSubtract(first, second);
                case "*" -> new CheckedMultiply(first, second);
                case "/" -> new CheckedDivide(first, second);
                case "&" -> new BitwiseAnd(first, second);
                case "^" -> new BitwiseXor(first, second);
                case "|" -> new BitwiseOr(first, second);
                case "min" -> new Min(first, second);
                case "max" -> new Max(first, second);
                default -> throw new IllegalArgumentException("Unknown operation symbol");
            };
        } catch (OverflowException | DivisionByZeroException e) {
            System.out.println(e.getMessage());
        }
        return null;
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
                    if (eof()) {
                        throw new UnexpectedEOFException(getPos());
                    }
                    if (!isPrimChar()) {
                        throw new UnexpectedSymbolException(peek(), getPos());
                    }
                    yield new CheckedNegate(parsePrimary());
                }
            }
            default -> throw new UnsupportedOperationException("Unknown unary operation");
        };
    }

    private List<String> getBinOp(int level) {
        return switch(level) {
            case 0 -> List.of("min", "max");
            case 1 -> List.of("|");
            case 2 -> List.of("^");
            case 3 -> List.of("&");
            case 5 -> List.of("+", "-");
            case 6 -> List.of("*", "/");
            default -> List.of();
        };
    }

    private List<String> getUnOp() {
        return List.of("-", "l1", "t1");
    }

    private boolean isOpChar() {
        for (char ch : opChar) {
            if (peek() == ch) {
                return true;
            }
        }
        return false;
    }

    private boolean isExpChar() {
        return isOpChar() || notOpPrim();
    }

    private boolean isExpStr(String expStr) {
        for (String op : opPref) {
            if (Objects.equals(expStr, op)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPrimChar() {
        for (String unOp : getUnOp()) {
            if (unOp.charAt(0) == peek()) {
                return true;
            }
        }
        return notOpPrim();
    }

    private boolean notOpPrim() {
        return between('0', '9') || between('x', 'z')
                || peek() == '(' || peek() == ')';
    }

    private boolean isBinOpPref(StringBuilder op) {
        op.append(peek());
        String str = op.toString();
        op.setLength(op.length() - 1);
        for (String binOp: opPref) {
            if (str.equals(binOp)) {
                return true;
            }
        }
        return false;
    }

    private void takeBinOp() {
        StringBuilder op = new StringBuilder();
        while (isExpChar() && isBinOpPref(op)) {
            op.append(take());
        }
        if (op.isEmpty()) {
            return;
        }
        currBinOp = op.toString();
        if (!isExpStr(currBinOp)) {
            throw new UnexpectedSymbolException(currBinOp.charAt(0), getPos());
        }
        if (currBinOp.charAt(0) == 'm' && between('0', '9')) {
            throw new UnknownSymbolException(peek(), getPos());
        }
    }
}