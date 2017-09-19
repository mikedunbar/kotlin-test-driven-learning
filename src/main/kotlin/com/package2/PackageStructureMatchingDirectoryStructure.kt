package com.package2

class Fruit(val name: String, val age: Int)

fun firstRipeFruit(ripeAge: Int, fruitBasket: List<Fruit>): Fruit? {
    return fruitBasket.find {
        it.age >= ripeAge
    }
}

val appleRipeness = 5