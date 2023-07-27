import java.util.*
​
fun mixAlphabet(input: Int): Char? {
    val alphaQueue = LinkedList<Char>(
        listOf(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
        )
    )
​
    for (i in 0 until input) {
        val del1 = alphaQueue.poll()
        val del2 = alphaQueue.poll()
        alphaQueue.offer(del2)
    }
​
    return alphaQueue.peek()
}
​
fun main() {
    println(mixAlphabet(1) == 'c') 
    println(mixAlphabet(2) == 'e') 
    println(mixAlphabet(3) == 'g')
}
