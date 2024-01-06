package game;

import common.Result;

/**
 * Represents a human player in the game.
 */
class HumanPlayer implements Player {
    private GameIO io;
    private String name;
    private char mark;

    /**
     * Creates a new human player.
     * @param io The GameIO object to use for input/output.
     * @param name The name of the player.
     * @param mark The mark to use for the player.
     */
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

    /**
     * Gets the mark of the player.
     *
     * @return The character representing the player's mark.
     */
    @Override
    public char getMark() {
        return mark;
    }
    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Makes a move on the board.
     *
     * @param board The board to make a move on.
     * @return True if the move was successfully made, false otherwise.
     */
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
