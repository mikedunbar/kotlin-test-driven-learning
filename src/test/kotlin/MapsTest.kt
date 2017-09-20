import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it

/**
 * @author Mike Dunbar
 */
class MapsTest : Spek ({
    given("a map") {
        val map = mutableMapOf(1 to 'A', 2 to 'B', 3 to 'C')
        it("can unpack both keys and values when iterating") {
            val keyList = mutableListOf<Int>()
            val valList = mutableListOf<Char>()
            for((key, value) in map) {
                keyList.add(key)
                valList.add(value)
            }
            keyList shouldEqual listOf(1,2,3)
            valList shouldEqual listOf('A', 'B', 'C')
            map[2] shouldEqual 'B' // just to highlight map setting/getting value notation
        }
        it("allows access/update with get/put like Java") {
            map.get(2) shouldEqual 'B'
            map.put(2, 'Z')
            map.get(2) shouldEqual 'Z'
        }
        it("allows access/update with index notation") {
            map[2] = 'B'
            map[2] shouldEqual 'B'
        }
    }
})