package runtime.game;

import runtime.common.Result;

interface GameFactory {
    Result<Game, String> createGame(GameIO io, String gameType);
}
