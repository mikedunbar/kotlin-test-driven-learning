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
class ClassesAndInterfacesTest : Spek ({
    given("an interface") {

        it("makes methods abstract by default") {
           // Uncomment below to see compile error
           //class Button : Clickable {}
        }

        it("makes methods public by default") {

        }


        it("can be implemented with the ':' modifier") {
            class Button : Clickable {
                override fun click() = 20
            }
            Button().click() shouldEqual 20
        }

        it("is mandatory to use the 'override' keyword in implementing classes") {
            // Remove 'override' modifier above to see error
        }

        it("can have default method implementations") {
            class Blah : Clickable {
                override fun click() = 10
            }
            Blah().speak() shouldEqual "hello"
        }

        it("allows for the overriding of default methods") {
            class Blah : Clickable {
                override fun click() = 10
                override fun speak() = "goodbye"
            }
            Blah().speak() shouldEqual "goodbye"
        }
    }

    given("a class") {
        class MyClass {
            fun speak() = "hi"
        }

        it("Is final/closed by default") {
            // Uncomment below to see error
            //class Other : MyClass()
        }

        it("can implement multiple interfaces but only extend one class") {
              // Uncomment below to see error
//            class Other : Clickable, Findable, Square(), Circle() {
//                override fun click(): Int {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun find(): Int {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//            }

        }
    }
})

interface Clickable {
    fun click() : Int
    fun speak() : String = "hello"
}

interface Findable {
    fun find(): Int
}



open class Square {
    fun area() = 30
}

open class Circle {
    fun radius()= 10
}



