
package game;

    class Board_3x3 implements Board {
        private char[][] board;
        private BoardState boardState;
        private PlayerState playerState;
        private Player player1;
        private Player player2;
        private Player currentPlayer;

        // Constructor
        Board_3x3(Player player1, Player player2) {
            this.player1 = player1;
            this.player2 = player2;
            this.currentPlayer = player1; // Start with player 1

            board = new char[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = ' '; // Initialize with empty spaces
                }
            }
            boardState = BoardState.CREATED;
            playerState = PlayerState.UNDEFINED;
        }

        private char getCurrentPlayerMark() {
            return currentPlayer.getMark();
        }

        private void switchTurn() {
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }

        @Override
        public boolean addMove(int x, int y) {
            char mark = getCurrentPlayerMark();

            if (x >= 0 && x < 3 && y >= 0 && y < 3 && board[x][y] == ' ') {
                board[x][y] = mark;
                updateBoardAndPlayerState(x, y, mark);
                switchTurn(); // Switch the turn after a move
                return true;
            }
            return false;
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
    public PlayerState getPlayerState() {
        return playerState;
    }

    // Additional private method to update the state of the board and player after a move
    private void updateBoardAndPlayerState(int x, int y, char mark) {
        if (checkWin(x, y, mark)) {
            playerState = PlayerState.WIN;
        } else if (checkDraw()) {
            playerState = PlayerState.DRAW;
        }

        // Update the board state based on player state
        if (playerState == PlayerState.WIN || playerState == PlayerState.DRAW) {
            boardState = BoardState.DONE;
        } else {
            boardState = BoardState.RUNNING;
        }
    }

    private boolean checkDraw() {
        // Check if all cells are filled
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                 if (board[i][j] == ' ') {
                     return false;
                 }
            }
        }
        return true;
    }

    // Additional private method to check if the game is won
    private boolean checkWin(int x, int y, char mark) {
        // Check if the row is filled
        boolean rowFilled = true;
        for (int i = 0; i < 3; i++) {
            if (board[x][i] != mark) {
                rowFilled = false;
                break;
            }
        }
        if (rowFilled) {
            return true;
        }

        // Check if the column is filled
        boolean colFilled = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][y] != mark) {
                colFilled = false;
                break;
            }
        }
        if (colFilled) {
            return true;
        }

        // Check if the diagonal is filled
        boolean diagFilled = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] != mark) {
                diagFilled = false;
                break;
            }
        }
        if (diagFilled) {
            return true;
        }

        // Check if the anti-diagonal is filled
        boolean antiDiagFilled = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][2 - i] != mark) {
                antiDiagFilled = false;
                break;
            }
        }
        if (antiDiagFilled) {
            return true;
        }

        return false;
    }

}
