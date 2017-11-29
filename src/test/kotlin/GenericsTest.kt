import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class GenericsTest : Spek({

    describe("generic type parameters") {
        it("is possible to infer the type arguments when creating a non-empty collection") {
            val mapNamesToPerson = mapOf(Pair("Mike", Person("Mike", 42)), Pair("Jon", Person("Jon", 52)))
            mapNamesToPerson.keys.elementAt(0) shouldBeInstanceOf  String::class
            mapNamesToPerson["Jon"] shouldBeInstanceOf Person::class
        }

        it("requires specifying the type argument when creating an empty collection") {
            // 2 options
            val mapNameToPerson : Map<String, Person> = emptyMap()
            val mapNameToPerson2 = emptyMap<String, Person>()
        }

        it("is not possible to declare a Java-like 'raw type' in Kotlin") {
            // uncomment for error
            //val rawList : List
        }
    }

    describe("generic functions and properties") {
        context("generic functions") {
            it("is possible to declare type parameters on top-level functions") {
                fun <T> helloToString(input: T) = "Hello ${input.toString()}"
            }

            it("is possible to declare type parameters on class and interface methods") {
                //see Seer and Blob below
            }

            it ("is possbile to declare type parameters on extension functions") {
                fun <T> List<T>.penultimate() : T = this[size - 2]
            }
        }

        context("generic properties") {
            it("is possible to declare generic extension properties") {
                //see List<T>.penultimate below
            }

            it("is NOT possible to declare a generic non-extension property (a regular property") {
                //see SomeClass below, uncomment for error
            }
        }
    }

    describe("Declaring generic classes and interfaces") {
        it("Is done by providing the params in angle brackets after the class or interface name") {
            //see GenericClass and MyList below
        }


        it("is required to provide a type argument when implementing a generic interface or extending a generic class") {
            // Uncomment for error
            //class SomeList : MyList


            class StringList : MyList<String> {
                override fun elementAt(index: Int) ="blah"

            }
        }

        it("is possible to specify another type parameter for that type argument") {
            class ArrayBackedList<T> : MyList<T> {
                override fun elementAt(index: Int): T {
                    TODO("not implemented")
                }
            }
        }
    }

    describe("Type parameter constraints, which allow you to restrict the types that can be used as arguments for a class or function") {
        context("Upper bound constraints") {
            it("requires the type argument to be the corresponding type, or a subtype") {
                //TODO use reduce to implement sum
                fun <T: Number> List<T>.sum() : T  = this.elementAt(0)
                listOf(1,2,3).sum() shouldEqual 1

                // uncomment for error
                //listOf("red", "blue").sum()

            }

            it("is possible to declare multiple constraints for a type argument") {
                fun <T> ensureTrailingPeriod(seq: T)
                    where T : CharSequence, T : Appendable {

                }
            }
        }

        context("Making type parameters non-null") {
            it("happens that type params without constraint have an upper bound of Any?, so can be null") {
                class Processor<T> {
                    fun process(value: T) = value?.hashCode() // null safety required, remove to see error
                }
            }

            it("is possible to declare a non-nullable type, by giving Any as the upper bound") {
                class Processor<T : Any> {
                    fun process(value: T) = value.hashCode() // null not required
                }

                // uncomment for 'type arg not within bounds' error
                //val nullProcessor = Processor<String?>()
            }
        }
    }

    describe("generics at runtime") {
        it("uses type erasure, just like Java") {

        }

        it("isn't possible to check the type of the type argument") {
            val list = listOf("one", "two")

            fun holdsString(aList: List<Any?>) : Boolean {
                //uncomment for compiler errors
                //return aList is List<String>
                return false
            }
        }

        it("is possible to check that we have a list, as opposed to a Set e.g., using * projection") {
            val list = listOf(1,2)

            fun isList(aCollection: Collection<Any>) : Boolean = aCollection is List<*>

            isList(list) shouldEqual true
        }
    }

    describe("casting generic types") {
        fun sumIt(c: Collection<*>) : Int {
            // Note warning below
            val intList = c as? List<Int> ?: throw IllegalArgumentException("int list expected")
            return intList.sum()
        }

        it("is possible and gives a compile-time warning") {
            val list = listOf(1,2,3)
            //See warning in sumIt
            sumIt(list) shouldEqual 6
        }

        it("even succeeds (the cast) with a non-integer list, and fails only when doing the sum") {
            var caught: Exception
            try {
                sumIt(listOf("a", "b"))
                caught = RuntimeException("test should already have failed")
            } catch (e: Exception) {
                caught = e
            }
            caught.javaClass shouldEqual ClassCastException().javaClass
        }

        it("fails when trying to cast a non-list collection") {
            var caught: Exception
            try {
                sumIt(setOf(1,2))
                caught = RuntimeException("test should already have failed")
            } catch (e: Exception) {
                caught = e
            }
            caught.javaClass shouldEqual IllegalArgumentException().javaClass
        }
    }

    describe("Declaring function with reified types") {


    }



})

interface Seer {
    fun <T> seeIt(): Set<T>

}

class Blob {
    fun <T, R> beBloblike(x: T, y: R) : Unit = println("x: $x and y: $y")
}

val <T> List<T>.penultimate: T
    get() = this[size - 2]

class SomeClass (val name: String) {
    val nameAsString: String
        get() = this.name.toString()

    //val <T> whatever: T
}

class GenericClass<T> (val x: T) {
    fun getIt() : T = x.apply { println("noting") }

}

interface MyList<T> {
    fun elementAt(index: Int) : T
}
