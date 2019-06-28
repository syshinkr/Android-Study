data class BookData(val title: String="title", val author: String, var price: Int)

fun main(args: Array<String>) {
    val bookData = BookData("Kotlin", "Daniel", 10000)
    println("책명 ${bookData.title} 저자 ${bookData.author} 가격 ${bookData.price}")

    val bookData2 = bookData.copy(price = 2000)
    println("책명 ${bookData2.title} 저자 ${bookData2.author} 가격 ${bookData2.price}")

    bookData2.price = 30000
    println("책명 ${bookData2.title} 저자 ${bookData2.author} 가격 ${bookData2.price}")
}
