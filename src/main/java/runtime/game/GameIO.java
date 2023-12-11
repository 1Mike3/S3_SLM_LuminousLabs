package runtime.game;

import runtime.common.Result;

interface GameIO {
    Result<Integer, String> getInt();
    Result<String, String> getString();
    Result<Boolean, String> putString();
}
