package game;

import common.Result;


import java.util.Scanner;

public class GameIO implements GameIOInterface {



    /** Variable to set the test mode */
    public Boolean testModeActivated = false;
    /** Variable to set the input stream
     * System.in is the default input stream, only changed for testing */
    private java.io.InputStream InputStream = System.in;
    java.io.OutputStream OutputStream = System.out;

    /**
     * Method to set the test mode
     * this method is used to replace the system streams with a test streams
     * @param testMode, ture for testing active, false for testing inactive
     * @param InputStream, the input stream to be used for testing (else System.in)
     */
    public void setTestMode(Boolean testMode, java.io.InputStream InputStream, java.io.OutputStream OutputStream) {
        if (testMode) {
            this.InputStream = InputStream;
            this.OutputStream = OutputStream;
        } else {
            this.InputStream = System.in;
            this.OutputStream = System.out;
        }
        this.testModeActivated = testMode;
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
        /* // Old Code, doesn't catch all errors
        int input = scanner.nextInt();
        if (!Integer.class.isInstance(input)){
            return Result.err("Input Range Error");
        } else {
            return Result.ok(input);
        }
        */

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