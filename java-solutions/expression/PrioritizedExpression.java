package expression;

public interface PrioritizedExpression extends Expression, TripleExpression, BigIntegerExpression {
    int getPriority();

    default int getPriorityLevel() {
        return getPriority() / 10;
    }

    boolean isPrimary();
}
