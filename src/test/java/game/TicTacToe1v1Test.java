package game;
import common.Result;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 * Test class for TicTacToe_1v1.
 * This class contains unit tests to verify the correctness of the TicTacToe_1v1 class,
 */
public class TicTacToe1v1Test {


    @Test
    void constructor_WithValidParameters_correct() {
        Board board = new Board_3x3();
        GameIO io = new GameIOImpl();
        Player player1 = new HumanPlayer(io, "Max", 'X');
        Player player2 = new HumanPlayer(io, "Tom", 'O');

        TicTacToe_1v1 game = new TicTacToe_1v1(board, io, Arrays.asList(player1, player2));
        assertNotNull(game);
    }

    @Test
    void constructor_WithNullBoard_throwsError() {
        GameIO io = new GameIOImpl();
        Player player1 = new HumanPlayer(io, "Max", 'X');
        Player player2 = new HumanPlayer(io, "Tom", 'O');

        assertThrows(IllegalArgumentException.class, () -> {
            new TicTacToe_1v1(null, io, Arrays.asList(player1, player2));
        });
    }

    @Test
    void constructor_WithNullGameIO_throwsError() {
        Board board = new Board_3x3();
        GameIO io = new GameIOImpl();
        Player player1 = new HumanPlayer(io, "Max", 'X');
        Player player2 = new HumanPlayer(io, "Tom", 'O');

        assertThrows(IllegalArgumentException.class, () -> {
            new TicTacToe_1v1(board, null, Arrays.asList(player1, player2));
        });
    }

    @Test
    void constructor_WithNullPlayersList_throwsError() {
        Board board = new Board_3x3();
        GameIO io = new GameIOImpl();

        assertThrows(IllegalArgumentException.class, () -> {
            new TicTacToe_1v1(board, io, null);
        });
    }

    @Test
    void testConstructorWithIncorrectNumberOfPlayers_throwsError() {
        Board board = new Board_3x3();
        GameIO io = new GameIOImpl();
        Player player1 = new HumanPlayer(io, "Max", 'X');

        assertThrows(IllegalArgumentException.class, () -> {
            new TicTacToe_1v1(board, io, Arrays.asList(player1));
        });
    }

    @Test
    public void getGameState_getValues_correctValues() {
        Board board = new Board_3x3();
        GameIO io = new GameIOImpl();
        Player player1 = new HumanPlayer(io, "Max", 'X');
        Player player2 = new HumanPlayer(io, "Tom", 'O');
        List<Player> list = Arrays.asList(player1, player2);
        TicTacToe_1v1 game = new TicTacToe_1v1(board,io,list);

        assertEquals(GameState.CREATED, game.getGameState());

        game.setGameState(GameState.RUNNING);
        assertEquals(GameState.RUNNING, game.getGameState());

        game.setGameState(GameState.DONE);
        assertEquals(GameState.DONE, game.getGameState());

        game.setGameState(GameState.ABORTED);
        assertEquals(GameState.ABORTED, game.getGameState());
    }

    @Test
    public void printWelcomeMessage_print_resultCorrect() {

        Board mockBoard = mock(Board.class);
        GameIO mockIO = mock(GameIO.class);
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);


        when(player1.getName()).thenReturn("Alice");
        when(player1.getMark()).thenReturn('X');
        when(player2.getName()).thenReturn("Bob");
        when(player2.getMark()).thenReturn('O');

        List<Player> players = List.of(player1, player2);


        TicTacToe_1v1 game = new TicTacToe_1v1(mockBoard, mockIO, players);


        game.printWelcomeMessage();


