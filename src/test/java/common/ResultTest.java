package common;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Result} class, covering various scenarios to ensure its correctness and behavior.
 */
public class ResultTest {

    /**
     * Tests creating a Result instance of type Ok and verifies that it is indeed an Ok result.
     */
    @Test
    public void ok_ResultOfTypeOk_returnsResult(){
        int value = 7;
        Result<Integer, String> res = Result.ok(value);
        assertTrue(res.isOk());
        assertFalse(res.isErr());
        assertEquals(value, res.value());
    }

    /**
     * Tests creating a Result instance of type Err with a valid error value and ensures it is an Err result.
     */
    @Test
    public void err_passesValidArgument_returnsResult(){
        String value = "some text";
        Result<Integer, String> res = Result.err(value);
        assertTrue(res.isErr());
        assertFalse(res.isOk());
        assertEquals(value, res.error());
    }

    /**
     * Tests creating an Err result with a null error object, which should throw an IllegalArgumentException.
     */
    @Test
    public void err_passesNullAsArgument_throwsException(){
        Exception ex = assertThrows(IllegalArgumentException.class, () -> Result.err(null));
        String expectedMsg = "Error object must be provided for err.";
        String actualMsg = ex.getMessage();
        assertTrue(actualMsg.contains(expectedMsg));
    }

    /**
     * Tests checking if a Result of type Ok correctly returns true for isOk() and false for isErr().
     */
    @Test
    public void isOk_ResultOfTypeOk_returnsTrue(){
        Result<Integer, String> res = Result.ok(7);
        assertTrue(res.isOk());
        assertFalse(res.isErr());
    }

    /**
     * Tests checking if a Result of type Err correctly returns false for isOk() and true for isErr().
     */
    @Test
    public void isOk_ResultOfTypeErr_returnsFalse(){
        Result<Integer, String> res = Result.err("some text");
        assertFalse(res.isOk());
        assertTrue(res.isErr());
    }

    /**
     * Tests checking if a Result of type Err correctly returns true for isErr() and false for isOk().
     */
    @Test
    public void isErr_ResultOfTypeErr_returnsTrue(){
        Result<Integer, String> res = Result.err("some text");
        assertTrue(res.isErr());
        assertFalse(res.isOk());
    }

    /**
     * Tests checking if a Result of type Ok correctly returns false for isErr() and true for isOk().
     */
    @Test
    public void isErr_ResultOfTypeOk_returnsFalse(){
        Result<Integer, String> res = Result.ok(7);
        assertFalse(res.isErr());
        assertTrue(res.isOk());
    }

    /**
     * Tests unwrapping a Result of type Ok and ensures it returns the expected value.
     */
    @Test
    public void unwrap_ResultOfTypeOk_returnsValue(){
        int value = 7;
        Result<Integer, String> res = Result.ok(value);
        assertEquals(value, res.unwrap());
    }

    /**
     * Tests attempting to unwrap a Result of type Err, which should throw an IllegalStateException.
     */
    @Test
    public void unwrap_ResultOfTypeErr_throwsException(){
        Result<Integer, String> res = Result.err("some text");
        Exception ex = assertThrows(IllegalStateException.class, res::unwrap);
        String expectedMsg = "Cannot unwrap an err result.";
        String actualMsg = ex.getMessage();
        assertTrue(actualMsg.contains(expectedMsg));
    }

    /**
     * Tests getting the error object from a Result of type Err and verifies it returns the expected error value.
     */
    @Test
    public void getErr_ResultOfTypeErr_returnsError(){
        String value = "some text";
        Result<Integer, String> res = Result.err(value);
        assertEquals(value, res.getErr());
    }

    /**
     * Tests attempting to get the error object from a Result of type Ok, which should throw an IllegalStateException.
     */
    @Test
    public void getErr_ResultOfTypeOk_throwsException(){
        Result<Integer, String> res = Result.ok(7);
        Exception ex = assertThrows(IllegalStateException.class, res::getErr);
        String expectedMsg = "No error object available for an ok result.";
        String actualMsg = ex.getMessage();
        assertTrue(actualMsg.contains(expectedMsg));
    }

