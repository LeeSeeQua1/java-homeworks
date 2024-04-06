package expression.exceptions;

public class UnknownSymbolException extends ParserException{

    public UnknownSymbolException(String message) {
        super(message);
    }

    public UnknownSymbolException(char ch, int pos) {
        super("Unknown symbol " + ch + " at position " + pos);
    }
}
