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
    return Console.readLine()
}

private fun readAttempts(): Int {
    println("시도할 횟수는 몇 회인가요?")
    return try {
        val input = Console.readLine().toInt()
        if (input <= 0) throw IllegalArgumentException()
        input
    } catch (e: NumberFormatException) {
        println("잘못된 입력입니다. 시도 횟수는 양의 정수여야 합니다.")
        readAttempts()
    } catch (e: IllegalArgumentException) {
        println("잘못된 입력입니다. 시도 횟수는 1 이상이어야 합니다.")
        readAttempts()
    }
}
