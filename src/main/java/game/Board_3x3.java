package game;

class Board_3x3 implements Board {
    private char[][] board;
    private BoardState boardState;
    private PlayerState playerState;

    Board_3x3() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' '; //initialize with empty spaces
            }
        }
        boardState = BoardState.CREATED;
        playerState = PlayerState.UNDEFINED;
    }

    @Override
    public boolean addMove(Player player, int x, int y) {
        if (x < 0 || x >= 3 || y < 0 || y >= 3 || board[x][y] != ' ') {
            return false; //invalid move
        }

        board[x][y] = player.getMark();
        updateBoardAndPlayerState(x, y, player.getMark());
        return true;
    }

    @Override
    public char[][] getBoardValues() {
        return board;
    }

    @Override
    public BoardState getBoardState() {
        return boardState;
    }

    @Override
    public PlayerState getPlayerState(Player player) {
        return playerState;
    }

    private void updateBoardAndPlayerState(int x, int y, char mark) {
        if (checkWin(x, y, mark)) {
            playerState = PlayerState.WIN;
            boardState = BoardState.DONE;
        } else if (checkDraw()) {
            playerState = PlayerState.DRAW;
            boardState = BoardState.DONE;
        } else {
            boardState = BoardState.RUNNING;
        }
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true; //no empty spaces = draw
    }

    private boolean checkWin(int x, int y, char mark) {
        //check row and column
        boolean rowWin = true;
        boolean colWin = true;
        for (int i = 0; i < 3; i++) {
            if (board[x][i] != mark) rowWin = false;
            if (board[i][y] != mark) colWin = false;
        }
        if (rowWin || colWin) return true;

        //check diagonals
        boolean diagWin = true;
        boolean antiDiagWin = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] != mark) diagWin = false;
            if (board[i][2 - i] != mark) antiDiagWin = false;
        }
        return diagWin || antiDiagWin;
    }

    //method to print the board
    public void printBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("|" + board[i][j]);
            }
            System.out.println("|");
            if (i < 2) {
                System.out.println("-----------");
            }
        }
    }
}
