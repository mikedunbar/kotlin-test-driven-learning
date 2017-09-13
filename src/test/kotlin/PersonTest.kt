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
class PersonTest : Spek({
    given("blah") {
        it ("should work") {
            val p = Person("Amy", 33)
            "Amy" shouldEqual p.name
        }
    }
})