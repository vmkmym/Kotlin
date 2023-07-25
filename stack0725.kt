// stack 이용해서 문자열 뒤집기. reverse() 쓰지 않고 만들기

fun reverseWord(input: String): String {
    val stack = mutableListOf<Char>()
    // 문자열에서 캐릭터를 stack 리스트에 더하기
    for (char in input) {
        stack.add(char)
    }
    
    // revStr 뒤집은 문자열
    var revStr = ""
    
    // stack 리스트가 빈 게 아니라면 중괄호를 반복한다.
    while (stack.isNotEmpty()) {
        // stack 리스트에서 하나씩 pop해서 revStr에 더해 저장
        revStr += pop(stack)
    }
    // 반환한다
    return revStr
}

// 스택 push, pop 연산
fun push(stack: MutableList<Char>, char: Char) {
    stack.add(char)
}

fun pop(stack: MutableList<Char>): Char {
    return stack.removeAt(stack.size - 1)
}
