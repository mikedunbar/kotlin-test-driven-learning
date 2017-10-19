import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * Current spot for misc. Kotlin facts, may eventually be migrated to a different test class
 */
@RunWith(JUnitPlatform::class)
class MiscForNowTest : Spek({

    given("The need to execute some code only when a given variable is non-null") {
        var letExecuted = false
        it("Can be done with a let block after the null check") {
            nonNullHolder.name?.let {
                letExecuted = true
            }
            letExecuted shouldBe true
            letExecuted = false

            nullHolder.name?.let {
                letExecuted = true
            }
            letExecuted shouldBe false
        }
    }

    given("The need to scope a variable to a narrow block") {
        it("Can be done with a let block") {
            nonNullHolder.name.let { theName ->
                theName shouldEqual nonNullHolder.name
            }
            //uncomment below to see that var theName doesn't exist here
            //theName shouldEqual nonNullHolder.name

            nullHolder.name.let { theName ->
                println("In let with null name")
                theName shouldBe null
            }

        }
    }

    given("the need to branch based on whether a variable is null or not") {
        it("can be done with the 'elvis' operator - '?:', which takes the right-hand value if the left-hand value is null") {
            val name1 = nonNullHolder.name ?: "replacedName"
            val name2 = nullHolder.name ?: "replacedName"

            name1 shouldEqual "something"
            name2 shouldEqual "replacedName"
        }

    }



})

class Holder (val name: String?)
val nonNullHolder = Holder("something")
val nullHolder = Holder(null)
