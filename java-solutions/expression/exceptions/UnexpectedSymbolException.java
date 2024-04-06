package expression.exceptions;

public class UnexpectedSymbolException extends ParserException {
    public UnexpectedSymbolException(String message) {
        super(message);
    }

    public UnexpectedSymbolException(char ch, int currPos) {
        super("Unexpected symbol " + ch + " at position " + currPos);
    }
}
