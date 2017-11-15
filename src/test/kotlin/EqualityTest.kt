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
class EqualityTest : Spek({
    given("Any two objects") {
        class StrWrap(val name: String) {
            override fun equals(other: Any?) : Boolean {
                if (other == null || other !is StrWrap) {
                    return false
                } else {
                    return other.name == name
                }
            }
        }
        var name1 = StrWrap("Mike")
        var name2 = StrWrap("Mike")
        it("does equality comparison using '==', a short-cut to the equals method") {
            assert(name1 == name2)
        }
        it("does reference comparison using '===', like '==' in Java ") {
            val refsEqual = name1 === name2
            assert(!refsEqual)
            val name3 = name2
            assert(name3 === name2)
        }
    }

})