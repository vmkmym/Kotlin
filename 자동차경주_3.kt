package racingcar

import camp.nextstep.edu.missionutils.Console
import camp.nextstep.edu.missionutils.Randoms

data class Car(val name: String) {
    var position: Int = 0

    fun move() {
        if (Randoms.pickNumberInRange(0, 9) >= 4) {
            position++
        }
    }

    fun display(): String {
        return "$name : ${"-".repeat(position)}"
    }
}

fun main() {
    val inputNames = readCarNames()
    val inputAttempts = readAttempts()

    val cars = inputNames.split(",").map { Car(it) }

    println("\n실행 결과")

    repeat(inputAttempts) {
        cars.forEach { it.move() }
        cars.forEach { println(it.display()) }
        println()
    }

    val maxPosition = cars.map { it.position }.maxOrNull()

    val winners = cars.filter { it.position == maxPosition }.joinToString(", ") { it.name }

    println("최종 우승자: $winners")
}

private fun readCarNames(): String {
    println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)")
    val inputNames = Console.readLine()
    if (inputNames.split(",").any { it.length > 5 }) {
        throw IllegalArgumentException()
    }
    return inputNames
}

private fun readAttempts(): Int {
    println("시도할 횟수는 몇 회인가요?")
    return try {
        val input = Console.readLine().toInt()
        if (input <= 0) throw IllegalArgumentException()
        input
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException()
    }
}

// at racingcar.ApplicationTest.전진_정지$lambda$0(ApplicationTest.kt:16)
// 	at camp.nextstep.edu.missionutils.test.Assertions.lambda$assertRandomTest$4(Assertions.java:89)
// 	at org.junit.jupiter.api.AssertTimeout.lambda$assertTimeoutPreemptively$2(AssertTimeout.java:102)
// 	at org.junit.jupiter.api.AssertTimeout.lambda$assertTimeoutPreemptively$4(AssertTimeout.java:138)
// 	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
// 	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
// 	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
// 	at java.base/java.lang.Thread.run(Thread.java:833)