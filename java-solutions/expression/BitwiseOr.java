package expression;

import java.math.BigInteger;

public class BitwiseOr extends AbstractBinaryOperation {
    public BitwiseOr(PrioritizedExpression firstArg, PrioritizedExpression secondArg) {
        super(firstArg, secondArg);
    }

    @Override
    public int evaluate(int num) {
        return arg1.evaluate(num) | arg2.evaluate(num);
    }

    @Override
    public int evaluate(int num1, int num2, int num3) {
        return arg1.evaluate(num1, num2, num3) | arg2.evaluate(num1, num2, num3);
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public String getOperation() {
        return " | ";
    }

    @Override
    public boolean isCommutative() {
        return true;
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }
}
