import com.package1.*
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * @author Mike Dunbar
 */
@RunWith(JUnitPlatform::class)
class VisibilityModifiersTest : Spek({
    given("an element without a visibility modifier") {
        class Info (val name : String)

        it ("is public by default") {
            val info = Info("Tom")
            info.name shouldEqual "Tom"
        }
    }

    given("an element with the 'internal' modifier") {
        class Info (internal val name : String)

        it("Is visible within the 'module'. A 'module' is a set of files compiled together") {
            val info = Info("Blah")
            info.name shouldEqual "Blah"
        }
    }

    given("a class element with the 'private' modifier") {
        class Info(private val name: String) {
            private fun privateFun() = "private"
        }

        it("Is only accessible inside that class") {
            val info = Info("Brett")

            // Uncomment below to see errors
            //info.name shouldEqual "Brett"
            //info.privateFun() shouldEqual "private"
        }
    }

    given("A top-level element with the 'private' modifier") {
        it("Is only accessible inside that file") {
            doSomething() shouldEqual 22

            // Uncomment below to see error
            //doSomethingFromOtherFile() shouldEqual 12
        }
    }

    given("a class element with the 'protected' modifier") {
        it("Is only accessible from subclasses, not package members like Java") {
            open class A  {
                protected fun sayIt() = "it"
            }

            class B : A() {
                fun doSomethingElse() = sayIt() + "and this"
            }

            class C {
                fun doSomethingElse() : String {
                    val a = A()
                    // Uncomment below to see error
                    return /*a.sayIt() + */ "and this"
                }
            }
        }
    }
})

private fun doSomething() = 22