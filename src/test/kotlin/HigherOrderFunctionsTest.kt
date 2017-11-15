import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class HigherOrderFunctionsTest : Spek({
    describe("function types") {
        it("can be done with type inference") {
            var sum = {x: Int, y: Int -> x + y}
            var action = {println("hello")}
        }

        it("can be done with explicity types") {
            var sum:  (Int, Int) -> Int = {x, y -> x + y}
            var action: () -> Unit = {println("hello")}
        }

        it("can be have a nullable return type") {
            var canReturnNull: (Int, Int) -> Int? = {x,y -> null}
        }

        it("can itself be nullable") {
            var canBeNull: ((Int, Int) -> Int)? = null
        }

        it("can have named paramters") {
            var myFun: (len: Int, width: Int) -> Int = {len, width -> len * width}
            myFun.invoke(2, 4) shouldEqual 8
        }
    }

    describe("calling functions as passed arguments") {
        it("can be called like a regular functions") {
            fun twoAndFive(funcArg: (Int, Int) -> Int) = funcArg(2,5)
            twoAndFive({x, y -> x + y}) shouldEqual 7
            twoAndFive({x,y -> x * y}) shouldEqual 10
        }
    }

    describe("using function types from Java") {
        //see KotlinFromJavaTest, using callableFromJava below
    }

    describe("default values for function type params") {
        it("is done with an '=', just like any other param default") {
            fun convert(input: Int, converter: (Int) -> (Int) = {it + 1}) = converter(input)


            convert(5) shouldEqual 6
            convert(5, {it - 1}) shouldEqual 4
            convert(5) {it + 2} shouldEqual 7
        }
    }

    describe("null values for function type params") {
        it ("is done with a '?' - just like any other nullable param. Requires null handling to invoke") {
            fun convertMaybe(input: Int, converter: ((Int) -> (Int))?) = if (converter != null) converter(input) else input
            convertMaybe(10, {it + 1}) shouldEqual 11
            convertMaybe(10, null) shouldEqual 10

            fun convertMaybe2(input: Int, converter: ((Int) -> (Int))?) = converter?.invoke(input) ?: input
            convertMaybe2(10, {it + 1}) shouldEqual 11
            convertMaybe2(10, null) shouldEqual 10

        }
    }

    describe("returning functions from functions") {
        it("is done with a function as the return type, no special syntax") {
            fun getLogic(x: Int) : () -> String {
               if (x < 10) {
                   return {"low"}
               }
               return {"high"}
            }
            getLogic(9).invoke() shouldEqual "low"
            getLogic(20).invoke() shouldEqual "high"
        }
    }

    describe("control flow in higher-order functions") {

        it("treats a 'return' stmt in a lambda as a return from the function where you called the lambda." +
                "This is called a non-local return, because it returns from more than the local block") {
            fun findOrangeString(stringList: List<String>) : String {
                stringList.forEach{
                    if (it.toLowerCase() == "orange") {
                        return it
                    }
                }
                return "not found"
            }

            findOrangeString(listOf("blue", "orange", "ball")) shouldEqual "orange"
        }

        it("is possible to do a local return from a lambda, similar to 'break'ing out of a for loop" +
                ", and is done with a label") {
            fun findOrangeString(stringList: List<String>) : String {
                stringList.forEach label@{
                    if (it.toLowerCase() == "orange") {
                        return@label
                    }
                }
                return "not sure"
            }
            findOrangeString(listOf("blue", "orange")) shouldEqual "not sure"

        }

        it("allows any identifier for the label name, not just 'label'") {
            fun findOrangeString(stringList: List<String>) : String {
                stringList.forEach trump@{
                    if (it.toLowerCase() == "orange") {
                        return@trump
                    }
                }
                return "not sure"
            }
            findOrangeString(listOf("blue", "orange")) shouldEqual "not sure"
        }

        it("is possible to use the name of the function taking the lambda as a label") {
            fun findOrangeString(stringList: List<String>) : String {
                stringList.forEach{
                    if (it.toLowerCase() == "orange") {
                        return@forEach
                    }
                }
                return "not sure"
            }
            findOrangeString(listOf("blue", "orange")) shouldEqual "not sure"
        }

        it("is possible to label a lambda, and use that label to reference the lambda's receiver") {
            fun useLabel() = StringBuilder().apply mysb@{
                listOf(1,3,5).apply {
                    this@mysb.append(this)
                }
            }.toString()

            useLabel() shouldEqual "[1, 3, 5]"
        }


    }

    describe("an anonymous function") {
        it("is an alternative way to pass around code blocks") {
            var found = false
            fun findOrangeString(stringList: List<String>) {
                stringList.forEach(fun (str)  {
                    if (str.toLowerCase() == "orange") {
                        found = true
                        return
                    }
                    return
                })
            }

            findOrangeString(listOf("blue", "orange", "ball"))
            found shouldEqual true
            found = false // reset
            findOrangeString(listOf("green", "red"))
            found shouldEqual false
        }

        it("follows the same rules for specifying a return type, though it's name and parameter types are always omitted") {
            fun retainOnlyOrange(stringList: List<String>) : List<String> {
                return stringList.filter(fun (str) : Boolean {
                    return str.toLowerCase() == "orange"
                })
            }
            retainOnlyOrange(listOf("orange", "red")) shouldEqual listOf("orange")
            retainOnlyOrange(listOf("blue", "red")) shouldEqual emptyList()
        }

        it ("can omit the return type, when defined with an expression body") {
            fun retainOnlyOrange(stringList: List<String>) : List<String> {
                return stringList.filter(fun (str) : Boolean = str.toLowerCase() == "orange")
            }
            retainOnlyOrange(listOf("orange", "red")) shouldEqual listOf("orange")
            retainOnlyOrange(listOf("blue", "red")) shouldEqual emptyList()
        }
    }

    describe("how a 'return' statement (without a label) is resolved in nested scenarios") {
        it("returns from the nearest function declared with the 'fun' keyword") {
            var reached = false
            fun topLevel(strings: List<String>) {
                strings.forEach {if (it == "orange") return }
                reached = true
            }
            topLevel(listOf("orange"))
            reached shouldEqual false

            reached = false
            fun topLevel2(strings: List<String>) {
                strings.forEach(fun(str) { if (str == "orange") return})
                reached = true
            }
            topLevel2(listOf("orange"))
            reached shouldEqual true
        }
    }

})

fun callableFromJava(someFunc: (Int, Int) -> Int) = {"someFunc returned ${someFunc(2, 3)} when called with 2 and 3"}