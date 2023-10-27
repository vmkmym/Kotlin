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
        throw IllegalArgumentException("잘못된 입력입니다. 자동차 이름은 5자 이하여야 합니다.")
    }
    return inputNames
}

private fun readAttempts(): Int {
    println("시도할 횟수는 몇 회인가요?")
    return try {
        val input = Console.readLine().toInt()
        if (input <= 0) throw IllegalArgumentException("잘못된 입력입니다. 시도 횟수는 1 이상이어야 합니다.")
        input
    } catch (e: NumberFormatException) {
        println("잘못된 입력입니다. 시도 횟수는 양의 정수여야 합니다.")
        readAttempts()
    }
}

// java.lang.AssertionError: 
// Expecting actual:
//   "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)
// 시도할 횟수는 몇 회인가요?

// 실행 결과
// pobi : -
// woni : 

// 최종 우승자: pobi"
// to contain:
//   ["pobi : -", "woni : ", "최종 우승자 : pobi"]
// but could not find:
//   ["최종 우승자 : pobi"]
 
// 	at racingcar.ApplicationTest.전진_정지$lambda$0(ApplicationTest.kt:16)
// 	at camp.nextstep.edu.missionutils.test.Assertions.lambda$assertRandomTest$4(Assertions.java:89)
// 	at org.junit.jupiter.api.AssertTimeout.lambda$assertTimeoutPreemptively$2(AssertTimeout.java:102)
// 	at org.junit.jupiter.api.AssertTimeout.lambda$assertTimeoutPreemptively$4(AssertTimeout.java:138)
// 	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
// 	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
// 	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
// 	at java.base/java.lang.Thread.run(Thread.java:833)

// 오류 메시지를 확인하니 테스트에서 예상한 출력과 실제 출력이 일치하지 않아 발생한 오류입니다. 
// 이 오류는 assertRandomNumberInRangeTest를 사용하여 실행되는 테스트에서 발생한 것으로 보입니다. 
// 이 테스트는 무작위 숫자를 사용하여 실행 결과를 확인하는데, 예상 출력을 제대로 검사하지 못하고 있습니다.