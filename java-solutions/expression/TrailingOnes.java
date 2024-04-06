package expression;

public class TrailingOnes extends AbstractUnaryOperation {
    public TrailingOnes(PrioritizedExpression arg) {
        super(arg);
    }

    @Override
    public String getOperation() {
        return "t1";
    }

    @Override
    public int evaluate(int x) {
        int num = arg.evaluate(x);
        int count = 0;
        while ((num & 1) == 1) {
            count++;
            num = num >>> 1;
        }
        return count;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int num = arg.evaluate(x, y, z);
        int count = 0;
        while ((num & 1) == 1) {
            count++;
            num = num >>> 1;
        }
        return count;
    }
}
