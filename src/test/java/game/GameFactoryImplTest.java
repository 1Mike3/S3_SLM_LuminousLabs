package game;

import common.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class GameFactoryImplTest {
    @Test
    void createGame_valid_input() {
        GameFactoryImpl factory = spy(new GameFactoryImpl());
        Player player_1 = mock(Player.class);
        Player player_2 = mock(Player.class);
        GameIO io = mock(GameIO.class);

        doReturn(Result.ok(player_1))
                .doReturn(Result.ok(player_2))
                .when(factory)
                .createHumanPlayer(io);

        when(player_1.getName()).thenReturn("X");
        when(player_2.getName()).thenReturn("O");

        when(player_1.getMark()).thenReturn('X');
        when(player_2.getMark()).thenReturn('O');

        var r = factory.createGame(io, GameType.TICTACTOE_1V1);
        assertTrue(r.isOk());
    }

    @Test
    void createGame_playersChooseSameMark_error() {
        GameFactoryImpl factory = spy(new GameFactoryImpl());
        Player player_1 = mock(Player.class);
        Player player_2 = mock(Player.class);
        GameIO io = mock(GameIO.class);

        doReturn(Result.ok(player_1))
                .doReturn(Result.ok(player_2))
                .when(factory)
                .createHumanPlayer(io);

        when(player_1.getName()).thenReturn("X");
        when(player_2.getName()).thenReturn("O");

        when(player_1.getMark()).thenReturn('X');
        when(player_2.getMark()).thenReturn('X');

        var r = factory.createGame(io, GameType.TICTACTOE_1V1);
        assertTrue(r.isErr());
    }

    @Test
    void createGame_playersChooseSameName_error() {
        GameFactoryImpl factory = spy(new GameFactoryImpl());
        Player player_1 = mock(Player.class);
        Player player_2 = mock(Player.class);
        GameIO io = mock(GameIO.class);

        doReturn(Result.ok(player_1))
                .doReturn(Result.ok(player_2))
                .when(factory)
                .createHumanPlayer(io);

        when(player_1.getName()).thenReturn("x");
        when(player_2.getName()).thenReturn("X");

        var r = factory.createGame(io, GameType.TICTACTOE_1V1);
        assertTrue(r.isErr());
    }

    @Test
    void createGame_invalid_input() {
        GameFactoryImpl factory = spy(new GameFactoryImpl());
        Player player = mock(Player.class);
        GameIO io = mock(GameIO.class);

        doReturn(Result.err("some error"))
                .when(factory)
                .createHumanPlayer(any(GameIO.class));

        var r = factory.createGame(io, GameType.TICTACTOE_1V1);
        assertTrue(r.isErr());
    }

    @ParameterizedTest
    @ValueSource(strings = {"jeff", "Jeff", "PussyDestroyer69"})
    void isValidName_ValidNames(String name) {
        GameFactoryImpl factory = new GameFactoryImpl();
        boolean res = factory.isValidName(name);
        assertTrue(res);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "jeff$", "someverylongstringthatiswaytolong"})
    void isValidName_InvalidNames(String name) {
        GameFactoryImpl factory = new GameFactoryImpl();
        boolean res = factory.isValidName(name);
        assertFalse(res);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "Z"})
    void isValidChar_ValidInput(String str) {
        GameFactoryImpl factory = new GameFactoryImpl();
        boolean res = factory.isValidChar(str);
        assertTrue(res);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "z", "9", "", "XX"})
    void isValidChar_InvalidInput(String str) {
        GameFactoryImpl factory = new GameFactoryImpl();
        boolean res = factory.isValidChar(str);
        assertFalse(res);
    }

    @Test
    void getPlayerName_ValidUserInput() {
        GameIO io = mock(GameIO.class);
        when(io.putString(any(String.class))).thenReturn(Result.ok(true));
        when(io.getString()).thenReturn(Result.ok("Jeff"));

        GameFactoryImpl factory = new GameFactoryImpl();
        var r = factory.getPlayerName(io);
        assertTrue(r.isOk());
    }

    @Test
    void getPlayerName_InvalidUserInput() {
        GameIO io = mock(GameIO.class);
        when(io.putString(any(String.class))).thenReturn(Result.ok(true));
        when(io.getString()).thenReturn(Result.ok("$$$Jeff$$$"));

        GameFactoryImpl factory = new GameFactoryImpl();
        var r = factory.getPlayerName(io);
        assertTrue(r.isErr());
    }

    @Test
    void getPlayerChar_ValidUserInput() {
        GameIO io = mock(GameIO.class);
        when(io.putString(any(String.class))).thenReturn(Result.ok(true));
        when(io.getString()).thenReturn(Result.ok("X"));

        GameFactoryImpl factory = new GameFactoryImpl();
        var r = factory.getPlayerChar(io);
        assertTrue(r.isOk());
    }

    @Test
    void getPlayerChar_InvalidUserInput() {
        GameIO io = mock(GameIO.class);
        when(io.putString(any(String.class))).thenReturn(Result.ok(true));
        when(io.getString()).thenReturn(Result.ok("XX"));

        GameFactoryImpl factory = new GameFactoryImpl();
        var r = factory.getPlayerChar(io);
        assertTrue(r.isErr());
    }

    @Test
    void createHumanPlayer_ValidInput() {
        GameIO io = mock(GameIO.class);
        when(io.putString(any(String.class))).thenReturn(Result.ok(true));
        when(io.getString()).thenReturn(
                Result.ok("Jeff$"),
                Result.ok("Jeff"),
                Result.ok("XX"),
                Result.ok("X"));

        GameFactoryImpl factory = new GameFactoryImpl();
        var r = factory.createHumanPlayer(io);
        assertTrue(r.isOk());
    }

    @Test
    void createHumanPlayer_InvalidName() {
        GameIO io = mock(GameIO.class);
        when(io.putString(any(String.class))).thenReturn(Result.ok(true));
        when(io.getString()).thenReturn(
                Result.ok("Jeff$"),
                Result.ok("Jeff$"),
                Result.ok("Jeff$"));

        GameFactoryImpl factory = new GameFactoryImpl();
        var r = factory.createHumanPlayer(io);
        assertTrue(r.isErr());
    }

    @Test
    void createHumanPlayer_InvalidChar() {
        GameIO io = mock(GameIO.class);
        when(io.putString(any(String.class))).thenReturn(Result.ok(true));
        when(io.getString()).thenReturn(
                Result.ok("Jeff"),
                Result.ok("XX"),
                Result.ok("XX"),
                Result.ok("XX"));

        GameFactoryImpl factory = new GameFactoryImpl();
        var r = factory.createHumanPlayer(io);
        assertTrue(r.isErr());
    }

    @Test
    void create_TICTACTOE_1V1_ValidUserInput() {
        GameIO io = mock(GameIO.class);
        when(io.putString(any(String.class))).thenReturn(Result.ok(true));
        when(io.getString()).thenReturn(
                Result.ok("Jeff1"),
                Result.ok("X"),
                Result.ok("Heff2"),
                Result.ok("O"));

        GameFactoryImpl factory = new GameFactoryImpl();
        var r = factory.createHumanPlayer(io);
        assertTrue(r.isOk());
    }

    @Test
    void create_TICTACTOE_1V1_InValidUserInput() {
        GameIO io = mock(GameIO.class);
        when(io.putString(any(String.class))).thenReturn(Result.ok(true));
        when(io.getString()).thenReturn(Result.ok("$"));

        GameFactoryImpl factory = new GameFactoryImpl();
        var r = factory.createHumanPlayer(io);
        assertTrue(r.isErr());
    }
}