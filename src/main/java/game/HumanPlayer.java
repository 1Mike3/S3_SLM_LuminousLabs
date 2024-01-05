package game;

import common.Result;

class HumanPlayer implements Player {
    private GameIOImpl io;
    private String name;
    private char mark;

    // Constructor
    HumanPlayer(GameIOImpl io, String name, char mark) {
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
    public Result<Boolean, String> makeMove(Board board) {
        io.putString("Player " + name + " (" + mark + "), make your move. Enter a number between 1 and 9:");

        Result<Integer, String> moveResult = io.getInt();
        if (moveResult.isErr()) {
            return Result.err(moveResult.getErr());
        }

        int move = moveResult.unwrap();

        // Convert the move (1-9) to board coordinates (0-2, 0-2)
        int x = (move - 1) / 3; // Row
        int y = (move - 1) % 3; // Column

        if (board.addMove(x, y)) {
            return Result.ok(true);
        } else {
            return Result.err("Invalid move. Please try again.");
        }
    }
}
