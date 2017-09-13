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
class BasicsTest : Spek({
    given("a simple function") {
        fun getGreeting(name: String) : String {
            return "Hello $name"
        }

        it("shows you a lot about the language") {
            val greeting: String = getGreeting("Donald")
            greeting shouldEqual "Hello Donald"
        }
    }

    given("a statement") {
        it ("works with a semicolon, like Java") {
            val x = 5;
            x shouldEqual 5
        }

        it("works without a semicolon, like Groovy") {
            val x = 5
            x shouldEqual 5
        }
    }


    given("variables declared without a type") {
        val x = 5
        val z = 7.5
        val y = "blah"


        it ("should infer the type") {
            x shouldBeInstanceOf Int::class
            z shouldBeInstanceOf Double::class
            y shouldBeInstanceOf String::class
        }
    }

    given("variables declared with a type") {
        val x: Int = 5
        val z: Double = 7.5
        val y: String = "blah"
        it("works fine as well") {
            x shouldBeInstanceOf Int::class
            z shouldBeInstanceOf Double::class
            y shouldBeInstanceOf String::class
        }
    }

    given("a function with a single expression as it's body") {
        fun max(a: Int, b: Int): Int {
            return if (a > b) a else b
        }

        it("can be written concisely on a single line 'expression-body") {
            fun max2(a: Int, b: Int): Int = if (a > b) a else b

            max(10, 5) shouldEqual max2(10, 5)
        }
    }

    given("an expression-body function") {
        it("can have it's return type omitted from the declaration and inferred and compile time") {
            fun max(a: Int, b: Int) = if (a > b) a else b

            max(10, 5) shouldEqual 10
        }
    }

    given("a statement-body function that returns a value") {
        it("must declare it's return type and must have an explicit return statement") {
            fun max(a: Int, b: Int) = if (a > b) a else b

            max(10, 5) shouldEqual 10
        }
    }
})