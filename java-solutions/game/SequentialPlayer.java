package game;

public class SequentialPlayer implements Player {
    @Override
    public Move move(final ReadOnlyPosition position, final Cell cell) {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                final Move move = new Move(r, c, cell);
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
