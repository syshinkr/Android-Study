import java.util.*

fun main(args:Array<String>) {
    var arr:Array<Int> = arrayOf(0, 1, 2, 3)

    println(Arrays.toString(arr))

    var intArr = intArrayOf(0, 1, 2, 3)
    var doubleArr = doubleArrayOf(1.0, 2.0, 3.0)
    var booleanArr = booleanArrayOf(true, false)
    var charArr = charArrayOf('a', 'b', 'c')

    var array = Array(5, { i -> i.toString()})

    println(array is Array<String>)
    println(Arrays.toString(array))
}