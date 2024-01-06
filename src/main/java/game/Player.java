package game;

interface Player {
    char getMark();
    String getName();
    boolean makeMove(Board board);
}