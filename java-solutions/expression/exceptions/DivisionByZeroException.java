package expression.exceptions;

public class DivisionByZeroException extends ExpressionException{
    public DivisionByZeroException(String message) {
        super(message);
    }

    public DivisionByZeroException() {
        super("division by zero");
    }
}
