package game;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final ReadOnlyPosition position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            try {
                String row = in.next();
                String col = in.next();
                final Move move = new Move(Integer.parseInt(row), Integer.parseInt(col), cell);
                if (position.isValid(move)) {
                    return move;
                }
                out.println("Move " + move + " is invalid, try again");
            } catch (NoSuchElementException | IllegalStateException | NumberFormatException e) {
                out.println("Invalid arguments for Move");
            }
        }
    }
}
