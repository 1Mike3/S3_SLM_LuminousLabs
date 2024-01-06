package game;

import common.Result;


import java.util.Scanner;

public class GameIOImpl implements GameIO {

    /** Variable to set the input stream
     * System.in is the default input stream, only changed for testing */
    private java.io.InputStream InputStream = System.in;
    java.io.OutputStream OutputStream = System.out;


    /**
     * Constructor GameIO, sets Streams, for default ()
     * The Streams can be changed, mostly relevant for testing
     * @param InputSteam
     * @param OutputStream
     */
    public GameIOImpl (java.io.InputStream InputSteam, java.io.OutputStream OutputStream){
        if (InputSteam != null)
            this.InputStream  = InputSteam;
        if (OutputStream != null)
            this.OutputStream = OutputStream;
    }

    /** For default Streams*/
    public  GameIOImpl (){

    }


    /**
     * Method to get an integer from the user
     *
     * @return Result Integer, String our common Result class
     */
    public Result<Integer, String> getInt() {
        Scanner scanner = new Scanner(InputStream);
        try {
            int input = scanner.nextInt();
            return Result.ok(input);
        } catch (Exception e) {
            return Result.err("Input Range Error");
        }
    }


    /**
     * Method to retrieve a String from the user
     * @return Result Boolean, String our common Result class
     */
    public Result<String, String> getString() {
        Scanner scanner = new Scanner(InputStream);
        try {
            String input = scanner.nextLine();
            if (input.length() < 1) {
                return Result.err("Invalid Input");
            } else {
                return Result.ok(input);
            }
        } catch (Exception e) {
            return Result.err("Invalid Input");
        }
    }


    /**
     * Method to print a String to the Console
     * @return Result Boolean, String our common Result class
     */
    public Result<Boolean, String> putString(String string) {
        if (string.isEmpty()) {
            return Result.err("Invalid String");
        }else{
            System.out.println(string);
            return Result.ok(true);
        }

    }
}