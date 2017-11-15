import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * @author Mike Dunbar
 */
@RunWith(JUnitPlatform::class)
class DataClassesTest : Spek ({
    given("a data class") {
        data class FoodItem(val name: String, val isFruit: Boolean)

        it("auto-generates equals, hashCode, and toString methods") {
            val banana = FoodItem("banana", true)
            val banana2 = FoodItem("banana", true)
            val cake = FoodItem("cake", false)

            banana shouldEqual banana2
            banana shouldNotEqual cake

            banana.hashCode() shouldEqual banana2.hashCode()
            banana.hashCode() shouldNotEqual cake.hashCode()

            listOf<FoodItem>(banana, cake).contains(banana2) shouldEqual true

        }
    }
})