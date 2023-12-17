import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Michi
 * Temporary for checking out if Tests work
 * !!Should be deleted later!!
 * @version 1.0
 * @since 1.0
 * @see Temp
 * just curious if this shows up in Javadoc :)
 */
public class TempTest {
    @Test
    public void testMethodTestMethod() {
        Temp tempTest = new Temp();
        assertEquals(9, tempTest.methodTestMethod(4, 5));
    }

    @Test
    public void testWillPassMethod() {
        Temp tempTest = new Temp();
        assertEquals(100, tempTest.methodTestMethod(50, 50));
    }


}
