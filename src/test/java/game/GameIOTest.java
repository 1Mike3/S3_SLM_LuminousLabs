package game;
import common.Result;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameIOTest {

    /** Performing tests on the GetInt function
    * edge cases and purposefully wrong input
     * Unit Test
     */
    @Test
    public void testGetInt()  {
        GameIO gameIOTest = new GameIO();
        java.io.InputStream InputStream = new java.io.ByteArrayInputStream("55".getBytes());
        java.io.OutputStream OutputStream = new java.io.ByteArrayOutputStream();

        // Testing normal Value
        gameIOTest.setTestMode(true, InputStream, OutputStream);
        Result<Integer, String> result = gameIOTest.getInt();
        assertTrue(result.isOk());
        assertEquals(result.value(), 55);


        // Testing edge case
        InputStream = new java.io.ByteArrayInputStream("2147483647".getBytes());
        gameIOTest.setTestMode(true, InputStream, OutputStream);
        result = gameIOTest.getInt();
        assertTrue(result.isOk());
        assertEquals(result.value(), 2147483647);

        InputStream = new java.io.ByteArrayInputStream("-2147483647".getBytes());
        gameIOTest.setTestMode(true, InputStream, OutputStream);
        result = gameIOTest.getInt();
        assertTrue(result.isOk());
        assertEquals(result.value(), -2147483647);

        InputStream = new java.io.ByteArrayInputStream("0".getBytes());
        gameIOTest.setTestMode(true, InputStream, OutputStream);
        result = gameIOTest.getInt();
        assertTrue(result.isOk());
        assertEquals(result.value(), 0);

        // Testing purposefully wrong input
        InputStream = new java.io.ByteArrayInputStream("2147483648".getBytes());
        gameIOTest.setTestMode(true, InputStream, OutputStream);
        result = gameIOTest.getInt();
        assertTrue(result.isErr());
        assertEquals(result.error(), "Input Range Error");

        InputStream = new java.io.ByteArrayInputStream("-2147483649".getBytes());
        gameIOTest.setTestMode(true, InputStream, OutputStream);
        result = gameIOTest.getInt();
        assertTrue(result.isErr());
        assertEquals(result.error(), "Input Range Error");

        InputStream = new java.io.ByteArrayInputStream("Hello i'm Mike\n".getBytes());
        gameIOTest.setTestMode(true, InputStream, OutputStream);
        result = gameIOTest.getInt();
        assertTrue(result.isErr());
        assertEquals(result.error(), "Input Range Error");


    }

    /** Testing the getString function
     * i am trying to cover all edge cases
     * Unit Test
     */
    @Test
    public void testGetString() {
        GameIO gameIOTest = new GameIO();

        // Test with valid string input
        ByteArrayInputStream testInput = new ByteArrayInputStream("yabaDaba doooooooo\n".getBytes());
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        gameIOTest.setTestMode(true, testInput, testOutput);

        Result<String, String> result = gameIOTest.getString();
        assertTrue(result.isOk());
        assertEquals("yabaDaba doooooooo", result.value());

        // Test with empty string input
        ByteArrayInputStream emptyInput = new ByteArrayInputStream("".getBytes());
        gameIOTest.setTestMode(true, emptyInput, testOutput);

        result = gameIOTest.getString();
        assertTrue(result.isErr());
        assertEquals("Invalid Input", result.error());

        // Just hitting enter
        ByteArrayInputStream enterInput = new ByteArrayInputStream("\n".getBytes());
        gameIOTest.setTestMode(true, enterInput, testOutput);
        result = gameIOTest.getString();
        assertTrue(result.isErr());
        assertEquals("Invalid Input", result.error());

        // Large String
        ByteArrayInputStream LargeInput = new ByteArrayInputStream(("asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadf1234#%ä\n").getBytes());
        gameIOTest.setTestMode(true, LargeInput, testOutput);
        result = gameIOTest.getString();
        assertTrue(result.isOk());
        assertEquals("asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadf1234#%ä", result.value());
    }


    /** Testing the putString function
     * Unit Test
     */
    @Test
    public void testPutString() {
        GameIO gameIOTest = new GameIO();

        // Test with a valid string
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutput));
        Result<Boolean, String> result = gameIOTest.putString("Hello World");
        assertTrue(result.isOk());
        assertEquals("Hello World\n", testOutput.toString()); // Including the newline character added by println

        // Test with an empty string
        testOutput.reset();
        result = gameIOTest.putString("");
        assertTrue(result.isErr());
        assertEquals("Invalid String", result.error());
        assertEquals("", testOutput.toString());

        // Test with a long string, seems ther is an app that does that for me
        testOutput.reset();
        String longString = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. :)";
        result = gameIOTest.putString(longString);
        assertTrue(result.isOk());
        assertEquals(longString + "\n", testOutput.toString());

        // Test with a string containing special characters
        testOutput.reset();
        String specialString = "Special characters: !@#öööö$%^&*()_+ääääää{}|:\"<>?";
        result = gameIOTest.putString(specialString);
        assertTrue(result.isOk());
        assertEquals(specialString + "\n", testOutput.toString());

        // Resetting System.out to its original state
        System.setOut(System.out);
    }


}
