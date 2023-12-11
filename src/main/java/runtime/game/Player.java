package runtime.game;

import runtime.common.Result;

public interface Player {
    String getName();
    Result<Boolean, String> makeMove(Board board);
}
