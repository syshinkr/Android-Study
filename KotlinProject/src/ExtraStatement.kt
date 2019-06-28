typealias StringArrayList = ArrayList<String>

fun main(args:Array<String>) {
    var strArrayList = StringArrayList()

    strArrayList.add("Hi")
    strArrayList.add("hello")

    println(strArrayList)

    var filter = Filter()
    var list = listOf(0, 5, 10, 15, 20, 25, 30)

    var newList = list.filter (filter::over10 )
    println(newList)
}

class Filter {
    fun over10(i:Int) = i > 10
}