package game;

import common.Result;

class HumanPlayer implements Player {
    HumanPlayer(GameIOInterface io, String name, char mark){

    }

    @Override
    public char getMark() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Result<Boolean, String> makeMove(Board board) {
        return null;
    }
}
