package game;

interface Board {
    boolean addMove(Player player, int x, int y);
    char[][] getBoardValues();
    BoardState getBoardState();
    PlayerState getPlayerState(Player player);
    public void printBoard();
}