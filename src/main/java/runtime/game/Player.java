package runtime.game;

import runtime.common.Result;

interface Player {
    String getName();
    Result<Boolean, String> makeMove(Board board);
}
