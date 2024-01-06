package game;

import common.Result;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.exit;

class TicTacToe_1v1 implements Game {


        //Parameter containing the current State of the Game (enum)
    private GameState gameState;
        // Game Data (Parameters)
    private Board board;
    private GameIO io;
    private List<Player> players;

    //Getters, mostly for debugging
    public Board getBoard() {
        return board;
    }
    public GameIO getIo() {
        return io;
    }
    public List<Player> getPlayers() {
        return players;
    }



    //Constructor
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

    @Override
    public void startGame() {
        gameState = GameState.RUNNING;
        /**
         * Marker for the current Player in the Game
         * Starts with the Player 1 (0) and switches each Round ... duh
         */
        short playerIndex = 0;
        /**
         * Player whichs turn it is for the current turn
         */
        Player activePlayer;


        printWelcomeMessage();


        while (gameState == GameState.RUNNING){

            //Set the Player for this round (starts with player1)
            activePlayer = players.get(playerIndex);

            printBoardState();

            PlayerMakeMove(activePlayer);


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

    @Override
    public GameState getGameState() {
        return gameState;
    }






// region internals
    // Internal Methods for facilitating gameplay
        private void printWelcomeMessage() {
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

        private void printBoardState() {
        //TODO, check State
            System.out.println(Arrays.deepToString(board.getBoardValues()));
        }

        private void PlayerMakeMove(Player p){
        System.out.println("Player " + p.getName() + " Make your move:");
        // Getting move from Player
        p.makeMove(board);
        }



        private Result<BoardState,String> evaluateGameStatus(Player p) {
        BoardState bs = board.getBoardState();
        //PlayerState ps = board.getPlayerState(p);
            Result<BoardState, String> r;
            if(bs == null)
                 r = new Result<>(null, "Error Boardstate is Null");
            else
                 r = new Result<>(bs, null);
            return r;
        }

        private void endGame(List<Player> players,Board board){
        Player p1 = players.getFirst();
        Player p2 = players.getLast();

        PlayerState p1s = board.getPlayerState(p1);
        switch (p1s){
            case WIN -> endGameWin(p1);
        }



        }
    private void endGameWin(Player winner){
        ;
    }
    private void endGameDraw(){

    }

    private void endGameUndefinded(){
        this.io.putString("Random Bullshit");
    }

//endregion






} // Endclass
 class Main{
    //TestMain

    public static void main(String[] args){
        System.out.println("## Testmain 1v1 Start ## \n");

        //Create Gameio
        GameIO io = new GameIOImpl();

        //Factory -- Create Tick-Tack-Toe 1V1
        GameFactory f = new GameFactoryImpl();

        /* Exzerpt Factory Return
        Board board = new Board_3x3();
        List<Player> players = List.of(player_1, player_2);
        Game game = new TicTacToe_1v1(board, io, players);
        */

        //Factory Return Created Game
        Result<Game,String> r = f.createGame(io,GameType.TICTACTOE_1V1);

        //Random checks
        System.out.println(r.isOk());
        System.out.println(r.error());
        System.out.println(r.unwrap());
        System.out.println("AAAA");

        //Extract Game
        TicTacToe_1v1 game = (TicTacToe_1v1)r.unwrap();

        List<Player> L = game.getPlayers();
        Player p1 = L.getFirst();
        Player p2 = L.getLast();

        System.out.println(p1.getName());
        System.out.println(p2.getName());
        System.out.println(p1.getMark());
        System.out.println(p2.getMark());

        game.startGame();


    }
}