        String expectedMessage = String.format("""
                        \t### Game Started ###

                        GameType: \t\t TickTacToe - PvP
                                    
                        \t\t # Rules #
                           1. The game is played on a grid that's 3 squares by 3 squares.
                           2. Players take turns putting their marks in empty squares.
                           3. The first player to get 3 of their marks in a row\s
                              (up, down, across, or diagonally) is the winner.
                           4. When all 9 squares are full, the game is over.\s
                              If no player has 3 marks in a row, the game ends in a draw
                           5. You make a move by entering a Number between 1 and 9
                              The Number corresponds to the board position as illustrated below
                              1 | 2 | 3
                              ---------
                              4 | 5 | 6
                              ---------
                              7 | 8 | 9
                                    
                        \t\t # Players and Symbols #
                        P1 = Alice \t X
                        P2 = Bob \t O
                                        
                        """);
        Mockito.verify(mockIO).putString(expectedMessage);
    }


    @Test
    void PlayerMakeMove_ValidMoveFirstAttempt_correctMove() {
        Player mockPlayer = mock(Player.class);
        Board mockBoard = mock(Board.class);
        GameIO mockIO = mock(GameIO.class);
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);

        List<Player> mockPlayers = List.of(player1, player2);
        when(mockPlayer.makeMove(mockBoard)).thenReturn(true);

        TicTacToe_1v1 game = new TicTacToe_1v1(mockBoard,mockIO,mockPlayers);

        assertTrue(game.PlayerMakeMove(mockPlayer));
    }

    @Test
    void PlayerMakeMove_InvalidMoveInitiallyThenValid_recognizes() {
        Player mockPlayer = mock(Player.class);
        Board mockBoard = mock(Board.class);
        when(mockPlayer.makeMove(mockBoard)).thenReturn(false, true);
        GameIO mockIO = mock(GameIO.class);
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);

        List<Player> mockPlayers = List.of(player1, player2);
        when(mockPlayer.makeMove(mockBoard)).thenReturn(true);

        TicTacToe_1v1 game = new TicTacToe_1v1(mockBoard,mockIO,mockPlayers);

        assertTrue(game.PlayerMakeMove(mockPlayer));
    }

    @Test
    void PlayerMakeMove_InvalidOnlyMoves_returnsFalse() {
        Player mockPlayer = mock(Player.class);
        Board mockBoard = mock(Board.class);

        GameIO mockIO = mock(GameIO.class);
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);

        List<Player> mockPlayers = List.of(player1, player2);
        when(mockPlayer.makeMove(mockBoard)).thenReturn(true);

        when(mockPlayer.makeMove(mockBoard)).thenReturn(false, false, false, false);

        TicTacToe_1v1 game = new TicTacToe_1v1(mockBoard,mockIO,mockPlayers);

        assertFalse(game.PlayerMakeMove(mockPlayer));
    }

    @Test
    void EvaluateGameStatus_validBoard_boardStateCorrect() {
        Board mockBoard = mock(Board.class);
        Player mockPlayer = mock(Player.class);
        when(mockBoard.getBoardState()).thenReturn(BoardState.RUNNING);
        GameIO mockIO = mock(GameIO.class);
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);

        List<Player> mockPlayers = List.of(player1, player2);

        TicTacToe_1v1 game = new TicTacToe_1v1(mockBoard,mockIO,mockPlayers);

        Result<BoardState, String> result = game.evaluateGameStatus();
        assertNotNull(result.value());
        assertEquals(BoardState.RUNNING, result.value());

    }

    @Test
    void EvaluateGameStatus_InvalidBoard_Error() {
        Board mockBoard = mock(Board.class);
        Player mockPlayer = mock(Player.class);
        when(mockBoard.getBoardState()).thenReturn(null);
        GameIO mockIO = mock(GameIO.class);
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);

        List<Player> mockPlayers = List.of(player1, player2);

        TicTacToe_1v1 game = new TicTacToe_1v1(mockBoard,mockIO,mockPlayers);

        Result<BoardState, String> result = game.evaluateGameStatus();
        assertNull(result.value());
        assertNotNull(result.getErr());
        assertEquals("Error Boardstate is Null", result.getErr());
    }


    @Test
    void EndGame_PlayerWin_CorrectValues() {
        Board mockBoard = mock(Board.class);
        GameIO mockIO = mock(GameIO.class);
        Player mockPlayer1 = mock(Player.class);
        Player mockPlayer2 = mock(Player.class);
        List<Player> players = List.of(mockPlayer1, mockPlayer2);

        when(mockBoard.getPlayerState(mockPlayer1)).thenReturn(PlayerState.WIN);

        TicTacToe_1v1 game = new TicTacToe_1v1(mockBoard,mockIO,players);

        game.endGame(players, mockBoard);

        assertEquals(GameState.DONE, game.getGameState());
        verify(mockBoard).printBoard();
        verify(mockIO).putString("\n### GAME END ###");
    }

    @Test
    void EndGame_PlayerDraw_CorrectValues() {
        Board mockBoard = mock(Board.class);
        GameIO mockIO = mock(GameIO.class);
        Player mockPlayer1 = mock(Player.class);
        Player mockPlayer2 = mock(Player.class);
        List<Player> players = List.of(mockPlayer1, mockPlayer2);

        TicTacToe_1v1 game = new TicTacToe_1v1(mockBoard, mockIO, players);
        when(mockBoard.getPlayerState(mockPlayer1)).thenReturn(PlayerState.DRAW);

        game.endGame(players, mockBoard);

        assertEquals(GameState.DONE, game.getGameState());
        verify(mockBoard).printBoard();
        verify(mockIO).putString("\n### GAME END ###");
    }

}
