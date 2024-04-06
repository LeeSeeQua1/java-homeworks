package expression;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter x");
            int x = sc.nextInt();
            Expression exp = new Add(new Subtract(new Multiply(new Variable("x"), new Variable("x")),
                    new Multiply(new Const(2), new Variable("x"))), new Const(1));
            System.out.println("Value of x^2 - 2x + 1: " + exp.evaluate(x));
        } catch (InputMismatchException e) {
            System.err.println("The value of x is not an integer");
        } finally {
            sc.close();
        }
    }
}
