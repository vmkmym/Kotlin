import java.util.Stack

fun reverseWord(input: List<String>): String {
    var answer = ""
    val stack = Stack<Char>()

    for (i in input) {
        for (j in i) {
            stack.push(j)
        }
        while (!stack.isEmpty()) {
            answer += stack.pop()
        }
        answer += " "
    }

    return answer
}

reverseWord(listOf("hi", "hellow", "bye"))

// 리스트 요소 안에 요소를 순회하여 문자열 뒤집기 (홍진님 support)
