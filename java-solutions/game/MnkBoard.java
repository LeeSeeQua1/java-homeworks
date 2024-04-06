package game;

public class MnkBoard extends AbstractBoard {

    public MnkBoard(int n, int m, int k) {
        super(n, m, k);
        if (k < 0) {
            throw new IllegalArgumentException("Win streak cannot be negative");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(" |");
        for (int c = 0; c < width; c++) {
            sb.append(c);
        }
        for (int r = 0; r < height; r++) {
            sb.append("\n");
            sb.append(r);
            sb.append('|');
            for (int c = 0; c < width; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }

    @Override
    protected int countFreeCells(int height, int width) {
        return height * width;
    }
}
