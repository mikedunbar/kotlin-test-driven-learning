import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Mike Dunbar
 */
public class KotlinFromJavaTest {

    @Test
    public void testUseKotlinCustomAccessor() {
        Person pete = new Person("Pete", 39);
        assertTrue(pete.getCanBuyAlcohol());
    }

}
