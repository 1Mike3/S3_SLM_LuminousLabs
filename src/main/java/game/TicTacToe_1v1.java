package game;

import common.Result;
import java.util.List;

import static java.lang.System.exit;

/**
 * Class representing a Game of TicTacToe
 * Implements the Game Interface
 * This is where the action happens :)
 * @see Game
 */
class TicTacToe_1v1 implements Game {

    //Parameter containing the current State of the Game (enum)
    private GameState gameState;
        // Game Data (Parameters)
    private Board board;
    private GameIO io;
    private List<Player> players;

            //region getter setter (testing and debugging)
            public void setGameState(GameState gameState) {
                this.gameState = gameState;
            }
            public Board getBoard() {
                return board;
            }
            public void setBoard(Board board) {
                this.board = board;
            }
            public List<Player> getPlayers() {
                return players;
            }
            public void setPlayers(List<Player> players) {
                this.players = players;
            }
            //endregion


    /**
     * Only usable Constructor to create a new Game of TicTacToe
     * @param board
     * @param io
     * @param players
     * @throws IllegalArgumentException
     */
    TicTacToe_1v1(Board board, GameIO io, List<Player> players) throws IllegalArgumentException{
        if(board != null)
            this.board = board;
        else {
            throw new IllegalArgumentException("Invalid \"board\" parameter passed");
        }

        if(io != null)
            this.io = io;
        else {
            throw new IllegalArgumentException("Failed to set Players");
        }

        if(players != null && (players.size() == 2))
            this.players = players;
        else {
            throw new IllegalArgumentException("Failed to set Players");
        }
        gameState = GameState.CREATED;
    }


    //region core methods

    /**
     * Method to start the Game
     * this is basically the main loop of the game
     * where different methods from other classes are called
     */
    @Override
    public void startGame() {
        gameState = GameState.RUNNING;
        short playerIndex = 0;
        Player activePlayer;


        printWelcomeMessage();


        while (gameState == GameState.RUNNING){

            //Set the Player for this round (starts with player1)
            activePlayer = players.get(playerIndex);

            board.printBoard();

            boolean statusPlayerMakeMove = PlayerMakeMove(activePlayer);
            if(!statusPlayerMakeMove){
                System.out.println("Error while making move");
                exit(1);
            }


            Result<BoardState,String> r = evaluateGameStatus(activePlayer);
            if(r.isErr()) {
                System.out.println("Error while evaluating Game Status");
                exit(1);
            }
            switch (r.value()){
                case DONE:
                    gameState = GameState.DONE;
                    endGame(players,board);
                break;
                case RUNNING:
                    playerIndex = (short) ((playerIndex + 1) % 2); //toggle active player
                break;
                default:
                    System.out.println("Error, Undefined State ---startGame");exit(1);
            }
        }

    }

    //endregion

    /**
     * Method to get the current GameState
     * @return GameState
     */
    @Override
    public GameState getGameState() {
        return gameState;
    }






// region internals
    // Internal Methods for facilitating gameplay
    /**
    * Method to print a welcome message to the console
    */
    void printWelcomeMessage() {
            String s = String.format("""
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
                            P1 = %s \t %c
                            P2 = %s \t %c
                                            
                            """,
                    players.getFirst().getName(),
                    players.getFirst().getMark(),
                    players.getLast().getName(),
                    players.getLast().getMark()
            );
            this.io.putString(s);
        }

    /**
     *
     * @param p Player Class
     * @return true if move was valid, false if not
     */
    boolean PlayerMakeMove(Player p) {
        // Getting move from Player
        //true if move was valid, false if not
        boolean result = p.makeMove(board);

        // Case : Player made a valid move, has chance to try again
        if (! result) {
            for (int i = 0; i < 3; i++) {
                System.out.println("Invalid Move, try again:");
                result = p.makeMove(board);
                if (result)
                    return true;
            }
            return false;
        }
        return true;
    }


    /**
     * Method to evaluate the current Game Status
     *
     * @param p Player Class
     * @return Our default Result Class
     */
        Result<BoardState,String> evaluateGameStatus(Player p) {
        BoardState bs = board.getBoardState();
        //PlayerState ps = board.getPlayerState(p);
            Result<BoardState, String> r;
            if(bs == null)
                 r = new Result<>(null, "Error Boardstate is Null");
            else
                 r = new Result<>(bs, null);
            return r;
        }

        //region endGame

    /**
     * Method to end the Game
     * @param players //List of Players
     * @param board //Board Class
     */
    void endGame(List<Player> players, Board board){
        Player p1 = players.getFirst();
        Player p2 = players.getLast();

        PlayerState p1s = board.getPlayerState(p1);
        gameState = GameState.DONE;
        switch (p1s) {
            case WIN -> endGameWin(p1);
            case DRAW -> endGameDraw();
            case LOSS -> endGameWin(p2);
            //same behaviour for undefined
            default -> endGameUndefinded();
        }
            board.printBoard();
        this.io.putString("\n### GAME END ###");
        }

    /**
     * helper methods for endGame
     * @param winner The Player who won the game
     *
     */
    void endGameWin(Player winner){
        this.io.putString("\n\n## PLAYER " + winner.getName() + " WON THE GAME ## ");
        this.io.putString("CONGRATULATIONS !!");
    }

    /**
     * helper methods for endGame
     * call this method if the game ended in a draw
     */
    void endGameDraw(){
       this.io.putString("\n\nTHE GAME ENDED IN A DRAW");
    }

    /**
     * helper methods for endGame
     * call this method if the game ended in a draw
     */
    private void endGameUndefinded(){
        this.io.putString("\n\nUNEXPECTED BEHAVIOUR OCCURRED, NO WINNER COULD BE DETERMINED");
    }
//endregion
//endregion






} // Endclass










