package game;
import common.Result;
import helpers.OSchecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for GameIOImpl.
 * This class contains unit tests to verify the correctness of the GameIOImpl class,
 *
 */
public class GameIOImplTest {


    /**
     * Test to ensure getInt() correctly reads and returns a valid integer from InputStream.
     */
    @Test
    public void getInt_ValidInput_correctRes(){
        java.io.InputStream InputStream = new java.io.ByteArrayInputStream("55".getBytes());
        java.io.OutputStream OutputStream = new java.io.ByteArrayOutputStream();
        GameIOImpl gameIOImplTest = new GameIOImpl(InputStream, OutputStream);

        Result<Integer, String> result = gameIOImplTest.getInt();
        assertTrue(result.isOk());
        assertEquals(result.value(), 55);
    }

    /**
     * Test to ensure getInt() correctly handles the maximum integer value as input.
     */
    @Test
    public void getInt_edgeCase1_correctRes(){
        java.io.InputStream InputStream = new java.io.ByteArrayInputStream("2147483647".getBytes());
        java.io.OutputStream OutputStream = new java.io.ByteArrayOutputStream();
        GameIOImpl gameIOImplTest = new GameIOImpl(InputStream, OutputStream);

        Result<Integer, String> result = gameIOImplTest.getInt();
        assertTrue(result.isOk());
        assertEquals(result.value(), 2147483647);
    }

    /**
     * Test to ensure getInt() correctly handles the minimum integer value as input.
     */
    @Test
    public void getInt_edgeCase2_correctRes(){
        java.io.InputStream InputStream = new java.io.ByteArrayInputStream("-2147483647".getBytes());
        java.io.OutputStream OutputStream = new java.io.ByteArrayOutputStream();
        GameIOImpl gameIOImplTest = new GameIOImpl(InputStream, OutputStream);

        Result<Integer, String> result = gameIOImplTest.getInt();
        assertTrue(result.isOk());
        assertEquals(result.value(), -2147483647);
    }

    /**
     * Test to ensure getInt() correctly handles zero as input.
     */
    @Test
    public void getInt_edgeCase3_correctRes(){
        java.io.InputStream InputStream = new java.io.ByteArrayInputStream("0".getBytes());
        java.io.OutputStream OutputStream = new java.io.ByteArrayOutputStream();
        GameIOImpl gameIOImplTest = new GameIOImpl(InputStream, OutputStream);

        Result<Integer, String> result = gameIOImplTest.getInt();
        assertTrue(result.isOk());
        assertEquals(result.value(), 0);
    }

    /**
     * Test to ensure getInt() returns an error for inputs larger than the maximum integer value.
     */
    @Test
    public void getInt_outOfBounds_error(){
        java.io.InputStream InputStream = new java.io.ByteArrayInputStream("2147483648".getBytes());
        java.io.OutputStream OutputStream = new java.io.ByteArrayOutputStream();
        GameIOImpl gameIOImplTest = new GameIOImpl(InputStream, OutputStream);

        Result<Integer, String> result = gameIOImplTest.getInt();
        assertTrue(result.isErr());
        assertEquals(result.error(), "Input Range Error");
    }

    /**
     * Test to ensure getInt() returns an error for inputs smaller than the minimum integer value.
     */
    @Test
    public void getInt_outOfBounds2_error(){
        java.io.InputStream InputStream = new java.io.ByteArrayInputStream("-2147483649".getBytes());
        java.io.OutputStream OutputStream = new java.io.ByteArrayOutputStream();
        GameIOImpl gameIOImplTest = new GameIOImpl(InputStream, OutputStream);

        Result<Integer, String> result = gameIOImplTest.getInt();
        assertTrue(result.isErr());
        assertEquals(result.error(), "Input Range Error");
    }

    /**
     * Test to ensure getInt() returns an error for non-integer inputs.
     */
    @Test
    public void getInt_wrongInputType_error(){
        java.io.InputStream InputStream = new java.io.ByteArrayInputStream("Hello i'm Mike\n".getBytes());
        java.io.OutputStream OutputStream = new java.io.ByteArrayOutputStream();
        GameIOImpl gameIOImplTest = new GameIOImpl(InputStream, OutputStream);

        Result<Integer, String> result = gameIOImplTest.getInt();
        assertTrue(result.isErr());
        assertEquals(result.error(), "Input Range Error");
    }




    /**
     * Parameterized test to verify getString() correctly reads different strings from InputStream.
     *
     * @param str The string to be tested.
     */
    @ParameterizedTest
    @ValueSource(strings =
            {
                    "yabaDaba dooooooo0",
                    "asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfsadf1234#%ä"
            })
    public void getString_differentStrings_correctRes(String str) {

        // Test with valid string input
        ByteArrayInputStream testInput = new ByteArrayInputStream((str + "\n").getBytes());
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        GameIOImpl gameIOImplTest = new GameIOImpl(testInput, testOutput);

        Result<String, String> result = gameIOImplTest.getString();
        assertTrue(result.isOk());
        assertEquals(str, result.value());
    }

    /**
     * Parameterized test to verify getString() handles invalid string inputs.
     *
     * @param str The string to be tested.
     */
    @ParameterizedTest
    @ValueSource(strings =
            {
                    "\n",
                    ""
            })
    public void getString_invalidStrings_error(String str) {

        // Test with valid string input
        ByteArrayInputStream testInput = new ByteArrayInputStream(str.getBytes());
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        GameIOImpl gameIOImplTest = new GameIOImpl(testInput, testOutput);

        Result<String, String> result = gameIOImplTest.getString();
        assertTrue(result.isErr());
        assertEquals("Invalid Input", result.error());
    }




    /**
     * Parameterized test to check if putString() correctly outputs different strings.
     *
     * @param str The string to be outputted.
     */
    @ParameterizedTest
    @ValueSource(strings =
            {
                    "Hello World",
                    "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. :)",
                    "Special characters: !@#öööö$%^&*()_+ääääää{}|:\"<>?"
            })
    public void putString_differentStrings_correctRes(String str) {
        GameIOImpl gameIOImplTest = new GameIOImpl();

        // Test with a valid string
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutput));
        Result<Boolean, String> result = gameIOImplTest.putString(str);
        assertTrue(result.isOk());

        //This extra snippets are used to let the test work properly on windows and linux (LF/CRLF)
        OSchecker osCk = new OSchecker();
        if (OSchecker.isUnix())
            assertEquals(str + "\n", testOutput.toString()); // Including the newline character added by println
        else if (OSchecker.isWindows())
            assertEquals(str + "\r\n", testOutput.toString()); // Including the newline character added by println
        else
            //autofail
            assertEquals("unsupported OS", "unsupported");
    }

    /**
     * Test to verify putString() returns an error for an empty string input.
     */
    @Test
    public void putString_emptyString_error() {
        GameIOImpl gameIOImplTest = new GameIOImpl();

        // Test with a valid string
        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutput));
        Result<Boolean, String> result = gameIOImplTest.putString("");

        assertTrue(result.isErr());
        assertEquals("Invalid String", result.error());
        assertEquals("", testOutput.toString());

    }

}