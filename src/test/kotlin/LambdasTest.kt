import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.math.BigInteger

@RunWith(JUnitPlatform::class)
class LambdasTest : Spek ({

    describe("lambdas with receivers") {

        context("The 'with' function") {

            it("specifies the receiver value, which methods are called on - explictly with 'this' or implicitly") {
                fun alphabet() : String {
                    val builder = StringBuilder()
                    return with(builder) {
                        for (letter in 'A'..'Z') {
                            this.append(letter) // explicit call to receiver
                        }
                        append("\nNow I know my abc's") // implicit call to receiver
                        toString()
                    }
                }
                alphabet() shouldEqual "ABCDEFGHIJKLMNOPQRSTUVWXYZ\nNow I know my abc's"
            }

            it("takes the receiver and the lambda as arguments. The lambda can also be specified inside the method call parens") {
                fun alphabet() : String {
                    val builder = StringBuilder()
                    return with(builder, {
                        for (letter in 'A'..'Z') {
                            this.append(letter) // explicit call to receiver
                        }
                        append("\nNow I know my abc's") // implicit call to receiver
                        toString()
                    })
                }
                alphabet() shouldEqual "ABCDEFGHIJKLMNOPQRSTUVWXYZ\nNow I know my abc's"
            }

            it("returns the result of the lambda as a value") {
                fun compute() : Int {
                    return with(BigInteger.valueOf(5), {
                        add(java.math.BigInteger.valueOf(7)).intValueExact()
                    })
                }

                compute() shouldEqual 12
            }

        }

        context("the 'apply' function") {
            it("works like 'with', except that the receiver object is returned from the lambda") {
                fun alphabet() : String {
                    return StringBuilder().apply {
                        for (letter in 'A'..'Z') {
                            append(letter)
                        }
                        append("\nNow I know my abc's")
                    }.toString()
                }
                alphabet() shouldEqual "ABCDEFGHIJKLMNOPQRSTUVWXYZ\nNow I know my abc's"
            }
        }
    }
})