package expression;

import java.math.BigInteger;

public abstract class AbstractUnaryOperation implements PrioritizedExpression, TripleExpression {
    protected PrioritizedExpression arg;

    protected AbstractUnaryOperation(PrioritizedExpression arg) {
        this.arg = arg;
    }

    @Override
    public int getPriority() {
        return 100;
    }

    public abstract String getOperation();

    @Override
    public boolean isPrimary() {
        return true;
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract int evaluate(int x);

    @Override
    public abstract int evaluate(int x, int y, int z);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getOperation());
        sb.append("(");
        sb.append(arg);
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getOperation());
        if (arg.isPrimary()) {
            sb.append(" ");
        } else {
            sb.append("(");
        }
        sb.append(arg.toMiniString());
        if (!arg.isPrimary()) {
            sb.append(")");
        }
        return sb.toString();
    }
}
