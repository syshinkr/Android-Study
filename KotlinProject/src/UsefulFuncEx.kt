class  WithTestClass {
    var number = 10

    fun sum(a:Int, b:Int) = a+b
}

fun main(args:Array<String>) {
    useWith()
    useRunAnonymous()
    useRunObjectCall()
}

fun useWith() {
    var testClass = WithTestClass()

    var num = with(testClass) {
        number += 10

        sum(10, number)
    }
    println(num)
}

fun useRunAnonymous() {
    var num2 = run {
        1 + 2
    }
    println(num2)

    run {
        var a = 10
        println(a)
    }
}

fun useRunObjectCall() {
    var testObj = WithTestClass()

    var num = testObj.run {
        number += 20
        sum(number, 20)
    }

    println(20)
}