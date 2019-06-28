fun main(args:Array<String>) {
    val number1:Int? = 0
    val number2:Int? = null

    number1?.let {
        println("number1은 null이 아닙니다")
        println(it.toString())
    }

    number2?.let {
        println("number2는 null이 아닙니다")
        println(it.toString())
    }

    val str1 = number1?.toString() ?: "Out"
    val str2 = number2?.toString() ?: "Out"

    println("$str1 $str2")

    val str3:String? = null
    str3 ?: str3.let {
        println("str3는 null이다")
    }

    val s = "2010년 09월 31일"
    val s2 = s.substring(0, 4) + s.substring(6, 8) + s.substring(10, 12)

    println(s2)
}