package foo

var myVar = "mine"
val yours = "yours"

fun topLevelFunction() : String = "This is a top-level function"

fun String.extensionFunc() = length

fun callableFromJava(someFunc: (Int) -> Int) = {"someFunc returned ${someFunc(2)} when called with 2"}
