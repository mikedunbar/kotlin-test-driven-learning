import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class NullabilityTest : Spek ({
    given("the desire to mark something nullable") {
        it("can be done by putting a '?' after the normal type name") {
            var nullStringVar : String?

            var nullListVar: List<String>?

            var nullCustomTypeVar : CustomType?
        }
    }

    given("a variable of non-null type") {
        var myVar: String

        it("cannot directly be assigned null") {
            // uncomment to see error
            //myVar = null
        }

        it("cannot be assigned a nullable variable") {
            var nullableVar: String?
            // uncomment to see error
            //myVar = nullableVar
        }
    }

    given("A nullable variable") {
        var myVar: String?

        it("is nullable") {
            myVar = null
        }

        it("can of course also be assigned a non-null value") {
            myVar = "hey"
        }

        it("cannot be passed as an argument for a non-nullable parameter") {
            fun doIt(name: String) = name.length

            // uncomment to see error
            //doIt(myVar)
        }

    }


    given("a nullable parameter") {
        fun strLen(s: String?) = s?.length

        it("cannot directly call methods, but must either use a safe call or a non-null asserted called") {
            // Safe call
            fun strLen1(s: String?) = s?.length

            // Non-null asserted call
            fun strLen2(s: String?) = s!!.length
        }

        it("Is OK to pass it null") {
            // uncomment to see error
            strLen(null)
        }

        it("will be remembered as non-null in the checking scope, after a null check") {
            fun strLen(s: String?) : Int {
                if (s != null)
                    return s.length
                else
                    return 0
            }
        }
    }



})


class CustomType
