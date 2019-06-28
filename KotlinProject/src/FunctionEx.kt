fun main(args:Array<String>) {
    val a:Int = sum(10, 13)
    println(a)

    printNumbers(0, 1, 2, 3)
}

fun sum(a:Int, b:Int) = a+b

fun printNumbers(vararg numbers:Int) {
    for(num in numbers) {
        println(num)
    }
}