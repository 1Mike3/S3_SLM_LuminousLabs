package game;

import common.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The `AppTest` class contains unit tests for the `App` class, which is responsible for managing game sessions.
 */
public class AppTest {

    /**
     * Tests the `playAnotherGame` method with user input indicating a desire to play again.
     * It should return true.
     */
    @Test
    public void playAnotherGame_userWantsToPlayAgain_returnTrue(){
        GameIO io = mock(GameIO.class);

        when(io.putString(any(String.class))).thenReturn(Result.ok(true));
        when(io.getString()).thenReturn(Result.ok("X"));
        when(io.getString()).thenReturn(Result.ok("X"));
        when(io.getString()).thenReturn(Result.ok("y"));

        App app = new App();
        assertTrue(app.playAnotherGame(io));
    }

    /**
     * Tests the `playAnotherGame` method with user input indicating no desire to play again.
     * It should return false.
     */
    @Test
    public void playAnotherGame_userDoesNotWantsToPlayAgain_returnTrue(){
        GameIO io = mock(GameIO.class);

        when(io.putString(any(String.class))).thenReturn(Result.ok(true));
        when(io.getString()).thenReturn(Result.ok("X"));
        when(io.getString()).thenReturn(Result.ok("X"));
        when(io.getString()).thenReturn(Result.ok("n"));

        App app = new App();
        assertFalse(app.playAnotherGame(io));
    }

    /**
     * Tests the `playAnotherGame` method with invalid user input.
     * It should return false as the input is considered invalid.
     */
    @Test
    public void playAnotherGame_userProvidesInvalidInput_returnFalse(){
        GameIO io = mock(GameIO.class);

        when(io.putString(any(String.class))).thenReturn(Result.ok(true));
        when(io.getString()).thenReturn(Result.ok("X"));
        when(io.getString()).thenReturn(Result.ok("X"));
        when(io.getString()).thenReturn(Result.ok("X"));

        App app = new App();
        assertFalse(app.playAnotherGame(io));
    }

    /**
     * Tests the `isYes` method with valid input strings.
     * It should return true for valid input.
     *
     * @param str The input string to test.
     */
    @ParameterizedTest
    @ValueSource(strings = {"y", "Y", "yes", "YeS"})
    public void isYes_validInput_returnsTrue(String str){
        App app = new App();
        assertTrue(app.isYes(str));
    }

    /**
     * Tests the `isYes` method with invalid input strings.
     * It should return false for invalid input.
     *
     * @param str The input string to test.
     */
    @ParameterizedTest
    @ValueSource(strings = {"yess", "no", "x"})
    public void isYes_invalidInput_returnsFalse(String str){
        App app = new App();
        assertFalse(app.isYes(str));
    }

    /**
     * Tests the `isNo` method with valid input strings.
     * It should return true for valid input.
     *
     * @param str The input string to test.
     */
    @ParameterizedTest
    @ValueSource(strings = {"n", "N", "no", "nO"})
    public void isNo_validInput_returnsTrue(String str){
        App app = new App();
        assertTrue(app.isNo(str));
    }

    /**
     * Tests the `isNo` method with invalid input strings.
     * It should return false for invalid input.
     *
     * @param str The input string to test.
     */
    @ParameterizedTest
    @ValueSource(strings = {"y", "nooo", "x"})
    public void isNo_invalidInput_returnsTrue(String str){
        App app = new App();
        assertFalse(app.isNo(str));
    }
}
