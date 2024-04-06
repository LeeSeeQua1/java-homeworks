package expression;

public class LeadingOnes extends AbstractUnaryOperation {
    public LeadingOnes(PrioritizedExpression arg) {
        super(arg);
    }

    @Override
    public String getOperation() {
        return "l1";
    }

    @Override
    public int evaluate(int x) {
        int num = arg.evaluate(x);
        int count = 0;
        while ((num & (1 << 31)) == 1 << 31) {
            count++;
            num = num << 1;
        }
        return count;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int num = arg.evaluate(x, y, z);
        int count = 0;
        while ((num & (1 << 31)) == 1 << 31) {
            count++;
            num = num << 1;
        }
        return count;
    }
}
