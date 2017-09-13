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
class FunctionBasicsTest : Spek({
    given("a 'block body' function with a single expression") {
        fun max(a: Int, b: Int): Int {
            return if (a > b) a else b
        }

        it("can be written more concisely as an 'expression-body function") {
            fun max2(a: Int, b: Int): Int = if (a > b) a else b

            max(10, 5) shouldEqual max2(10, 5)
        }
    }

    given("an expression-body function") {
        it("can have it's return type omitted from the declaration and inferred at compile time") {
            fun max(a: Int, b: Int) = if (a > b) a else b

            max(10, 5) shouldEqual 10
        }
    }

    given("a 'block-body' function that returns a value") {
        it("must declare it's return type and must have an explicit return statement") {
            // Remove the return type and/or return keyword below to see compile errors
            fun max(a: Int, b: Int) : Int  {
                return if (a > b) a else b
            }
            max(5,4) shouldEqual 5
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

    given("the need to templatize a string") {
        it("is done with the $ char") {
            val thepres = "Trump"
            val s = "Your president is $thepres"
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