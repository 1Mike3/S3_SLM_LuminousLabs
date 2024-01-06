package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

/**
 * Implements a 3x3 tic-tac-toe board.
 */
class Board_3x3 implements Board {
    private char[][] board;
    private BoardState boardState;
    private Map<Character, PlayerState> playerStates; // Map to track state by player's mark
    /**
     * Constructor for Board_3x3. Initializes the 3x3 board with empty spaces.
     */
    Board_3x3() {
        board = new char[3][3];
        playerStates = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' '; // Initialize with empty spaces
            }
        }
        boardState = BoardState.CREATED;
    }

    /**
     * Adds a move to the board.
     * @param player The player making the move.
     * @param x The x coordinate of the move.
     * @param y The y coordinate of the move.
     * @return True if the move was successfully added, false otherwise.
     */
    @Override
    public boolean addMove(Player player, int x, int y) {
        if (x < 0 || x >= 3 || y < 0 || y >= 3 || board[x][y] != ' ') {
            return false; // Invalid move
        }

        char mark = player.getMark();
        board[x][y] = mark;

        // Initialize player state if not already present
        playerStates.putIfAbsent(mark, PlayerState.UNDEFINED);

        updateBoardAndPlayerState(x, y, mark);
        return true;
    }

    /**
     * Returns a copy of the board values.
     * @return A copy of the board values.
     */
    @Override
    public char[][] getBoardValues() {
        char[][] copy = new char[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board[i].length);
        }
        return copy;
    }

    /**
     * Returns the current state of the board.
     * @return The current state of the board.
     */
    @Override
    public BoardState getBoardState() {
        return boardState;
    }

    /**
     * Returns the state of the player.
     * @param player The player to get the state of.
     * @return The state of the player.
     */
    @Override
    public PlayerState getPlayerState(Player player) {
        return playerStates.getOrDefault(player.getMark(), PlayerState.UNDEFINED);
    }


    /**
     * Method to check if the game is over.
     * @return True if the game is over, false otherwise.
     */
    private void updateBoardAndPlayerState(int x, int y, char mark) {
        if (checkWin(x, y, mark)) {
            playerStates.put(mark, PlayerState.WIN);
            boardState = BoardState.DONE;
        } else if (checkDraw()) {
            // Update all players to DRAW state
            for (char key : playerStates.keySet()) {
                playerStates.put(key, PlayerState.DRAW);
            }
            boardState = BoardState.DONE;
        } else {
            boardState = BoardState.RUNNING;
        }
    }

    /**
     * Method to check if the game is a draw.
     * @return True if the game is a draw, false otherwise.
     */
    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true; //no empty spaces = draw
    }

    /**
     * Method to check if the game is won.
     * @param x The x coordinate of the move.
     * @param y The y coordinate of the move.
     * @param mark The mark of the player making the move.
     * @return True if the game is won, false otherwise.
     */
    private boolean checkWin(int x, int y, char mark) {
        //check row and column
        boolean rowWin = true;
        boolean colWin = true;
        for (int i = 0; i < 3; i++) {
            if (board[x][i] != mark) rowWin = false;
            if (board[i][y] != mark) colWin = false;
        }
        if (rowWin || colWin) return true;

        //check diagonals
        boolean diagWin = true;
        boolean antiDiagWin = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] != mark) diagWin = false;
            if (board[i][2 - i] != mark) antiDiagWin = false;
        }
        return diagWin || antiDiagWin;
    }


    /**
     * Prints the current state of the board to the console.
     */
    public void printBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("|" + board[i][j]);
            }
            System.out.println("|");
            if (i < 2) {
                System.out.println("-----------");
            }
        }
    }

}
