val stack = mutableListOf<Int>()

fun push(x: Int) {
    stack.add(x)
}

fun pop(): Int {
    return if (stack.isNotEmpty()) {
        stack.removeAt(stack.size - 1)
    } else {
        -1
    }
}

fun size(): Int {
    return stack.size
}

fun empty(): Boolean {
    return stack.isEmpty()
}

fun top(): Int {
    return if (stack.isNotEmpty()) {
        stack[stack.size - 1]
    } else {
        -1
    }
}

fun main() {
    val numCommands = readLine()!!.toInt() 

    repeat(numCommands) {
        val command = readLine()!!.split(" ")

        when (command[0]) {
            "push" -> push(command[1].toInt())
            "pop" -> println(pop())
            "size" -> println(size())
            "empty" -> println(if (empty()) 1 else 0)
            "top" -> println(top())
        }
    }
}
// 시간복잡도 수정.ver 백준 10828
