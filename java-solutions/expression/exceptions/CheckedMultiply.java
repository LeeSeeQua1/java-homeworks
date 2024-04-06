package expression.exceptions;

import expression.Multiply;
import expression.PrioritizedExpression;

public class CheckedMultiply extends Multiply {

    public CheckedMultiply(PrioritizedExpression firstArg, PrioritizedExpression secondArg) {
        super(firstArg, secondArg);
    }

    @Override
    public int evaluate(int x) throws OverflowException{
        int a = arg1.evaluate(x);
        int b = arg2.evaluate(x);
        checkOverflow(a, b);
        return a * b;
    }

    @Override
    public int evaluate(int x, int y, int z) throws OverflowException{
        int a = arg1.evaluate(x, y, z);
        int b = arg2.evaluate(x, y, z);
        checkOverflow(a, b);
        return a * b;
    }

    static void checkOverflow(int a, int b) {
        int c = a * b;
        if (a == 0 || b == 0) {
            return;
        }
        if (c / a != b || c / b != a) {
            throw new OverflowException();
        }
    }
}
