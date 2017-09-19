import com.package1.Dog
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import com.package1.Person
import com.package1.findDrinkingBuddy
import com.package2.*

@RunWith(JUnitPlatform::class)
class PackagingImportTest : Spek({

    given("The need to to use a function or class defined in another package") {
        it("Can be explicitly imported and used") {
            // Note import of class person atop file
            val joe = Person("joe", 20)
            val mary = Person("Mary", 30)
            val friends: List<Person> = listOf(joe, mary)

            // Note import of function findDrinkingBuddy atop file
            findDrinkingBuddy(friends) shouldEqual mary

        }
    }

    given("The need to use several items defined in another package") {
        it("Can be done with a single 'star import'") {
            // Note star import of com.package2 above, and use of class, function, and property below
            appleRipeness shouldEqual 5
            val banana = Fruit("banana", 9)
            val grape = Fruit("grape", 10)

            firstRipeFruit(10, listOf(banana, grape)) shouldEqual grape
        }
    }

    given("The desire to define packages in Kotlin") {
        it("doesn't force package names to mirror directory structure like Java") {
            // Note that com.package2 is defined in the top-level src/main/kotlin directory
        }
    }

    given("The desire to define multiple top-level classes in Kotlin") {
        it("doesn't require them to be in their own files or match the file title") {
            // Note that dog is defined in the same top-level file as Person, neither class title
            // matches the file title either
            Dog("fido", 5).bark()
        }
    }
})