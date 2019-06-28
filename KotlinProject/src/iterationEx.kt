fun main(args: Array<String>) {
    var arr = arrayOf("a", "b", "c")

    for(item in arr) {
        println(item)
    }
    //인덱스 받아오기
    for(index in arr.indices) {
        println(arr[index])
    }

    for(idx in 0..arr.size - 1) {
        println(arr[idx])
    }
    for((idx, value) in arr.withIndex()) {
        println("${idx} 번째 값 : ${value}")
    }
}