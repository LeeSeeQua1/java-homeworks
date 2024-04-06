package expression;

import java.math.BigInteger;
import java.util.Objects;

public abstract class AbstractBinaryOperation implements PrioritizedExpression, TripleExpression {
    protected PrioritizedExpression arg1;
    protected PrioritizedExpression arg2;

    @Override
    public abstract int evaluate(int num);
    @Override
    public abstract int evaluate(int num1, int num2, int num3);
    @Override
    public abstract BigInteger evaluate(BigInteger x);

    public AbstractBinaryOperation(PrioritizedExpression firstArg, PrioritizedExpression secondArg) {
        arg1 = firstArg;
        arg2 = secondArg;
    }

    public boolean isPrimary() {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder expression = new StringBuilder();
        expression.append("(");
        expression.append(arg1);
        expression.append(getOperation());
        expression.append(arg2);
        expression.append(")");
        return expression.toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            AbstractBinaryOperation that = (AbstractBinaryOperation) obj;
            return arg1.equals(that.arg1) && arg2.equals(that.arg2);
        }
        return false;
    }

    public abstract int getPriority();

    public abstract String getOperation();

    public abstract boolean isCommutative();

    public abstract boolean isLeftAssociative();

    @Override
    public int hashCode() {
        return Objects.hash(arg1, arg2, getClass());
    }

    @Override
    public String toMiniString() {
        StringBuilder expression = new StringBuilder();
        if (firstCondition()) {
            expression.append("(");
        }
        expression.append(arg1.toMiniString());
        if (firstCondition()) {
            expression.append(")");
        }
        expression.append(getOperation());
        if (secondCondition()) {
            expression.append("(");
        }
        expression.append(arg2.toMiniString());
        if (secondCondition()) {
            expression.append(")");
        }
        return expression.toString();
    }

    private boolean firstCondition() {
        return getPriorityLevel() > arg1.getPriorityLevel();
    }

    private boolean secondCondition() {
        if (getPriority() > arg2.getPriority()) {
            return true;
        } else if (getPriorityLevel() < arg2.getPriorityLevel()) {
            return false;
        }
        if (getPriority() == arg2.getPriority() && !isCommutative()) {
            return true;
        }
        AbstractBinaryOperation op = (AbstractBinaryOperation)arg2;
        return (getPriorityLevel() == arg2.getPriorityLevel() && !op.isLeftAssociative()
                && this.getClass() != arg2.getClass());
    }
}
