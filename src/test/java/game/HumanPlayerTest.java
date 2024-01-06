package game;

import common.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    void testGetMark() {
        assertEquals('X', player.getMark());
    }

    @Test
    void testGetName() {
        assertEquals("TestPlayer", player.getName());
    }

    @Test
    void testMakeMoveValid() {
        mockIo.setNextInt(5); //simulate user entering 5
        assertTrue(player.makeMove(board));
    }

    @Test
    void testMakeMoveInvalid() {
        mockIo.setNextInt(12); //simulate user entering an invalid number
        assertFalse(player.makeMove(board));
    }

    @Test
    void testMakeMoveWithErrorInInput() {
        mockIo.setNextIntError("Input Error"); //simulate an error in input
        assertFalse(player.makeMove(board));
    }

}

class MockGameIO implements GameIO {
    private Integer nextInt;
    private String nextIntError;

    void setNextInt(int value) {
        nextInt = value;
    }

    void setNextIntError(String error) {
        nextIntError = error;
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
