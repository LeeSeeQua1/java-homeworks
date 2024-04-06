package expression;

import java.math.BigInteger;

public class Negate extends AbstractUnaryOperation {

    public Negate(PrioritizedExpression exp) {
        super(exp);
    }

    @Override
    public String getOperation() {
        return "-";
    }

    @Override
    public boolean isPrimary() {
        return super.isPrimary();
    }

    @Override
    public int evaluate(int x) {
        return -arg.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -arg.evaluate(x, y, z);
    }
}
