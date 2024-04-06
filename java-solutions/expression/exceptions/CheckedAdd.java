package expression.exceptions;

import expression.Add;
import expression.PrioritizedExpression;

public class CheckedAdd extends Add {

    public CheckedAdd(PrioritizedExpression firstArg, PrioritizedExpression secondArg) {
        super(firstArg, secondArg);
    }

    @Override
    public int evaluate(int x) throws OverflowException {
        int a = arg1.evaluate(x);
        int b = arg1.evaluate(x);
        checkOverflow(a, b);
        return a + b;
    }

    @Override
    public int evaluate(int x, int y,  int z) throws OverflowException{
        int a = arg1.evaluate(x, y, z);
        int b = arg2.evaluate(x, y, z);
        checkOverflow(a, b);
        return a + b;
    }

    private void checkOverflow(int a, int b) throws OverflowException{
        if (a >= 0 && Integer.MAX_VALUE - a < b || a < 0 && Integer.MIN_VALUE - a > b) {
            throw new OverflowException();
        }
    }
}
