package game;

import common.Result;

interface GameFactory {
    Result<Game, String> createGame(GameIO io, GameType gameType);
}
