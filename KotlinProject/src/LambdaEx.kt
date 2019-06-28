fun invokeFunction1(f: ()->Unit) {
    f()
}

fun invokeFunction2(num:Int, f: ()->String) {
    println("인자로 받은 숫자 : $num")
    var returnValue = f()
    println(returnValue)
}

fun invokeFunction3(f: (Int, Int) -> Int) {
    val returnValue = f(10, 20)
    println(returnValue)
}

fun invokeFunction4(f: (Int) -> String) {
    val returnValue = f(10)
    println(returnValue.length)
}

fun sum2(a:Int, b:Int) = a+b
fun minus2(a:Int, b:Int) = a-b

fun main(args:Array<String>) {
    invokeFunction1 { println("콜백 함수 실행") }
    invokeFunction2(10, {
        println("콜백 함수 여러 줄 실행")
        "리턴 문자열"
    })

    invokeFunction3({a, b ->
        val c = a + b
        c
    })

    invokeFunction3(::sum2)

    invokeFunction4 {
        println("단일 매개변수 it의 값 : $it")
        it.toString()
    }

    val minus3:(Int, Int) -> Int = {a,b -> a-b}
    val minus4:(Int, Int) -> Int = ::minus2

    println(minus2(10, 1))
    println(minus3(10, 1))
    println(minus4(10, 1))
}