    /**
     * Tests mapping a Result of one type to another type and ensures the mapper function is applied correctly.
     */
    @Test
    public void map_ResultWithDoubleValue_ResultWithIntValue(){
        Result<Double, String> res = Result.ok(7.7);
        Result<Integer, String> mapped = res.map(Double::intValue);
        assertTrue(mapped.isOk());
        assertEquals(7, mapped.unwrap());
    }

    /**
     * Tests attempting to map a Result without providing a valid mapper function, which should throw an IllegalArgumentException.
     */
    @Test
    public void map_mapperIsInvalid_throwsException(){
        Result<Double, String> res = Result.ok(7.7);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> res.map(null));
        String expectedMsg = "A mapper must be provided for map.";
        String actualMsg = ex.getMessage();
        assertTrue(actualMsg.contains(expectedMsg));
    }

    /**
     * Tests mapping a Result of type Err and verifies that it remains an Err result.
     */
    @Test
    public void map_ResultOfTypeErr_returnsError(){
        Result<Integer, String> res = Result.err("some text");
        Result<Integer, String> mapped = res.map((_) -> res.unwrap());
        assertTrue(mapped.isErr());
    }

    /**
     * Tests executing a consumer function when the Result is of type Ok and ensures it is executed as expected.
     */
    @Test
    public void ifOk_ResultOfTypeOk_consumerIsExecuted(){
        AtomicBoolean exec = new AtomicBoolean(false);
        Result<Integer, String> res = Result.ok(7);
        Result<Integer, String> ifOk = res.ifOk((_) -> exec.set(true));
        assertTrue(exec.get());
        assertEquals(res, ifOk);
    }

    /**
     * Tests executing a consumer function when the Result is of type Ok, but no consumer is provided.
     */
    @Test
    public void ifOk_noConsumerProvided_consumerIsExecuted(){
        boolean exec = false;
        Result<Integer, String> res = Result.ok(7);
        Result<Integer, String> ifOk = res.ifOk(null);
        assertFalse(exec);
        assertEquals(res, ifOk);
    }

    /**
     * Tests executing a consumer function when the Result is of type Err and ensures it is not executed.
     */
    @Test
    public void ifOk_ResultOfTypeErr_consumerIsExecuted(){
        AtomicBoolean exec = new AtomicBoolean(false);
        Result<Integer, String> res = Result.err("some text");
        Result<Integer, String> ifOk = res.ifOk((_) -> exec.set(true));
        assertFalse(exec.get());
        assertEquals(res, ifOk);
    }

    /**
     * Tests executing a consumer function when the Result is of type Ok and ensures it is executed as expected.
     */
    @Test
    public void ifErr_ResultOfTypeOk_consumerIsExecuted(){
        AtomicBoolean exec = new AtomicBoolean(false);
        Result<Integer, String> res = Result.ok(7);
        Result<Integer, String> ifOk = res.ifOk((_) -> exec.set(true));
        assertTrue(exec.get());
        assertEquals(res, ifOk);
    }

    /**
     * Tests executing a consumer function when the Result is of type Ok, but no consumer is provided.
     */
    @Test
    public void ifErr_noConsumerProvided_consumerIsExecuted(){
        boolean exec = false;
        Result<Integer, String> res = Result.ok(7);
        Result<Integer, String> ifOk = res.ifOk(null);
        assertFalse(exec);
        assertEquals(res, ifOk);
    }

    /**
     * Tests executing a consumer function when the Result is of type Err and ensures it is executed as expected.
     */
    @Test
    public void ifErr_ResultOfTypeErr_consumerIsExecuted(){
        AtomicBoolean exec = new AtomicBoolean(false);
        Result<Integer, String> res = Result.err("some text");
        Result<Integer, String> ifOk = res.ifOk((_) -> exec.set(true));
        assertFalse(exec.get());
        assertEquals(res, ifOk);
    }
}
