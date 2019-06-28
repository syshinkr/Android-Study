import java.util.function.BiConsumer

fun main(args:Array<String>) {
    listEx()
    arrayListEx()
    streamOperation()
    mapEx()
    hashMapEx()
}

fun listEx() {
    var list1 = List(5, { i -> i + i})
    println(list1)

    var list2 = listOf(0, 1, 2, 3, 4)
    println(list2)

    for(idx in 0..list2.size-1) {
        println(list2[idx])
    }
}

fun arrayListEx() {
    var list1 = ArrayList<Int>()
    list1.add(0)
    list1.add(1)
    println(list1)

    var list2 = arrayListOf(0, 1, 2, 3, 4)
    list2[0] = 10
    list2.add(20)
    println(list2)
}

fun streamOperation() {
    var list = arrayListOf(0, 1, 2, 3, 4, 5)

    var newList = list.filter { it > 2 } //2보다 큰 수만 남기기
            .map { it + 1 } // 모든 요소에 + 1
            .sortedByDescending { it } //내림차순 정렬

    println(newList)
}

fun mapEx() {
    var map = mapOf(Pair("A", "Person 1"),
            Pair("B", "Person 2"),
            Pair("C", "Person 3"))

    println(map["A"])
    println(map["B"])
}

fun hashMapEx() {
    var map = hashMapOf<String, String>()

    map["A"] = "Joongsoo"
    map["B"] = "Younghwan"
    map["C"] = "Minji"

    map.filter { it.value.startsWith("Y") || it.value.startsWith("M")}
            .forEach{ _, u ->  println(u)}

}