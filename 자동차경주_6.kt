// 되는 코드
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
    val inputWinningDistance = readWinningDistance()

    val cars = inputNames.split(",").map { Car(it) }

    println("우승결과입력")
    println(inputWinningDistance)
    println("실행 결과")

    while (!cars.any { it.position >= inputWinningDistance }) {
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

private fun readWinningDistance(): Int {
    println("우승 조건으로 사용할 거리를 입력하세요.")
    return try {
        val input = Console.readLine().toInt()
        if (input <= 0) throw IllegalArgumentException()
        input
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException()
    }
}
