package game;

import common.Result;

interface GameFactory {
    Result<Game, String> createGame(GameIOInterface io, GameType gameType);
}
