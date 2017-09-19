/**
 * @author Mike Dunbar
 */
class Person(val name: String, var age : Int) {
    val canBuyAlcohol: Boolean
        get() = age >= 21
}