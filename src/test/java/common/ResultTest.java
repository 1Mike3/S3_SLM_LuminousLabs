package common;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class ResultTest {
    @Test
    public void ok_ResultOfTypeOk_returnsResult(){
        int value = 7;
        Result<Integer, String> res = Result.ok(value);
        assertTrue(res.isOk());
        assertFalse(res.isErr());
        assertEquals(value, res.value());
    }

    @Test
    public void err_passesValidArgument_returnsResult(){
        String value = "some text";
        Result<Integer, String> res = Result.err(value);
        assertTrue(res.isErr());
        assertFalse(res.isOk());
        assertEquals(value, res.error());
    }

    @Test
    public void err_passesNullAsArgument_throwsException(){
        Exception ex = assertThrows(IllegalArgumentException.class, () -> Result.err(null));
        String expectedMsg = "Error object must be provided for err.";
        String actualMsg = ex.getMessage();
        assertTrue(actualMsg.contains(expectedMsg));
    }

    @Test
    public void isOk_ResultOfTypeOk_returnsTrue(){
        Result<Integer, String> res = Result.ok(7);
        assertTrue(res.isOk());
        assertFalse(res.isErr());
    }

    @Test
    public void isOk_ResultOfTypeErr_returnsFalse(){
        Result<Integer, String> res = Result.err("some text");
        assertFalse(res.isOk());
        assertTrue(res.isErr());
    }

    @Test
    public void isErr_ResultOfTypeErr_returnsTrue(){
        Result<Integer, String> res = Result.err("some text");
        assertTrue(res.isErr());
        assertFalse(res.isOk());
    }

    @Test
    public void isErr_ResultOfTypeOk_returnsFalse(){
        Result<Integer, String> res = Result.ok(7);
        assertFalse(res.isErr());
        assertTrue(res.isOk());
    }

    @Test
    public void unwrap_ResultOfTypeOk_returnsValue(){
        int value = 7;
        Result<Integer, String> res = Result.ok(value);
        assertEquals(value, res.unwrap());
    }

    @Test
    public void unwrap_ResultOfTypeErr_throwsException(){
        Result<Integer, String> res = Result.err("some text");
        Exception ex = assertThrows(IllegalStateException.class, res::unwrap);
        String expectedMsg = "Cannot unwrap an err result.";
        String actualMsg = ex.getMessage();
        assertTrue(actualMsg.contains(expectedMsg));
    }

    @Test
    public void getErr_ResultOfTypeErr_returnsError(){
        String value = "some text";
        Result<Integer, String> res = Result.err(value);
        assertEquals(value, res.getErr());
    }

    @Test
    public void getErr_ResultOfTypeOk_throwsException(){
        Result<Integer, String> res = Result.ok(7);
        Exception ex = assertThrows(IllegalStateException.class, res::getErr);
        String expectedMsg = "No error object available for an ok result.";
        String actualMsg = ex.getMessage();
        assertTrue(actualMsg.contains(expectedMsg));
    }

    @Test
    public void map_ResultWithDoubleValue_ResultWithIntValue(){
        Result<Double, String> res = Result.ok(7.7);
        Result<Integer, String> mapped = res.map(Double::intValue);
        assertTrue(mapped.isOk());
        assertEquals(7, mapped.unwrap());
    }

    @Test
    public void map_mapperIsInvalid_throwsException(){
        Result<Double, String> res = Result.ok(7.7);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> res.map(null));
        String expectedMsg = "A mapper must be provided for map.";
        String actualMsg = ex.getMessage();
        assertTrue(actualMsg.contains(expectedMsg));
    }

    @Test
    public void map_ResultOfTypeErr_returnsError(){
        Result<Integer, String> res = Result.err("some text");
        Result<Integer, String> mapped = res.map((_) -> res.unwrap());
        assertTrue(mapped.isErr());
    }

    @Test
    public void ifOk_ResultOfTypeOk_consumerIsExecuted(){
        AtomicBoolean exec = new AtomicBoolean(false);
        Result<Integer, String> res = Result.ok(7);
        Result<Integer, String> ifOk = res.ifOk((_) -> exec.set(true));
        assertTrue(exec.get());
        assertEquals(res, ifOk);
    }

    @Test
    public void ifOk_noConsumerProvided_consumerIsExecuted(){
        boolean exec = false;
        Result<Integer, String> res = Result.ok(7);
        Result<Integer, String> ifOk = res.ifOk(null);
        assertFalse(exec);
        assertEquals(res, ifOk);
    }

    @Test
    public void ifOk_ResultOfTypeErr_consumerIsExecuted(){
        AtomicBoolean exec = new AtomicBoolean(false);
        Result<Integer, String> res = Result.err("some text");
        Result<Integer, String> ifOk = res.ifOk((_) -> exec.set(true));
        assertFalse(exec.get());
        assertEquals(res, ifOk);
    }

    @Test
    public void ifErr_ResultOfTypeOk_consumerIsExecuted(){
        AtomicBoolean exec = new AtomicBoolean(false);
        Result<Integer, String> res = Result.ok(7);
        Result<Integer, String> ifOk = res.ifOk((_) -> exec.set(true));
        assertTrue(exec.get());
        assertEquals(res, ifOk);
    }

    @Test
    public void ifErr_noConsumerProvided_consumerIsExecuted(){
        boolean exec = false;
        Result<Integer, String> res = Result.ok(7);
        Result<Integer, String> ifOk = res.ifOk(null);
        assertFalse(exec);
        assertEquals(res, ifOk);
    }

    @Test
    public void ifErr_ResultOfTypeErr_consumerIsExecuted(){
        AtomicBoolean exec = new AtomicBoolean(false);
        Result<Integer, String> res = Result.err("some text");
        Result<Integer, String> ifOk = res.ifOk((_) -> exec.set(true));
        assertFalse(exec.get());
        assertEquals(res, ifOk);
    }
}
