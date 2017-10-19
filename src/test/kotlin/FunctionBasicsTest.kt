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

    given("a collection created in Kotltin") {
        val list = arrayListOf("one", "two", "three")

        it("has a java collection type") {
            list shouldBeInstanceOf java.util.ArrayList::class
        }

        it("has more helper functions that available in Java") {
            list.last() shouldEqual "three"
            list.min() shouldEqual "one"
        }
    }

    given("a utility function") {
        fun <T> joinToString(
                collection: Collection<T>,
                separator: String,
                prefix: String,
                postFix: String
        ) : String {
            val result = StringBuilder(prefix)

            for((index, element) in collection.withIndex()) {
                if (index > 0) {
                    result.append(separator)
                }
                result.append(element)
            }
            result.append(postFix)
            return result.toString()
        }

        val intList = listOf(1,3,5,7)

        it("can be used") {
            joinToString(intList, "; ", "(", ")") shouldEqual "(1; 3; 5; 7)"
        }

        it("can be called with named parameters to increase readability (without IDE support)") {
            joinToString(intList, separator = "; ", prefix = "(", postFix = ")") shouldEqual "(1; 3; 5; 7)"
        }
    }

    given("a Java method") {
        it("Cannot be called from Kotlin with named parameters") {
            //Uncomment below to see compile error
            //val b = JavaBook(title = "Clean Arch", isAvailableOnSafare = false)
        }
    }

    given("a function defined with default parameter values") {
        fun <T> joinToString(
                collection: Collection<T>,
                separator: String = ", ",
                prefix: String = "",
                postFix: String = ""
        ) : String {
            val result = StringBuilder(prefix)

            for((index, element) in collection.withIndex()) {
                if (index > 0) {
                    result.append(separator)
                }
                result.append(element)
            }
            result.append(postFix)
            return result.toString()
        }
        val intList = listOf(1,3,5,7)

        it("can be called with the defaulted params omitted") {
            joinToString(intList) shouldEqual "1, 3, 5, 7"
        }

        it("can be called with the some or all of the defaulted params supplied") {
            joinToString(intList, " * ", "[", "]") shouldEqual "[1 * 3 * 5 * 7]"
            joinToString(intList, postFix = "]") shouldEqual "1, 3, 5, 7]"
        }
    }
})