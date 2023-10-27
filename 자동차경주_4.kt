package racingcar
// build successsful 2s
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
    try {
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
    } catch (e: IllegalArgumentException) {
        System.exit(0)
    }
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