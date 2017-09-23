import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * @author Mike Dunbar
 */
@RunWith(JUnitPlatform::class)
class WhenTest : Spek({

    given("a when construct, which is very similar to a Java switch statement") {
        it("is an expression and therefore has a value") {
            fun abbreviate(size: Size) : String {
                return when (size) {
                    Size.SMALL -> "s"
                    Size.MEDIUM -> "m"
                    Size.LARGE -> "l"
                    Size.EXTRA_LARGE -> "xl"
                }
            }
            abbreviate(Size.LARGE) shouldEqual "l"
        }
        it("Doesn't require break statements like Java"){
            fun addToList(size: Size, list: MutableList<String>) {
                when (size) {
                    Size.SMALL -> list.add("s")
                    Size.MEDIUM -> list.add("m")
                    Size.LARGE -> list.add("l")
                    Size.EXTRA_LARGE -> list.add("xl")
                }
            }

            val list = mutableListOf<String>()
            addToList(Size.MEDIUM, list)
            list shouldEqual listOf("m")
            list shouldNotEqual listOf("m", "l", "xl") // Result in Java when breaks are omitted

        }
        it("allows multiple values in the same branch to be 'ORed'") {
            fun describe(size: Size) : String {
                return when (size) {
                    Size.SMALL, Size.MEDIUM -> "small or medium"
                    Size.LARGE, Size.EXTRA_LARGE -> "large or extra-large"
                }
            }

            describe(Size.SMALL) shouldEqual "small or medium"
            describe(Size.EXTRA_LARGE) shouldEqual "large or extra-large"
        }
        it("permits arbitrary objects as branches, not just constant values") {
            fun mix(color1: Color, color2: Color) =
                when(setOf(color1, color2)) {
                    setOf(Color.RED, Color.YELLOW) -> Color.ORANGE
                    setOf(Color.YELLOW, Color.BLUE) -> Color.GREEN
                    setOf(Color.BLUE, Color.VIOLET) -> Color.INDIGO
                    else -> throw Exception("Dirty Bastard")
                }

            mix(Color.YELLOW, Color.RED) shouldEqual Color.ORANGE
            mix(Color.YELLOW, Color.BLUE) shouldEqual Color.GREEN
        }
        it("can be used without an argument, giving a boolean expression for each branch") {
            fun colorForInt(int: Int)  =
                    when {
                        (int == 1) -> Color.RED
                        (int == 4) -> Color.GREEN
                        else -> Color.VIOLET
                    }

            colorForInt(4) shouldEqual Color.GREEN
            colorForInt(-1) shouldEqual Color.VIOLET
        }
        it("can have blocks as branches") {
            fun colorForInt(int: Int) =
                    when {
                        (int == 1) -> {
                            println("red")
                            Color.RED
                        }
                        (int == 4) -> {
                            println("green")
                            Color.GREEN
                        }
                        else -> {
                            println("violet")
                            Color.VIOLET
                        }

                    }

            colorForInt(4) shouldEqual Color.GREEN
            colorForInt(-1) shouldEqual Color.VIOLET
        }
        it("requires multi-line branches to be in blocks/curly braces") {
            val list = mutableListOf<String>()
            fun funkyFunc(int: Int) =
                    when {
                        (int == 1) ->
                            list.add("one")
                            // uncomment below line to see error
                            //println("received a one")
                        else ->
                                println("unknown")
                    }
            funkyFunc(1)
            list shouldEqual listOf("one")
        }


    }
})

enum class Color {
    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
}