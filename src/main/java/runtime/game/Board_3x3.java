package runtime.game;

class Board_3x3 implements Board{
    Board_3x3(){}
    @Override
    public boolean addMove(int x, int y) {
        return false;
    }

    @Override
    public char[][] getBoardValues() {
        return new char[0][];
    }

    @Override
    public BoardState getBoardState() {
        return null;
    }

    @Override
    public PlayerState getPlayerState() {
        return null;
    }
}
