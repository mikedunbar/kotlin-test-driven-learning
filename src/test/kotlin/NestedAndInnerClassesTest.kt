import org.amshove.kluent.shouldBeInstanceOf
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
class NestedAndInnerClassesTest : Spek({

    given("a class defined inside another class") {
        it("is nested/'Java static' by default - instances contain no reference to an instance of the enclosing class") {
            val outerWithNested = OuterWithNested()
            val nestedInside = OuterWithNested.Inner()
            outerWithNested shouldNotEqual nestedInside.getOuterRef()
        }

        it("must be marked as 'inner' to have instances contain a reference to an instance of the enclosing class") {
            val outerWithInner = OuterWithInner()
            val innerInside = outerWithInner.Inner()
            outerWithInner shouldEqual innerInside.getOuterRef()
        }
    }

    given("An outer class with the sealed modifier") {
        it("Only permits subclasses defined inside it as nested classes") {
            SealedOuter.SubclassOne() shouldBeInstanceOf SealedOuter::class

            // Uncomment below to see error
            //class SubclassThree : SealedOuter()
        }

        it("Permits omitting default branches in a when clause") {
            fun getString(x: SealedOuter) =
                when (x) {
                    is SealedOuter.SubclassOne -> "one"
                    is SealedOuter.SubclassTwo -> "two"
                }

            getString(SealedOuter.SubclassOne()) shouldEqual "one"
            getString(SealedOuter.SubclassTwo()) shouldEqual "two"
        }
    }
})

class OuterWithNested {
    class Inner {
        fun getOuterRef() : OuterWithNested {
            // Uncomment below to see error
            //return this@Outer

            return OuterWithNested()
        }
    }
}

class OuterWithInner {
    inner class Inner {
        fun getOuterRef() : OuterWithInner {
            return this@OuterWithInner
        }
    }
}

sealed class SealedOuter {
    class SubclassOne: SealedOuter()
    class SubclassTwo: SealedOuter()
}