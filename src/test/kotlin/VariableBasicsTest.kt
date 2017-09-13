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
class VariableBasicsTest : Spek({
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

    given("variables declared with types") {
        val x: Int = 5
        val z: Double = 7.5
        val y: String = "blah"
        it("is not needed, but works fine as well") {
            x shouldBeInstanceOf Int::class
            z shouldBeInstanceOf Double::class
            y shouldBeInstanceOf String::class
        }
    }

    given("a mutable variable - 'var' keyword") {
        var x = 7
        it("can be reassigned after it's initialized") {
            x = 9
            x shouldEqual 9
        }

        it("cannot change it's type") {
            // Uncomment below for compiler error
            //x = "mike"
        }
    }

    given("an immutable variable - 'val' keyword") {
        val x = 7
        it("cannot be reassigned after it's initialized") {
            // Uncomment below to see compiler error
            //x = 10
        }

        it("can point to a mutable object, which can be mutated") {
            val list = arrayListOf("blue", "red", "white")
            list shouldEqual arrayListOf("blue", "red", "white")

            list.add("purple")
            list shouldEqual arrayListOf("blue", "red", "white", "purple")
        }
    }
})