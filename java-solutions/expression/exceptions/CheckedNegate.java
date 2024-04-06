package expression.exceptions;

import expression.Negate;
import expression.PrioritizedExpression;

public class CheckedNegate extends Negate {
    public CheckedNegate(PrioritizedExpression exp) {
        super(exp);
    }

    @Override
    public int evaluate(int x) throws OverflowException {
        int a = arg.evaluate(x);
        checkOverflow(a);
        return -a;
    }

    @Override
    public int evaluate(int x, int y, int z) throws OverflowException {
        int a = arg.evaluate(x, y, z);
        checkOverflow(a);
        return -a;
    }

    private void checkOverflow(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }
}
