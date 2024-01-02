package game;

import common.Result;

import java.util.List;

class TicTacToe_1v1 implements Game {

        //Parameter containing the current State of the Game (enum)
    private GameState gameState;
        // Game Data (Parameters)
    private Board board;
    private GameIO io;
    private List<Player> players;

        //Constructor
    TicTacToe_1v1(Board board, GameIO io, List<Player> players) throws IllegalArgumentException{
        if(board != null)
            this.board = board;
        else
            throw new IllegalArgumentException("Invalid \"board\" parameter passed");


        if(io != null)
            this.io = io;
        else
            throw new IllegalArgumentException("Failed to set Players");


        if(players != null && (players.size() == 2))
            this.players = players;
        else
            throw new IllegalArgumentException("Failed to set Players");

        gameState = GameState.CREATED;
    }



    @Override
    public void startGame() {
        gameState = GameState.RUNNING;

        printWelcomeMessage();





    }

    @Override
    public GameState getGameState() {
        return gameState;
    }



    private void printWelcomeMessage(){
    this.io.putString("""

            \t### Game Started ###\s

            GameType: \t\t TickTacToe - PvP
            \t# Rules #
            THERE ARE NO RULES
            """);
    }

    private void setUpPlayer(List<Player> players, int playerNumber){
        this.io.putString("Player 1, enter your Name:");
        Result<String,String> playerName = this.io.getString();
        this.io.putString("Player 1, enter your Symbol[A-Z]:");
        Result<String,String>l = this.io.getString();
        //TODO unfinished
        //Check if Needed
    }




}





