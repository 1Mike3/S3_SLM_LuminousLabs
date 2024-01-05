package game;

import common.Result;

interface Player {
    char getMark();
    String getName();
    Result<Boolean, String> makeMove(Board board);
}
