package game;

import common.Result;

import java.io.IOException;
import java.util.Scanner;

interface GameIO {

    Result<Integer, String> getInt();
    Result<String, String> getString();
    Result<Boolean, String> putString(String string);
}