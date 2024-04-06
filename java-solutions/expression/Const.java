package expression;

import java.math.BigInteger;
import java.util.Objects;

public class Const implements PrioritizedExpression {
    private final Number value;

    public Const(int num) {
        value = num;
    }

    public Const(BigInteger num) {
        value = num;
    }

    public int evaluate(int num) {
        return value.intValue();
    }

    public int evaluate(int x, int y, int z) {
        return value.intValue();
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return (BigInteger)value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    private Number getValue() {
        return value;
    }

    public int getPriority() {
        return 100;
    }

    @Override
    public boolean isPrimary() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == Const.class) {
            Const that = (Const) obj;
            return value.equals(that.getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
