package runtime.game;

public interface Board {
    boolean addMove(int x, int y);
    char[][] getBoardValues();
    BoardState getBoardState();
    PlayerState getPlayerState();
}
