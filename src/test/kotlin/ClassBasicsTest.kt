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
class ClassBasicsTest : Spek({

    given("The need to define a class containing only properties") {
        it("Can be done very concisely in Kotlin with a primary constructor, where properties are first class citizens") {
            class Person(
                    val name: String,
                    val age : Int)

            val mike = Person("Mike", 42)
            mike.name shouldEqual "Mike"
            mike.age shouldEqual 42
        }
    }

    given("A class with both val and var properties ") {
        class Person(
                val name: String,
                var age : Int)
        val mike = Person("Mike", 42)

        it("provides both an accessor and a mutator for the var property") {
            mike.age shouldEqual 42
            mike.age = 43
            mike.age shouldEqual 43
        }

        it("provides only an accessor for the val property") {
            mike.name shouldEqual "Mike"
            //uncomment to see compile error
            //mike.title = "Donald"
        }
    }

    given("the desire to use an existing Java class with getters and setters") {
        it("it exposes the getters and setters as Kotlin properties") {
            val kotlinInAction = JavaBook("Kotlin in Action", true)
            kotlinInAction.title shouldEqual "Kotlin in Action"
            kotlinInAction.isAvailableOnSafari shouldEqual true
            kotlinInAction.isAvailableOnSafari = false
            kotlinInAction.isAvailableOnSafari shouldEqual  false
        }
    }

    given("A class with some normal properties ") {
        it("can provide a custom accessor for a property that is computed and not stored") {
            class Person(val name: String, var age : Int) {
                val canBuyAlcohol: Boolean
                    get() = age >= 21
            }

            val joe = Person("Joe", 20)
            joe.canBuyAlcohol shouldEqual false

            val mark = Person("Mark", 30)
            mark.canBuyAlcohol shouldEqual true
        }
    }
})