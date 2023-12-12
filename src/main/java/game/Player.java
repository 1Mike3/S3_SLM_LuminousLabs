package game;

import common.Result;

interface Player {
    String getName();
    Result<Boolean, String> makeMove(Board board);
}
