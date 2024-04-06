package game;

import java.util.Arrays;
import java.util.Map;

public abstract class AbstractBoard implements Board, Position {
    protected static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );
    protected final int height;
    protected final int width;
    protected Cell[][] cells;
    protected Cell turn;
    protected final int winStreak;
    private int freeCells;

    protected AbstractBoard(int boardHeight, int boardWidth, int streak) {
        if (boardHeight < 0 || boardWidth < 0) {
            throw new IllegalArgumentException("Board parameters cannot be negative");
        }
        if ((long)boardHeight * boardWidth > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Board size is too big");
        }
        height = boardHeight;
        width = boardWidth;
        cells = new Cell[height][width];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        winStreak = streak;
        freeCells = countFreeCells(height, width);
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        int row = move.getRow();
        int col = move.getColumn();
        cells[row][col] = move.getValue();
        freeCells--;
        if (checkWin(move)) {
            return Result.WIN;
        }
        if (checkDraw()) {
            return Result.DRAW;
        }
        updateTurn(move);
        return Result.UNKNOWN;
    }

    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < height
                && 0 <= move.getColumn() && move.getColumn() < width
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == move.getValue();
    }

    protected boolean checkWin(Move move) {
        int row = move.getRow();
        int col = move.getColumn();
        Cell cell = move.getValue();
        return checkStreak(row, col, cell);
    }

    private int countStreak(int row, int col, int rowShift, int colShift, Cell value) {
        int rowIdx = row;
        int colIdx = col;
        int k = 0;
        while (rowIdx >= 0 && rowIdx < height
                && colIdx >= 0 && colIdx < width
                && cells[rowIdx][colIdx] == value
                && Math.abs(row - rowIdx) < winStreak) {
            rowIdx += rowShift;
            colIdx += colShift;
            k++;
        }
        return k;
    }

    private boolean checkStreak(int row, int col, Cell value) {
        int verticalStreak = countStreak(row, col, 1, 0, value) +
                countStreak(row, col, -1, 0, value) - 1;

        int horizontalStreak = countStreak(row, col, 0, 1, value) +
                countStreak(row, col, 0, -1, value) - 1;

        int mainDiagStreak = countStreak(row, col, 1, 1, value) +
                countStreak(row, col, -1, -1, value) - 1;

        int sideDiagStreak = countStreak(row, col, 1, -1, value) +
                countStreak(row, col, -1, 1, value) - 1;

        return verticalStreak >= winStreak
                || horizontalStreak >= winStreak
                || mainDiagStreak >= winStreak
                || sideDiagStreak >= winStreak;
    }

    protected abstract int countFreeCells(int height, int width);

    @Override
    public Cell getCell() {
        return turn;
    }

    private boolean checkDraw() {
        return freeCells == 0;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    protected void updateTurn(Move move) {
        turn = turn == Cell.X ? Cell.O : Cell.X;
    }

    @Override
    public ReadOnlyPosition getReadOnlyPosition() {
        return new ReadOnlyPosition(this);
    }

    public void clear() {
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        freeCells = countFreeCells(height, width);
    }
}