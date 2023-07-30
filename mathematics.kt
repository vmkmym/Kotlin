// 재귀 함수의 반복적 방법
fun fibonacciIterative(n: Int): Int {
    if (n <= 1) return n

    var prev = 0
    var current = 1

    for (i in 2..n) {
        val next = prev + current
        prev = current
        current = next
    }

    return current
}

// 재귀적 방법은 피보나치수열 계산에 비효율적, 반복적 방법을 더 선호
fun fibonacciRecursive(n: Int): Int {
    return when (n) {
        0 -> 0
        1 -> 1
        else -> fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2)
    }
}

// 등비수열
fun exponential(n: Int): Int {
    if (n == 0) {
        return 1
    }
    return 2 * exponential(n-1)
}

for(i in 1..10) {
    println("i == $i : ${exponential(i)}")
}

// 피보나치수열
fun fibo(n: Int) : Int {
    if(n == 1 || n== 2) {
        return 1
    }

    return fibo(n-1) + fibo(n-2)
}

for(i in 1..10){
    println("fibo i==$i , ${fibo(i)}")
}
