fun main(args: Array<String>) {
    useIf()
    useWhen()
    digit(100) // when 메소드
}

fun useIf() {
    //if문
    var num = if(0 == 1) {
        println("0과 1은 같다")
        1
    } else {
        println("0과 1은 다르다")
        0
    }

    println(num)

    var res = if(0 == 1) "같다" else "다르다"

    println(res)
}

fun useWhen() : Unit{
    //when문
    var c = 10
    var cArray:IntArray = intArrayOf(6,7,8,9)

    when(c) {
        0 -> println("0입니다")
        5 -> println("5입니다")
        10 -> println("10입니다")
        else -> {
            println("해당되는 게 없습니다")
            println("중괄호를 통해 다중 코드 작성 가능")
        }
    }

    when(c) {
        0, 1 -> println("0 또는 1")
        in 2..5 -> println("2와 5 사이")
        in cArray -> println("cArray 배열에 속함")
        !in cArray -> println("cArray 배열에 속하지 않음")
    }

    var unknownObject: Any = "ABCDEFG"

    when(unknownObject) {
        is TestClass -> println(unknownObject.print())
        is String -> println(unknownObject.length)
        is Int -> println(unknownObject.minus(10))
    }
}
fun digit(num:Int) = when(num) {
    in 0..9 -> "한자릿수"
    in 10..99 -> "두자릿수"
    else -> "범위 밖"
}
class TestClass {
    fun print() {
        println("TestClass의 print메소드")
    }
}