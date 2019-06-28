interface Parent1 {
    val str:String

    fun info() {
        println("Parent 1")
    }
}

interface Parent2 {
    val str:String

    fun info() {
        println("Parent 2")
    }
}

class Child : Parent1, Parent2 {
    override val str = "Child.str"

    override fun info() {
        super<Parent1>.info()
        super<Parent2>.info()
    }
}

fun main(args:Array<String>) {
    val child = Child()
    child.info()
}