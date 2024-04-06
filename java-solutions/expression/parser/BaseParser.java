package expression.parser;

import expression.exceptions.OverflowException;

public class BaseParser {
    private static final char END = '\0';
    private CharSource source = null;
    private char ch = 0xffff;

    public void sourceInit(CharSource source) {
        this.source = source;
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected int takeNum(boolean isNegative) {
        StringBuilder sb = new StringBuilder();
        if (isNegative) {
            sb.append('-');
        }
        while(between('0', '9')) {
            sb.append(take());
        }
        skipWhitespaces();
        String numStr = sb.toString();
        try {
            Integer.parseInt(numStr);
        } catch(NumberFormatException e) {
            throw new OverflowException();
        }
        return Integer.parseInt(numStr);
    }

    protected String takeVar() {
        char varChar = take();
        skipWhitespaces();
        return String.valueOf(varChar);
    }

    protected void expect(final char expected) {
        if (!take(expected)) {
            throw error("Expected '" + expected + "', found '" + ch + "'");
        }
    }

    protected void expect(final String value) {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean take(final String value) {
        for (final char ch : value.toCharArray()) {
            if (!take(ch)) {
                return false;
            }
        }
        return true;
    }

    public char peek() {
        return ch;
    }

    protected boolean eof() {
        return take(END);
    }

    protected IllegalArgumentException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected void skipWhitespaces() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }

    protected int getPos() {
        return source.getPos();
    }
}
