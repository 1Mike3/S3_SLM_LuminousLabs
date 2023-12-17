package game;

import common.Result;


interface GameIOInterface {
    Result<Integer, String> getInt();
    Result<String, String> getString();
    Result<Boolean, String> putString(String string);
}

