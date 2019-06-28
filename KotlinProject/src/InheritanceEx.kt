fun main(args:Array<String>) {
    val book = Book("Kotlin", "Daniel")
    val ebook = EBook("Kotlin", "Daniel", "https://devdogs.kr")

    book.printInfo()
    ebook.printInfo()

    val listener = object : OnClickListener() {
        override fun onClick() {
            println("익명 클래스 재정의")
        }
    }
}

open class Book() {
    var title:String = ""
    var author:String=""

    constructor(title:String) : this() {
        this.title = title
    }

    constructor(title:String, author:String) : this(title) {
        this.author = author
    }

    open fun printInfo() {
        println("제목은 ${title} 저자는 ${author}")
    }
}
// 주 생성자 상속
class EBook(title:String, author : String, var url: String?) : Book(title, author) {
    constructor() : this("", "", null)

    override fun printInfo() {
        println("제목은 ${title} 저자는 ${author} url은 ${url}")
    }
}

// 보조 생성자 상속
class E2Book : Book {
    var url:String = ""

    constructor() : super("", "")
    constructor(title:String, author: String, url: String) : super(title, author) {
        this.url = url
    }
}

open class OnClickListener {
    open fun onClick() {}
}