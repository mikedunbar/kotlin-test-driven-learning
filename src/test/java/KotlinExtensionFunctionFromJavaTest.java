import org.junit.Test;

import static foo.TopLevelFunctionsAndPopertiesKt.extensionFunc;
import static org.junit.Assert.assertEquals;

/**
 * @author Mike Dunbar
 */
public class KotlinExtensionFunctionFromJavaTest {

    @Test
    public void testKotlinExtensionFunctionBecomeStaticUtilMethodFromJava() {
        assertEquals(9, extensionFunc("my string"));
    }
}
