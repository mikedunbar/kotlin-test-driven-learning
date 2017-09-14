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
class ClassBasics : Spek({

    given("The need to define a class containing only properties") {
        it("Can be done very concisely in Kotlin, where properties are first class citizen") {
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

        it("provides only an accessor for the var property") {
            mike.name shouldEqual "Mike"
            //uncomment to see compile error
            //mike.name = "Donald"
        }
    }

    given("the desire to use an existing Java class with getters and setters") {
        it("it exposes the getters and setters as Kotlin properties") {
            val kotlinInAction = JavaBook("Kotlin in Action", true);
            kotlinInAction.title shouldEqual "Kotlin in Action"
            kotlinInAction.isAvailableOnSafari shouldEqual true
            kotlinInAction.isAvailableOnSafari = false
        }


    }

})