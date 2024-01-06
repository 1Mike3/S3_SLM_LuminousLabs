package game;

import common.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for HumanPlayer.
 * Tests the functionality of HumanPlayer methods including making moves.
 */
class HumanPlayerTest {

    private HumanPlayer player;
    private MockGameIO mockIo;
    private Board board;

    @BeforeEach
    void setUp() {
        mockIo = new MockGameIO();
        player = new HumanPlayer(mockIo, "TestPlayer", 'X');
        board = new Board_3x3();
    }

    @Test
    void getMark_returnsPlayerMark() {
        assertEquals('X', player.getMark());
    }

    @Test
    void getName_returnsPlayerName() {
        assertEquals("TestPlayer", player.getName());
    }

    @Test
    void makeMove_validInput_returnsTrue() {
        mockIo.setNextInt(5); //simulate user entering 5
        assertTrue(player.makeMove(board));
    }

    @Test
    void makeMove_invalidInput_returnsFalse() {
        mockIo.setNextInt(12); //simulate user entering an invalid number
        assertFalse(player.makeMove(board));
    }

    @Test
    void makeMove_inputError_returnsFalse() {
        mockIo.setNextIntError(); //simulate an error in input
        assertFalse(player.makeMove(board));
    }

}

class MockGameIO implements GameIO {
    private Integer nextInt;
    private String nextIntError;

    void setNextInt(int value) {
        nextInt = value;
    }

    void setNextIntError() {
        nextIntError = "Input Error";
    }

    @Override
    public Result<Integer, String> getInt() {
        if (nextIntError != null) {
            return Result.err(nextIntError);
        }
        return Result.ok(nextInt);
    }

    @Override
    public Result<String, String> getString() {
        return null;
    }

    @Override
    public Result<Boolean, String> putString(String string) {
        return null;
    }
}
