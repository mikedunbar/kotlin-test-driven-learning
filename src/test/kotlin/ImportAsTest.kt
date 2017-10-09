import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import JavaBook as BookType
import foo.topLevelFunction as reallyUsefulFunction

/**
 * @author Mike Dunbar
 */
@RunWith(JUnitPlatform::class)
class ImportAsTest : Spek ({
    given("The need to change the name of an imported class or function") {
        it("can be done with 'import as'") {
            val myBook = BookType("The Book", false)
            myBook.title shouldEqual  "The Book"

            reallyUsefulFunction() shouldEqual "This is a top-level function"
        }

    }

})