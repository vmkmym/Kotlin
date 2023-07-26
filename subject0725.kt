import java.util.Stack
fun main2(input: String): List<String> {
    // 임시저장한 단어 저장할 리스트 result 생성
    val result = mutableListOf<String>()
    // 임시저장할 변수 초기화
    var tmp = ""

    // 반복 (공백을 만날 때 까지) 순회하면서 char를 tmp에 저장
    // 공백을 만나면 tmp의 값을 word에 저장하고
    for (word in input) {
        if (word != ' ') {
            tmp += word
        } else {
            // 만약 tmp이 비지 않았다면 result에 추가 저장후 tmp 초기화
            if (tmp.isNotEmpty()) {
                result.add(tmp)
                tmp = ""
            }
        }
    }
    // 마지막 공백 이후 출력이 되지 않아서 한 번 더 조건문
    if (tmp.isNotEmpty()) {
        result.add(tmp)
    }
    // 원하는 결과 반환 (split 메서드)
    return result
}

// 문자열 뒤집는 함수
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

fun main() {
    // split 메서드 구현한 결과 확인
    val input = "my name is k"
    val result = main2(input)
    println(result)
    // 그래서 리스트로 받은 문자열 단어만 뒤집어서 단어순서는 그대로 출력
    val revResult = reverseWord(result)
    println(revResult)
}
