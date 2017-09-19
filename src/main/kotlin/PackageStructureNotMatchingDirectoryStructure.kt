package com.package1

class Person(val name: String, var age : Int) {
    val canBuyAlcohol: Boolean
        get() = age >= 21
}

class Dog (val name: String, var age: Int) {
    fun bark()  = println("Woof! Woof!")
}

fun findDrinkingBuddy(people: List<Person>) : Person? {
    return people.find { it -> it.canBuyAlcohol }
}