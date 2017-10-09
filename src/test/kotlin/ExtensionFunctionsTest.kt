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
class ExtensionFunctionsTest : Spek ({

    given("the desire to add functionality to an existing class") {

        it("can be done with an extension function") {
            // String is the 'receiver type' and 'this' is the 'receiver object'
            fun String.lastChar() = this.get(this.length - 1)

            val myString = "abcd"

            myString.lastChar() shouldEqual 'd'
        }

        it("is OK to omit the receiver object in an extension function") {
            fun String.lastChar() = get(length - 1)

            val myString = "abcd"

            myString.lastChar() shouldEqual 'd'
        }

        it("is not possible to access private class members in an extension function") {
            // Uncomment below to see compile error
            //fun JavaBook.accessPrivate = this.private + "not possible"
        }

        it("is OK for extension functions to call other extension functions") {
            fun String.lastChar() = get(length - 1)
            fun String.lastCharPlusD() = "${lastChar()}D"

            val myString = "abcd"
            myString.lastCharPlusD () shouldEqual "dD"
        }
    }



})