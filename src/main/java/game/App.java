package game;

import common.Result;

import java.util.regex.Pattern;

/**
 * The `App` class represents the main application for playing a game.
 * It provides methods to start and manage game sessions with user interaction.
 */
public class App {

    /**
     * Starts the application.
     * It initializes the game input and output, creates and starts game sessions,
     * and allows the user to play multiple rounds.
     */
    public void start(){
        GameIO io = new GameIOImpl(System.in, System.out);

        do{
            startGame(io);
        } while (playAnotherGame(io));
    }

    /**
     * Creates a new game using the provided GameIO.
     *
     * @param io The GameIO for interacting with the user.
     * @return A Result containing the created Game if successful, or an error message if failed.
     */
    private Result<Game, String> createGame(GameIO io){
        GameFactory fac = new GameFactoryImpl();
        return fac.createGame(io, GameType.TICTACTOE_1V1);
    }

    /**
     * Starts a game session by creating a new game and initializing it.
     * Displays an error message if the game creation fails.
     *
     * @param io The GameIO for interacting with the user.
     */
    private void startGame(GameIO io){
        var r_create = createGame(io);
        if(r_create.isErr()){
            io.putString("Program encountered an error. Could not create a game because: " + r_create.getErr());
            return;
        }

        Game game = r_create.unwrap();
        game.startGame();
    }

    /**
     * Asks the user if they want to play another round and handles their response.
     *
     * @param io The GameIO for interacting with the user.
     * @return True if the user wants to play another round, false otherwise.
     */
    boolean playAnotherGame(GameIO io){
        int i = 0;
        boolean yes;
        boolean no;

        do{
            String keepPlayingMsg = "Do you want to play another round?\nYes[y,yes], No[n,no]\n";
            var r_putStr = io.putString(keepPlayingMsg);
            if(r_putStr.isErr()){
                return false;
            }

            var r_getStr = io.getString();
            if(r_getStr.isErr()){
                return false;
            }

            String answer = r_getStr.unwrap();
            yes = isYes(answer);
            no = isNo(answer);
            i++;
        } while (!yes && !no && i < 3);

        return yes != no && yes;
    }

    /**
     * Checks if the provided string represents a positive response (yes).
     *
     * @param str The input string to check.
     * @return True if the input represents a positive response (yes), false otherwise.
     */
    boolean isYes(String str){
        Pattern pattern = Pattern.compile("(?i)y|yes");
        return pattern.matcher(str).matches();
    }

    /**
     * Checks if the provided string represents a negative response (no).
     *
     * @param str The input string to check.
     * @return True if the input represents a negative response (no), false otherwise.
     */
    boolean isNo(String str){
        Pattern pattern = Pattern.compile("(?i)n|no");
        return pattern.matcher(str).matches();
    }
}