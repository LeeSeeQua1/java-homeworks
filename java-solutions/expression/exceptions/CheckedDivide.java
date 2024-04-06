package expression.exceptions;

import expression.Divide;
import expression.PrioritizedExpression;

public class CheckedDivide extends Divide {
    public CheckedDivide(PrioritizedExpression firstArg, PrioritizedExpression secondArg) {
        super(firstArg, secondArg);
    }

    @Override
    public int evaluate(int x) throws OverflowException, DivisionByZeroException{
        int a = arg1.evaluate(x);
        int b = arg2.evaluate(x);
        if (b == 0) {
            throw new DivisionByZeroException();
        }
        checkOverflow(a, b);
        return a / b;
    }

    @Override
    public int evaluate(int x, int y, int z) throws OverflowException{
        int a = arg1.evaluate(x, y, z);
        int b = arg2.evaluate(x, y, z);
        if (b == 0) {
            throw new DivisionByZeroException();
        }
        checkOverflow(a, b);
        return a / b;
    }

    private void checkOverflow(int a, int b) {
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new DivisionByZeroException();
        }
    }
}
