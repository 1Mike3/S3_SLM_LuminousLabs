package runtime.game;

import runtime.common.Result;

public interface GameIO {
    Result<Integer, String> getInt();
    Result<String, String> getString();
    Result<Boolean, String> putString();
}
