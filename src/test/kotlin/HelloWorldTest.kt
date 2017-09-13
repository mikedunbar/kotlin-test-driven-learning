import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBeInstanceOf
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
class HelloWorldTest : Spek({
    given("a simple function") {
        fun getGreeting(name: String) : String {
            val greeting = "Hello $name";
            println("Returning $greeting")
            return greeting
        }

        it("shows you a lot about the language:" +
                "'fun' is the keyword for function, " +
                "parameter name comes before type, " +
                "return type comes at the end, " +
                "string templates can be defined with the '$' symbol, " +
                "use println to print/no System.out required, " +
                "semicolons are optional" ) {
            val greeting: String = getGreeting("Donald")
            greeting shouldEqual "Hello Donald"
        }
    }

    given("an expression, as opposed to a statement") {
        it("has a value and can be used as part of another expression or statement") {
            val x = 5
            val y = x + 5
            y shouldEqual 10
        }
    }

    given("a statement, as opposed to an expression") {
        it("must be a top-level element in its enclosing scope, " +
                "does not have a value, " +
                "cannot be used in other expression or statement") {
            // Remove the comment at start of line below to see the compiler error
            /*val n =*/ for (s in arrayOf("one", "two", "three")) { s shouldBeInstanceOf String::class }

        }
    }
})