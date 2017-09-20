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
class IteratingTest : Spek({
    given("A collection") {
        val list = listOf("one", "two", "three", "four")

        it("can be iterated with a while loop") {
            val newList  = mutableListOf<String>()
            val iter = list.listIterator()
            while (iter.hasNext()) {
                newList.add(iter.next())
            }
            newList shouldEqual list
        }

        it("can be iterated with a do while") {
            val newList  = mutableListOf<String>()
            var index = 0;
            do {
                newList.add(list.get(index))
                index++
            } while (list.size > index)
        }
    }
})