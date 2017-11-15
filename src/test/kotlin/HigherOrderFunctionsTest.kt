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
})

fun callableFromJava(someFunc: (Int, Int) -> Int) = {"someFunc returned ${someFunc(2, 3)} when called with 2 and 3"}