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
class EnumTest : Spek({

    given("The need to define and enum") {
        it("uses the 'enum' soft keyword before the class keyword") {
            Size.SMALL.toString() shouldEqual "SMALL"
        }
    }

    given("The need to define properties and functions for an enum") {
        it("it similar to a Kotlin class definition") {
            Platform.ANDROID.description() shouldEqual "Android, brought to you by Google"
        }

        it("requires a semicolon at the end of the enum constant list since it includes a function") {
            Platform.IOS.description() shouldEqual "IOS, brought to you by Apple"
        }
    }
})

enum class Size {
    SMALL, MEDIUM, LARGE, EXTRA_LARGE
}

enum class Platform(val title: String, val parentCompany: String) {
    ANDROID("Android", "Google"),
    IOS("IOS", "Apple");

    fun description() = "$title, brought to you by $parentCompany"
}