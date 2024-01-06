package game;

import common.Result;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.StringTemplate.STR;
import static java.util.FormatProcessor.FMT;

class GameFactoryImpl implements GameFactory {
    private static final int MAX_PLAYER_NAME_LENGTH = 20;
    private static final int MAX_RETRIES = 3;

    /**
     * Creates a game for the provided gameType and handles
     * the necessary user interactions.
     * @param io
     * @param gameType
     * @return
     */
    @Override
    public Result<Game, String> createGame(GameIO io, GameType gameType) {
        if (io == null){
            return Result.err("Argument io is null");
        }

        switch (gameType){
            case TICTACTOE_1V1:
                return create_TICTACTOE_1V1(io);
            default:
                return Result.err(STR."Creation of GameType '\{io.toString()}' not implemented");
        }
    }

    /**
     * Creates a game for GameType.TICTACTOE_1V1
     * @param io
     * @return
     */
    Result<Game, String> create_TICTACTOE_1V1(GameIO io){
        Result<Player, String> r_player_1 = createHumanPlayer(io);
        if(r_player_1.isErr()){
            return Result.err(r_player_1.error());
        }

        Result<Player, String> r_player_2 = createHumanPlayer(io);
        if (r_player_1.isErr()){
            return Result.err(r_player_2.error());
        }

        Player player_1 = r_player_1.value();
        Player player_2 = r_player_2.value();

        if(player_1.getName().equals(player_2.getName())){
            return Result.err("Both players chose the same name");
        }

        if(player_1.getMark() == player_2.getMark()){
            return Result.err("Both players chose the same mark.");
        }

        Board board = new Board_3x3();
        List<Player> players = List.of(player_1, player_2);
        Game game = new TicTacToe_1v1(board, io, players);

        return Result.ok(game);
    }

    /**
     * Creates a human player and handles all user interactions
     * like ask for name and mark.
     * @param io
     * @return
     */
    Result<Player, String> createHumanPlayer(GameIO io){
        Result<String, String> r_name = getPlayerName(io);
        if(r_name.isErr()){
            return Result.err(r_name.getErr());
        }

        Result<Character, String> r_char = getPlayerChar(io);
        if(r_char.isErr()){
            return Result.err(r_char.error());
        }

        Player player = new HumanPlayer(io, r_name.value(), r_char.value());
        return Result.ok(player);
    }

    /**
     * Gets the player's name from the user.
     * @param io
     * @return
     */
    Result<String, String> getPlayerName(GameIO io){
        String user_prompt = FMT."Please input a name like [a-zA-z0-9]{1,%d\{MAX_PLAYER_NAME_LENGTH}";
        return getInputFromUser(io, this::isValidName, (str) -> str, user_prompt);
    }

    /**
     * Gets the player's mark from the user.
     * @param io
     * @return
     */
    Result<Character, String> getPlayerChar(GameIO io){
        String user_prompt = "Please input a single character for the board like [A-Z]{1}.";
        return getInputFromUser(io, this::isValidChar, (str) -> str.charAt(0), user_prompt);
    }

    /**
     * Genaric method to query a user for input and validation.
     * @param io
     * @param validator
     * @param mapper
     * @param prompt
     * @return
     * @param <T>
     */
    private <T> Result<T, String> getInputFromUser(GameIO io, Predicate<String> validator, Function<String, T> mapper, String prompt){
        boolean isValidName;
        int count_retries = 0;
        Result<String, String> r_get_name;

        do{
            Result<Boolean, String> r_ask_name = io.putString(prompt);
            if(r_ask_name.isErr()){
                return Result.err(r_ask_name.getErr());
            }

            r_get_name = io.getString();
            if(r_get_name.isErr()){
                return Result.err(r_get_name.getErr());
            }

            isValidName = validator.test(r_get_name.value());
            if(!isValidName){
                Result<Boolean, String> r_name_err = io.putString(FMT."Invalid input, please try again.");
                if(r_name_err.isErr()){
                    return Result.err(r_name_err.getErr());
                }
            }

            count_retries++;
        } while (!isValidName && count_retries < MAX_RETRIES);

        return isValidName ? Result.ok(mapper.apply(r_get_name.value())) : Result.err("Invalid input");
    }

    /**
     * Checks is a string is a valid player name.
     * @param str
     * @return
     */
    boolean isValidName(String str){
        if(str == null){
            return false;
        }

        Pattern p = Pattern.compile(FMT."^[a-zA-Z0-9]{1,%d\{MAX_PLAYER_NAME_LENGTH}}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * Checks is a char is a valid player mark.
     * @param str
     * @return
     */
    boolean isValidChar(String str){
        if(str == null || str.length() > 1){
            return false;
        }

        Pattern p = Pattern.compile("^[A-Z]$");
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
