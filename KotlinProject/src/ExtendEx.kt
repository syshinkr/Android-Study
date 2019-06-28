class Calculator {
    fun sum(a:Int, b:Int) = a+b
}
fun Calculator.sum(a:Int, b:Int, c:Int) = sum(a, b) + c

fun Calculator.minus(a:Int, b:Int) = a-b

fun main(args:Array<String>) {
    val calc = Calculator()
    
    println(calc.sum(1, 2, 3))
    println(calc.minus(10, 5))
}