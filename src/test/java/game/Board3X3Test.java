package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

/**
 * Test class for Board_3X3.
 * This class tests the functionality of the Board_3X3 class,
 * including initial board state, move validity, win conditions, draw conditions, and board value copying.
 */
public class Board3X3Test {

    private Board_3x3 board;
    private Player player1, player2;
    private GameIO newIo;

    @BeforeEach
    public void setUp() {
        newIo = new GameIOImpl();
        player1 = new HumanPlayer(newIo, "Player1", 'X');
        player2 = new HumanPlayer(newIo, "Player2", 'O');
        board = new Board_3x3();
    }


    @Test
    public void initialState_onCreation_isCreated() {
        assertEquals(BoardState.CREATED, board.getBoardState());
        for (char[] row : board.getBoardValues()) {
            for (char cell : row) {
                assertEquals(' ', cell);
            }
        }
    }
    @ParameterizedTest
    @MethodSource("invalidMoveProvider")
    public void addMove_invalidInputs_returnsFalse(int x, int y) {
        board.addMove(player1, 0, 0); //make a valid move first
        assertFalse(board.addMove(player1, x, y));
    }

    private static Stream<Arguments> invalidMoveProvider() {
        return Stream.of(
                Arguments.of(3, 3),  //outside bounds
                Arguments.of(-1, -1), //negative indices
                Arguments.of(0, 0)   //already occupied cell

        );
    }

    @Test
    public void addMove_validInput_updatesBoard() {
        assertTrue(board.addMove(player1, 0, 0));
        assertEquals(BoardState.RUNNING, board.getBoardState());
        assertEquals('X', board.getBoardValues()[0][0]);

        assertFalse(board.addMove(player1, 0, 0));
        assertFalse(board.addMove(player1, -1, -1));
        assertFalse(board.addMove(player1, 3, 3));
    }
    @ParameterizedTest
    @MethodSource("winningBoardProvider")
    public void addMove_winningBoard_updatesStateToWin(char[][] winningBoard, char winningMark) {
        //simulate the winning moves
        for (int i = 0; i < winningBoard.length; i++) {
            for (int j = 0; j < winningBoard[i].length; j++) {
                if (winningBoard[i][j] == winningMark) {
                    board.addMove(new HumanPlayer(newIo, "Player", winningMark), i, j);
                }
            }
        }

        assertEquals(BoardState.DONE, board.getBoardState());
        assertEquals(PlayerState.WIN, board.getPlayerState(new HumanPlayer(newIo, "Player", winningMark)));
    }
    private static Stream<Arguments> winningBoardProvider() {
        return Stream.of(
                Arguments.of(new char[][]{{'X', 'X', 'X'}, {' ', ' ', ' '}, {' ', ' ', ' '}}, 'X'), // Row 1
                Arguments.of(new char[][]{{' ', ' ', ' '}, {'X', 'X', 'X'}, {' ', ' ', ' '}}, 'X'), // Row 2
                Arguments.of(new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {'X', 'X', 'X'}}, 'X'), // Row 3
                Arguments.of(new char[][]{{'X', ' ', ' '}, {'X', ' ', ' '}, {'X', ' ', ' '}}, 'X'), // Column 1
                Arguments.of(new char[][]{{' ', 'X', ' '}, {' ', 'X', ' '}, {' ', 'X', ' '}}, 'X'), // Column 2
                Arguments.of(new char[][]{{' ', ' ', 'X'}, {' ', ' ', 'X'}, {' ', ' ', 'X'}}, 'X'), // Column 3
                Arguments.of(new char[][]{{'X', ' ', ' '}, {' ', 'X', ' '}, {' ', ' ', 'X'}}, 'X'), // Diagonal 1
                Arguments.of(new char[][]{{' ', ' ', 'X'}, {' ', 'X', ' '}, {'X', ' ', ' '}}, 'X')  // Diagonal 2
        );
    }

    @Test
    public void addMove_fullBoardWithoutWinner_updatesStateToDraw() {
        // Play out a full game that ends in a draw
        board.addMove(player1, 0, 0); // X
        board.addMove(player2, 0, 1); // O
        board.addMove(player1, 0, 2); // X
        board.addMove(player2, 1, 0); // O
        board.addMove(player1, 1, 1); // X
        board.addMove(player2, 2, 0); // O
        board.addMove(player1, 1, 2); // X
        board.addMove(player2, 2, 2); // O
        board.addMove(player1, 2, 1); // X

        assertEquals(BoardState.DONE, board.getBoardState());
        assertEquals(PlayerState.DRAW, board.getPlayerState(player1));
        assertEquals(PlayerState.DRAW, board.getPlayerState(player2));
    }

    @Test
    public void getBoardValues_afterMove_returnsCopy() {
        board.addMove(player1, 0, 0);
        char[][] boardCopy = board.getBoardValues();
        assertNotSame(board.getBoardValues(), boardCopy);
        boardCopy[0][0] = 'O';
        assertEquals('X', board.getBoardValues()[0][0]);
    }
}