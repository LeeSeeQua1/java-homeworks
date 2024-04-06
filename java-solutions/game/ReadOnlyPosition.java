package game;

public class ReadOnlyPosition implements Position {
    private final Position position;

    public ReadOnlyPosition (Position pos) {
        position = pos;
    }

    public boolean isValid(Move move) {
        return position.isValid(move);
    }

    @Override
    public String toString(){
        return position.toString();
    }
}
