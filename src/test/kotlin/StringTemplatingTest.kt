import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * @author Mike Dunbar
 */
@RunWith(JUnitPlatform::class)
class StringTemplatingTest : Spek({
    given("the need to templatize a string") {
        it("is done with the $ char") {
            val thePres = "Trump"
            val s = "Your president is $thePres"
            s shouldEqual "Your president is Trump"
        }
    }

    given("a string that needs to include the $ char literal") {
        it("can be escape the '$' char with the '\' char") {
            val amt = 1000000
            val s = "I wish I had \$$amt dollars"
            s shouldEqual "I wish I had $1000000 dollars"
        }
    }

    given("a string that wishes to templatize more than simple variable names") {
        it("can be done using curly braces {}") {
            val s = "the total is ${20 + 50 + 30}"
            s shouldEqual "the total is 100"
        }
    }
})