package expression.exceptions;

import expression.Expression;
import expression.PrioritizedExpression;
import expression.TripleExpression;

public class Main {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        PrioritizedExpression exp = (PrioritizedExpression) parser.parse("1000000*x*x*x*x*x/(x-1)");
        System.out.printf("%s\t%s%n", "x", "f");
        for (int i = 0; i < 11; i++) {
            String f;
            try {
                int res = exp.evaluate(i);
                f = String.valueOf(res);
            } catch (OverflowException e) {
                f = "overflow";
            } catch (DivisionByZeroException e) {
                f = "division by zero";
            }
            System.out.printf("%d\t%s%n", i, f);
        }
    }
}
