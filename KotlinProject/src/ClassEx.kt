fun main(args: Array<String>) {
    val calc = Calc()
    println(calc.minus(9, 5))

    val backpack = Backpack("뉴스타일", "검정")
    println(backpack)

    val number = SingletonClass.sum(1, 2)
    println(number)
}

// 생성자 없으므로 기본 생성자 생성
class Calc {
    fun sum(a: Int, b: Int):Int {
        return a+b
    }

    fun minus(a:Int, b:Int) = a-b
}

// 주 생성자 사용
class Backpack(brand:String, color:String) {
    init {
        println("브랜드는 ${brand}, ${color}")
    }
}

// 여러 생성자 처리는 다른 생성자에게 위임
class ClassBook() {
    var title:String = ""
    var author:String = ""

    constructor(title:String) : this() {
        this.title = title
    }
    constructor(title:String, author:String) : this(title) {
        this.author = author
    }
}

class getsetBook() {
    var title:String?
    get() {
        return title
    }
    set(value) {
        title = value
    }
}

object SingletonClass {
    val str = "Hello Kotlin"

    fun sum(a:Int, b:Int) = a+b
}