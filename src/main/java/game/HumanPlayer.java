package game;

import common.Result;

class HumanPlayer implements Player {
    private GameIO io;
    private String name;
    private char mark;

    // Constructor
    HumanPlayer(GameIO io, String name, char mark) {
        this.io = io;
        this.name = name;
        this.mark = mark;
    }

    @Override
    public char getMark() {
        return mark;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean makeMove(Board board) {
        io.putString("Player " + name + " (" + mark + "), make your move. Enter a number between 1 and 9:");

        Result<Integer, String> moveResult = io.getInt();
        if (moveResult.isErr()) {
            return false;
        }

        int move = moveResult.unwrap();

        if (move < 1 || move > 9) {
            return false;
        }
        int x = (move - 1) / 3;
        int y = (move - 1) % 3;

        return board.addMove(this, x, y);
    }
}
