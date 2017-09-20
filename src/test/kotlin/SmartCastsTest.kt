import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * @author Mike Dunbar
 */
@RunWith(JUnitPlatform::class)
class SmartCastsTest : Spek ({
    given("The need to check the type of a variable") {
        val e: Expr = Num(7)
        it("uses an 'is' check and allows a 'smart cast' when the check passes") {
            if (e is Num) {
                e.value shouldEqual 7
            }
            else {
                throw RuntimeException()
            }

        }
    }

    given("The need to write a function that casts variables") {
        it("Can be written in Java style") {
            fun eval(e: Expr): Int {
                if (e is Num) {
                    val n = e as Num // Explicit cast not needed
                    return e.value
                }
                if (e is Sum) {
                    return eval(e.left) + eval(e.right) // Smart cast like this can be used
                }
                throw IllegalArgumentException("Unknown expression")
            }
            eval(Sum(Num(7), Num(3))) shouldEqualTo 10
        }
        it("Can be done in the Kotlin style, where if statements have a value") {
            fun eval(e: Expr): Int =
                    when (e) {
                        is Num -> e.value
                        is Sum -> eval(e.left) + eval(e.right)
                        else -> throw IllegalArgumentException("unknown expression")
                    }
            eval(Sum(Num(7), Num(3))) shouldEqualTo 10
        }
    }
})

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr
