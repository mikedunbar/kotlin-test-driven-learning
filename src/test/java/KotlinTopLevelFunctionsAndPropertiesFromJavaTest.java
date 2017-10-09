import foo.FunkyUtils;
import foo.TopLevelFunctionsAndPopertiesKt;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mike Dunbar
 */
public class KotlinTopLevelFunctionsAndPropertiesFromJavaTest {


    @Test
    public void testKotlinTopLevelFunctionShallCompileToStaticMethodInClassCorrespondingToKotlinFileNameByDefault() {
        assertEquals("This is a top-level function", TopLevelFunctionsAndPopertiesKt.topLevelFunction());
    }

    @Test
    public void testKotlinTopLevelFunctionsCanBeGivenDifferentClassNameWithFileAnnotationJvmNameField() {
        assertEquals("blah", FunkyUtils.anotherTopLevelFunction());
    }

    @Test
    public void testKotlinTopLevelPropertyShallCompileToStaticFieldsExposedWithGetterAndSetterByDefault() {
        assertEquals("mine", TopLevelFunctionsAndPopertiesKt.getMyVar());
        TopLevelFunctionsAndPopertiesKt.setMyVar("still mine");
        assertEquals("still mine", TopLevelFunctionsAndPopertiesKt.getMyVar());
    }

    @Test
    public void testKotlinTopLevelPropertiesWithConstModifierShallCompileToPublicStaticFinalField() {
        assertEquals("valuable", FunkyUtils.SOME_VAL);
    }

}
