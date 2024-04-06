package expression.exceptions;

public class UnmatchedParenthesesException extends ParserException {
    public UnmatchedParenthesesException(String message) {
        super(message);
    }

    public UnmatchedParenthesesException(int lastBracketPos) {
        super("Unmatched parentheses at position " + lastBracketPos);
    }
}
