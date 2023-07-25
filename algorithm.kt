fun repeatWord(input:String, n:Int): String  {
    var answer:String = ""
    for (i in 1..n) {
        if (1 <= n && n <= 10) {
            answer += input
        }
    }
    return(answer)
}

fun main() {
    val result = repeatWord("Kotlin", 3)
    println(result)
}
