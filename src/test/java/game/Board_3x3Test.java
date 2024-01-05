package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Board_3x3Test {

    private Board_3x3 board;
    private Player player1, player2;

    @BeforeEach
    public void setUp() {
        player1 = new HumanPlayer(null, "Player1", 'X');
        player2 = new HumanPlayer(null, "Player2", 'O');
        board = new Board_3x3(player1, player2);
    }

    @Test
    public void testInitialBoardState() {
        assertEquals(BoardState.CREATED, board.getBoardState());
        for (char[] row : board.getBoardValues()) {
            for (char cell : row) {
                assertEquals(' ', cell);
            }
        }
    }

    @Test
    public void testBoardStateTransition() {
        board.addMove(0, 0); // First move, board should be in RUNNING state
        assertEquals(BoardState.RUNNING, board.getBoardState());
    }

    @Test
    public void testAddMove() {
        assertTrue(board.addMove(0, 0));
        assertEquals('X', board.getBoardValues()[0][0]);
    }

    @Test
    public void testInvalidMove() {
        assertFalse(board.addMove(3, 3)); // Outside bounds
        board.addMove(0, 0);
        assertFalse(board.addMove(0, 0)); // Occupied cell
    }

    @Test
    public void testWinCondition() {
        board.addMove(0, 0); // X
        board.addMove(1, 0); // O
        board.addMove(0, 1); // X
        board.addMove(1, 1); // O
        board.addMove(0, 2); // X - winning move
        assertEquals(PlayerState.WIN, board.getPlayerState());
        assertEquals(BoardState.DONE, board.getBoardState());
    }

    @Test
    public void testDrawCondition() {
        // Test a draw condition (board full, no winner)
        board.addMove(0, 0); // X
        board.addMove(0, 1); // O
        board.addMove(0, 2); // X
        board.addMove(1, 1); // O
        board.addMove(1, 0); // X
        board.addMove(1, 2); // O
        board.addMove(2, 1); // X
        board.addMove(2, 0); // O
        board.addMove(2, 2); // X - last move, resulting in a draw

        assertEquals(PlayerState.DRAW, board.getPlayerState());
    }

}
