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

        it("can define properties") {

        }

        it("makes methods abstract by default") {
           // Uncomment below to see compile error
           //class Button : Clickable {}
        }

        it("makes methods public by default") {

        }

        it("can be implemented with the ':' modifier, which replaces both 'implements' and 'extends' in Java") {
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

    given("two interfaces with the same method definition") {
        it("Is required that a class implementing both provide an implementation of that method") {
            class SomeClass : Clickable, Findable {
                override fun click() = 1
                override fun find() = 2

                // Comment out implementation of speak, to see compile error
                override fun speak() = "have spoken"
            }

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

        it("has methods that are all final by default") {
            open class MyClass {
                fun speak() = "hi"
                open fun move() = 7
            }

            class Other : MyClass() {
                override fun move() = 3

                // Uncomment below to see error
                //override fun speak() = "goodbye"
            }
        }

        it("uses the override modifier to override methods and properties") {
            class MySquare : Square (10) {
                override fun area() = 400
            }
            MySquare().area() shouldEqual 400
        }

        it("by default leaves overridden methods as overridable") {
            open class MySquare : Square (10) {
                override fun area() = 400
            }

            class YourSquare : MySquare () {
                override fun area() = 500
            }
        }

        it ("Can explicitly mark overridden methods as final") {
            open class MySquare : Square (10) {
                final override fun area() = 400
            }

            class YourSquare : MySquare () {
                // Uncomment below to see error
                //override fun area() = 500
            }
        }

        it("uses the super keyword to access an overridden implementation") {
            class MySquare : Square(5) {
                override fun area() = super<Square>.area() + 10
            }
        }

        it("can implement multiple interfaces but only extend one class") {
              // Uncomment below to see error
//            class Other : Clickable, Findable, Square(), Circle() {
//                override fun click(): Int {}
//
//                override fun find(): Int {}
//            }

        }
    }

    given("An abstract method defined in an abstract class") {
        it ("Is open by default") {
            abstract class Shape {
                abstract fun area() : Int // no open modifier needed
            }

            class Square (val size: Int) : Shape() {
                override fun area() = size * size
            }
        }
    }

    given("The need to perform more initialization with a primary constructor") {
        it("can be done with an initializer blocks") {
            class Person(name: String, age: Int) {
                val name: String
                val age: Int

                init {
                    this.name = name
                }

                init {
                    this.age = age
                }

            }
        }

        val mike = Person("Mike", 25)
        mike.age shouldEqual 25
    }

    given("a constructor with default param values") {
        class Person(val name: String = "Mike")

        it("can be constructed without params") {
            val mike = Person()
            mike.name shouldEqual "Mike"
        }
    }

    given("A class with a superclass") {
        open class Base(val baseItem: String)

        it("must initialize the superclass if the superclass has a constructor") {
            class Sub(subItem: String) : Base(subItem)

            val mySub = Sub("myItem")
            mySub.baseItem shouldEqual "myItem"
        }

        it("must call the superclass's empty constructor if the superclass does not have a constructor") {
            open class BaseWithoutConstructor {
                val item = "the item"
            }

            class Sub : BaseWithoutConstructor()

            Sub().item shouldEqual "the item"
        }

    }

    given("the desire to prevent clients from constructing instances of a class") {
        it("Can be done with a private constructor") {
            class Secret private constructor ()

            // Uncomment below to see error
            //Secret()
        }
    }

    given("a super class with multiple secondary constructors") {
        open class Base {
            constructor(name: String)

            constructor(name: String, authorized: Boolean)
        }

        it("can be extended by a class mirroring the base's secondary constructors") {
            class Sub : Base {
                constructor(name: String) : super(name) {

                }

                constructor(name: String, authorized: Boolean) : super(name, authorized){

                }
            }
        }
    }

    given("The desire to call one constructor from another") {
        it("can be done with the 'this' keyword, just like in Java") {
            open class MyClass {
                val name: String
                val authorized: Boolean

                constructor(name: String) : this(name, false)

                constructor(name: String, authorized: Boolean) {
                    this.name = name
                    this.authorized = authorized
                }

            }

            MyClass("Tom").authorized shouldEqual false
        }
    }

    given("An interface with an abstract property") {
        it("Can be implemented with a primary constructor property") {
            class ClassA(override val nickname: String) : WithProperty
        }
        it("Can be implemented with a custom getter") {
            class ClassB(val email: String) : WithProperty {
                override val nickname: String
                    get() = email.substringBefore("@")
            }
        }
        it("Can be implemented with a property initializer") {
            class ClassC(val accountId: Int) : WithProperty {
                override val nickname = getFbUsername(accountId)

                private fun getFbUsername(accountId: Int) = "blah-${accountId}"

            }
        }

    }

})

interface WithProperty {
    val nickname: String
}

interface Clickable {
    fun click() : Int
    fun speak() : String = "hello"
}

interface Findable {
    fun find(): Int
    fun speak() : String = "goodbye"
}


open class Square(val side: Int) {
    open fun area() = side * side
}

open class Circle {
    fun radius()= 10
}



