@file:Suppress("unused")

// Test 1: Keyword Highlighting
// Copy this into the editor and verify that keywords are blue and bold

fun greet(name: String) {
    val message = "Hello"
    var count = name.length

    if (count > 0) {
        println("$message, $name!")
        for (i in 1..3) {
            println("Iteration $i")
        }
    } else {
        println("Empty name")
    }

    while (count < 5) {
        count++
        if (count == 3) {
            return
        }
    }
}

class Person(val name: String) {
    fun introduce() {
        println("I am $name")
    }
}

object Singleton {
    @Suppress("UNUSED")
    fun doSomething() {
        println("Singleton method")
    }
}

// Use the Person class and Singleton object
val person = Person("Alice")
person.introduce()
Singleton.doSomething()

greet("Tester")
