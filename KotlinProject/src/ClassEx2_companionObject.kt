class CompanionTestClass {
    val str1 = "Hello"
    val str3 = "Kotlin"

    fun sum(a:Int, b:Int) = a+b

    companion object Companion{
        val str2 = "HI"
        val str3 = "USA"
        fun minus(a:Int, b:Int) = a-b
    }
}

class CompanionTestClass2 {
    companion object {
        val str = "Hi"
    }
}

fun main(args:Array<String>) {
    var obj = CompanionTestClass()
    println(obj.str1)
    println(obj.sum(10, 5))

    println(CompanionTestClass.minus(10, 5))
    println(CompanionTestClass.Companion.minus(10, 5))

    println(CompanionTestClass.str2)
    println(CompanionTestClass.Companion.str2)

    println(CompanionTestClass.str3)
    println(CompanionTestClass.Companion.str3)


    println(CompanionTestClass2.str)
}