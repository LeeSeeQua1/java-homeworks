package expression;


import java.math.BigInteger;

public class Variable implements PrioritizedExpression {
    private final String name;

    public Variable(String str) {
        name = str;
    }

    public int evaluate(int num) {
        return num;
    }

    public int evaluate(int x, int y, int z) {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new IllegalArgumentException("Unknown variable");
        };
    }

    public BigInteger evaluate(BigInteger num) {
        return num;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    public int getPriority() {
        return 101;
    }

    @Override
    public boolean isPrimary() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == Variable.class) {
            Variable that = (Variable) obj;
            return name.equals(that.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
