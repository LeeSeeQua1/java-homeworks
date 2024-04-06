package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.PrioritizedExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(PrioritizedExpression firstArg, PrioritizedExpression secondArg) {
        super(firstArg, secondArg);
    }

    @Override
    public int evaluate(int x) throws OverflowException {
        int a = arg1.evaluate(x);
        int b = arg2.evaluate(x);
        checkOverflow(a, b);
        return a - b;
    }

    @Override
    public int evaluate(int x, int y, int z) throws OverflowException{
        int a = arg1.evaluate(x, y, z);
        int b = arg2.evaluate(x, y, z);
        checkOverflow(a, b);
        return a - b;
    }

    private void checkOverflow(int a, int b) {
        if (a >= 0 && b < 0 && a - Integer.MAX_VALUE > b
                || a < 0 && b > 0 && a - Integer.MIN_VALUE < b) {
            throw new OverflowException();
        }
    }
}
