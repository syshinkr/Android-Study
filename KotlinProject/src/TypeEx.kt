fun main(args:Array<String>) {
    var oneLiineStr = "한줄 문자열 선언"

    var multiLineStr = """
        |여러줄 문자열 선언
        |두줄
        |세줄
        """

    println(multiLineStr.trimMargin())

    //문자열 = char의 배열
    var str = "abcdefg"

    println(str[0])

    for(c in str) {
        println(c)
    }

    val myName = "name"
    val introduce = "제 이름은 $myName 입니다"
    val namelength = "제 이름은 ${myName.length} 글자입니다"

    //디렉토리 파일 목록을 가져온다
    var files = java.io.File("c:\\nothing").list() // null
    println(files?.size)
}