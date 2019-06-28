import kotlin.coroutines.experimental.buildSequence

fun main(args: Array<String>) {
    val seq = buildSequence {
        var num = 0

        while(true) {
            yield(num)
            num += 2
        }
    }

    seq.take(5).forEach {
        println(it)
    }

    val seqAll = buildSequence {
        var num = 0

        while(true) {
            yieldAll(num..num+2)
            num += 2
        }
    }

    val list = seqAll.take(5).toList()
    println(list)

    
}