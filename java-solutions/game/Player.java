package game;

public interface Player {
    Move move(ReadOnlyPosition position, Cell cell);
}
