package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.PrioritizedExpression;

import java.math.BigInteger;

public class Max extends AbstractBinaryOperation {

    public Max(PrioritizedExpression firstArg, PrioritizedExpression secondArg) {
        super(firstArg, secondArg);
    }

    private int max(int a, int b) {
        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }
    @Override
    public int evaluate(int num) {
        return max(arg1.evaluate(num), arg2.evaluate(num));
    }

    @Override
    public int evaluate(int num1, int num2, int num3) {
        return max(arg1.evaluate(num1, num2, num3), arg2.evaluate(num1, num2, num3));
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getOperation() {
        return " max ";
    }

    @Override
    public boolean isCommutative() {
        return true;
    }

    @Override
    public boolean isLeftAssociative() {
        return false;
    }
}
