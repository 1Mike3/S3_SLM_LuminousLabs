package game;

class Board_3x3 implements Board {
    private char[][] board;
    private PlayerState currentPlayer;
    private BoardState boardState;

    Board_3x3() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        currentPlayer = PlayerState.X;
        boardState = BoardState.CREATED;
    }

    @Override
    public boolean addMove(int x, int y) {
        if (x < 0 || x >= 3 || y < 0 || y >= 3 || board[x][y] != ' ' || boardState == BoardState.DONE) {
            return false;
        }
        board[x][y] = currentPlayer == PlayerState.X ? 'X' : 'O'; // Place the move
        if (checkWin()) {
            boardState = BoardState.DONE;
            // Additional logic to handle win can be added here
        } else if (isBoardFull()) {
            boardState = BoardState.DONE;
            // Additional logic to handle draw can be added here
        } else {
            currentPlayer = currentPlayer == PlayerState.X ? PlayerState.O : PlayerState.X; // Switch player
            boardState = BoardState.RUNNING;
        }
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
    public PlayerState getPlayerState() {
        return currentPlayer;
    }

    private boolean checkWin() {
        char mark = currentPlayer == PlayerState.X ? 'X' : 'O';

        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == mark && board[i][1] == mark && board[i][2] == mark) ||
                    (board[0][i] == mark && board[1][i] == mark && board[2][i] == mark)) {
                return true;
            }
        }

        // Check diagonals
        return (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark) ||
                (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark);
    }


    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
