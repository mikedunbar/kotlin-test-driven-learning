import kotlin.collections.CollectionsKt;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static foo.TopLevelFunctionsAndPopertiesKt.callableFromJava;


/**
 * @author Mike Dunbar
 */
public class KotlinFromJavaTest {

    @Test
    public void testUseKotlinCustomAccessor() {
        Person pete = new Person("Pete", 39);
        assertTrue(pete.getCanBuyAlcohol());
    }

    @Test
    public void testUseKotlinFunctionTakingFunctionAsAnArg() {
        String res = callableFromJava(x -> x + 1).invoke();
        assertEquals("someFunc returned 3 when called with 2", res);
    }

    @Test
    public void testUseKotlinExtensionFunction_RequiresPassingTheReceiver() {
        List<String> strings = Arrays.asList("one", "two", "tree");
        assertTrue(CollectionsKt.any(strings, s -> s.equals("two")));
    }

}
