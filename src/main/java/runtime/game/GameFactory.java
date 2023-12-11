package runtime.game;

import runtime.common.Result;

public interface GameFactory {
    Result<Game, String> createGame(GameIO io, String gameType);
}
