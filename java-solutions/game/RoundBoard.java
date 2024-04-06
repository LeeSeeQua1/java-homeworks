package game;

import java.util.Arrays;

public class RoundBoard extends AbstractBoard {
    private final boolean[][] inactiveCells;

    public RoundBoard(int d, int k) {
        super (d, d, k);
        inactiveCells = new boolean[d][d];
    }

    private boolean fourStreak(Move move) {
        int row = move.getRow();
        int col = move.getColumn();
        if (checkAllDir(row, col)) {
            markInactive(row, col);
            return true;
        }
        return false;
    }

    private void markInactive(int row, int col) {
        markHInactive(row, col);
        markVInactive(row, col);
        markMDInactive(row, col);
        markSDInactive(row, col);
    }

    private void markHInactive(int row, int col) {
        int k = 0;
        while (inCircle(row, col + k) && cells[row][col + k] == turn) {
            inactiveCells[row][col + k] = true;
            k++;
        }
        k = 0;
        while (inCircle(row, col - k) && cells[row][col - k] == turn) {
            inactiveCells[row][col - k] = true;
            k++;
        }
    }

    private void markVInactive(int row, int col) {
        int k = 0;
        while (inCircle(row + k, col) && cells[row + k][col] == turn) {
            inactiveCells[row + k][col] = true;
            k++;
        }
        k = 0;
        while (inCircle(row - k, col) && cells[row - k][col] == turn) {
            inactiveCells[row - k][col] = true;
            k++;
        }
    }

    private void markMDInactive(int row, int col) {
        int k = 0;
        while (inCircle(row + k, col + k) && cells[row + k][col + k] == turn) {
            inactiveCells[row + k][col + k] = true;
            k++;
        }
        k = 0;
        while (inCircle(row - k, col - k) && cells[row - k][col - k] == turn) {
            inactiveCells[row - k][col - k] = true;
            k++;
        }
    }

    private void markSDInactive(int row, int col) {
        int k = 0;
        while (inCircle(row - k, col + k) && cells[row - k][col + k] == turn) {
            inactiveCells[row - k][col + k] = true;
            k++;
        }
        k = 0;
        while (inCircle(row + k, col - k) && cells[row + k][col - k] == turn) {
            inactiveCells[row + k][col - k] = true;
            k++;
        }
    }

    private boolean checkAllDir(int row, int col) {
        return checkVertical(row, col) ||
                checkHorizontal(row, col) ||
                checkMainDiag(row, col) ||
                checkSideDiag(row, col);
    }

    private boolean checkVertical(int row, int col) {
        int streak = 0;
        int k = 0;
        while (inCircle(row + k, col) && cells[row + k][col] == turn &&
                !inactiveCells[row + k][col]) {
            k++;
        }
        streak += k;
        k = 0;
        while (inCircle(row + k, col) && cells[row + k][col] == turn &&
                !inactiveCells[row + k][col]) {
            k--;
        }
        return streak - k - 1 >= 4;
    }

    private boolean checkHorizontal(int row, int col) {
        int streak = 0;
        int k = 0;
        while (inCircle(row, col + k) && cells[row][col + k] == turn &&
                !inactiveCells[row][col + k]) {
            k++;
        }
        streak += k;
        k = 0;
        while (inCircle(row, col + k) && cells[row][col + k] == turn &&
                !inactiveCells[row][col + k]) {
            k--;
        }
        return streak - k - 1 >= 4;
    }

    private boolean checkMainDiag(int row, int col) {
        int streak = 0;
        int k = 0;
        while (inCircle(row + k, col + k) && cells[row + k][col + k] == turn &&
                !inactiveCells[row + k][col + k]) {
            k++;
        }
        streak += k;
        k = 0;
        while (inCircle(row + k, col + k) && cells[row + k][col + k] == turn &&
                !inactiveCells[row + k][col + k]) {
            k--;
        }
        return streak - k - 1 >= 4;
    }

    private boolean checkSideDiag(int row, int col) {
        int streak = 0;
        int k = 0;
        while (inCircle(row - k, col + k) && cells[row - k][col + k] == turn &&
                !inactiveCells[row - k][col + k]) {
            k++;
        }
        streak += k;
        k = 0;
        while (inCircle(row - k, col + k) && cells[row - k][col + k] == turn &&
                !inactiveCells[row - k][col + k]) {
            k--;
        }
        return streak - k - 1 >= 4;
    }

    private boolean inCircle(int r, int c) {
        double centerX = (height * 1d - 1) / 2;
        double centerY = (height * 1d - 1) / 2;
        double dist = (r - centerX) * (r - centerX) + (c - centerY) * (c - centerY);
        return dist <= height * 1d * height / 4;
    }

    @Override
    public boolean isValid(final Move move) {
        int row = move.getRow();
        int col = move.getColumn();
        return inCircle(row, col) && cells[row][col] == Cell.E;
    }

    @Override
    protected int countFreeCells(int height, int width) {
        int freeCells = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (inCircle(i, j)) {
                    freeCells++;
                }
            }
        }
        return freeCells;
    }

    @Override
    protected void updateTurn(Move move) {
        if (!fourStreak(move)) {
            turn = turn == Cell.X ? Cell.O : Cell.X;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (inCircle(i, j)) {
                    sb.append(SYMBOLS.get(cells[i][j]));
                } else {
                    sb.append(" ");
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void clear() {
        super.clear();
        for (int i = 0; i < height; i++) {
            Arrays.fill(inactiveCells[i], false);
        }
    }
}
