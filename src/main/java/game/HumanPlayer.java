package game;

import common.Result;

class HumanPlayer implements Player {
    private GameIO io;
    private String name;
    private char mark;

    HumanPlayer(GameIO io, String name, char mark) {
        if (io == null) {
            throw new IllegalArgumentException("GameIO cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

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
        // Guard against null board
        if (board == null) {
            return false;
        }

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
