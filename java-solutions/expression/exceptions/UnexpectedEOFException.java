package expression.exceptions;

public class UnexpectedEOFException extends ParserException {
    public UnexpectedEOFException(String message) {
        super(message);
    }

    public UnexpectedEOFException(int currPos) {
        super("Unexpected EOF at position " + currPos);
    }
}
