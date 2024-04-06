package expression;

import java.math.BigInteger;

public class Subtract extends AbstractBinaryOperation {
    public Subtract(PrioritizedExpression firstArg, PrioritizedExpression secondArg) {
        super(firstArg, secondArg);
    }

    @Override
    public int evaluate(int num) {
        return arg1.evaluate(num) - arg2.evaluate(num);
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return arg1.evaluate(x).subtract(arg2.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return arg1.evaluate(x, y, z) - arg2.evaluate(x, y, z);
    }

    @Override
    public int getPriority() {
        return 51;
    }

    @Override
    public String getOperation() {
        return " - ";
    }

    @Override
    public boolean isCommutative() {
        return false;
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }
}
