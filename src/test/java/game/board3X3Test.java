package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class board3X3Test {

    private Board_3x3 board;
    private Player player1, player2;

    @BeforeEach
    public void setUp() {
        player1 = new HumanPlayer(null, "Player1", 'X');
        player2 = new HumanPlayer(null, "Player2", 'O');
        board = new Board_3x3(); // updated instantiation
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
        board.addMove(player1, 0, 0); // first move board should be in RUNNING state
        assertEquals(BoardState.RUNNING, board.getBoardState());
    }

    @Test
    public void testAddMove() {
        assertTrue(board.addMove(player1, 0, 0));
        assertEquals('X', board.getBoardValues()[0][0]);
    }

    @Test
    public void testInvalidMove() {
        assertFalse(board.addMove(player1, 3, 3)); // outside bounds
        board.addMove(player1, 0, 0);
        assertFalse(board.addMove(player1, 0, 0)); // occupied cell
    }

    @Test
    public void testWinCondition() {
        board.addMove(player1, 0, 0); // X
        board.addMove(player2, 1, 0); // O
        board.addMove(player1, 0, 1); // X
        board.addMove(player2, 1, 1); // O
        board.addMove(player1, 0, 2); // X - winning move

        assertEquals(BoardState.DONE, board.getBoardState());
    }

    @Test
    public void testDrawCondition() {
        // Fill the board in a way that results in a draw
        board.addMove(player1, 0, 0); // X
        board.addMove(player2, 1, 0); // O
        board.addMove(player1, 2, 0); // X
        board.addMove(player2, 0, 1); // O
        board.addMove(player1, 0, 2); // X
        board.addMove(player2, 1, 1); // O
        board.addMove(player1, 1, 2); // X
        board.addMove(player2, 2, 1); // O
        board.addMove(player1, 2, 2); // X - Last move, resulting in a draw

        assertEquals(BoardState.DONE, board.getBoardState());
    }

}
