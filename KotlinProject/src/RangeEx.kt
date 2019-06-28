fun main(args:Array<String>) {
    rangeEx()
    rangeEx2()
    rangeStream()
}

fun rangeEx() {
    var intRange = IntRange(0, 10)
    var charRange = CharRange('a', 'e')
    var longRange = LongRange(11, 20)

    for (i in intRange) {
        println(i)
    }

    for(c in charRange) {
        println(c)
    }

    for(l in longRange) {
        println(l)
    }
}

fun rangeEx2() {
    var intRange2 = 0..10
    var charRange2 = 'a'..'e'

    for (i in intRange2) {
        println(i)
    }

    for(c in charRange2) {
        println(c)
    }
}

fun rangeStream() {
    var list = (0..50).filter { it % 2 == 0 } // 2의 배수 골라내기
            .filter { it > 20 } // 20이상 골라내기
            .sortedByDescending { it }

    println(list)
}