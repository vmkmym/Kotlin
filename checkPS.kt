import java.util.Stack

fun main() {
    println(checkPS("()"))      
    println(checkPS("()()"))
    println(checkPS("(())()"))
    println(checkPS("today is friday"))
    // 괄호만 잘 구성되면 true로 출력
    println(checkPS("(32, 64, 128, 256, 512)"))
    println(checkPS("(())(()"))
    println(checkPS("{}{}{{[}}"))
    println(checkPS("]"))
    println(checkPS("[][{}][()}]"))

}


fun checkPS(input: String): Boolean {
    val stack = mutableListOf<Char>()

    // 문자열의 첫 번째 문자가 여는 괄호가 아니라면 무조건 false를 반환
    if (input[0] != '(' && input[0] != '{' && input[0] != '[') {
        return false
    }

    for (ch in input) {
        if (ch == '(' || ch == '{' || ch == '[') {
            stack.add(ch) // '('가 들어오면 stack에 추가
        } else if (ch == ')' || ch == '}' || ch == ']') {
            // 닫는 괄호가 들어오는데 스택이 비어있다면 false 반환
            if (stack.isEmpty()) {
                return false
            } else {
                stack.removeAt(stack.size - 1) 
                // 스택에서 가장 최근에 추가된 요소 제거
            }
        }
        // 그 외의 다른 문자열인 경우는 모두 무시
    }

    // 반복문을 모두 수행한 후에 스택이 비어있으면 true를 반환
    return stack.isEmpty()
}
