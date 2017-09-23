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
class LoopsAndIterationsTest : Spek({
    given("The need to perform an action while a condition holds") {
        it("can test the condition first, then act in a while loop") {
            var count = 5
            var verify = 0
            while (count > 0) {
                verify++
                count--
            }
            verify shouldEqual 5
        }
        it("can act first, then test the condition in a do-while loop") {
            var count = 5
            var verify = 0
            do {
                verify++
                count--
            } while (count > 0)
            verify shouldEqual 5
        }
    }
    given("a range") {
        val closedRange = 1..10

        it("is 'closed' - includes the end, when defined with '..'") {
            val startIndex = closedRange.indexOf(1)
            val endIndex = closedRange.indexOf(10)
            val bogusIndex = closedRange.indexOf(22)

            startIndex shouldEqual 0
            endIndex shouldEqual 9
            bogusIndex shouldEqual -1
        }
        it("is half 'open' - does not include the end, when defined with 'until") {
            val halfOpenRange = 1 until 10
            val startIndex = halfOpenRange.indexOf(1)
            val endIndex = halfOpenRange.indexOf(10)
            val bogusIndex = halfOpenRange.indexOf(22)

            startIndex shouldEqual 0
            endIndex shouldEqual -1
            bogusIndex shouldEqual -1
        }

        it("is called a 'progression' if you can iterate over all it's values") {
            var count = 0
            for (i in closedRange) {
                count++
            }
            count shouldEqual 10
        }
        it ("can be defined over characters, not just numbers") {
            val list = mutableListOf<Char>()
            for (i in 'A'..'C') {
                list.add(i)
            }
            list shouldEqual listOf('A', 'B', 'C')
        }
    }
    given("a progression") {
        val oneToTen = 1..10
        val list = mutableListOf<Int>()
        it("can be given 'step' to skip some numbers") {
            for (i in oneToTen step 2) {
                list.add(i)
            }
            list shouldEqual listOf(1,3,5,7,9)
        }
        it("can run backwards and step") {
            list.clear()
            for (i in 10 downTo 1 step 2) {
                list.add(i)
            }
            list shouldEqual listOf(10, 8, 6, 4, 2)
        }
    }
    given("a collection") {
        val orig = listOf(5,4,3,2,1)
        val copy = mutableListOf<Int>()
        it("can be iterated with a for loop, similar to Java") {
            for(i in orig) {
                copy.add(i)
            }
            copy shouldEqual orig
        }
        it("can upack the index in addition to the value in a for loop") {
            val indices = mutableListOf<Int>()
            val elements = mutableListOf<Int>()
            for((index, element) in orig.withIndex()) {
                indices.add(index)
                elements.add(element)
            }
            indices shouldEqual listOf(0,1,2,3,4)
            elements shouldEqual orig
        }
    }
    given("a map") {
        val map = mapOf(1 to 'A', 2 to 'B', 3 to 'C')
        val keyList = mutableListOf<Int>()
        val valList = mutableListOf<Char>()
        it("can unpack both keys and values when iterating") {
            for((key, value) in map) {
                keyList.add(key)
                valList.add(value)
            }
            keyList shouldEqual listOf(1,2,3)
            valList shouldEqual listOf('A', 'B', 'C')
            map[2] shouldEqual 'B' // just to highlight map setting/getting value notation
        }
    }
})