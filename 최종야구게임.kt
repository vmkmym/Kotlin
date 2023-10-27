package baseball

import camp.nextstep.edu.missionutils.Console
import camp.nextstep.edu.missionutils.Randoms.pickNumberInRange

fun main() {
    baseballGame()
}

fun baseballGame() {
    val numberSet: MutableSet<String> = mutableSetOf()
    var playGame = true

    println("숫자 야구 게임을 시작합니다.")

    while (playGame) {
        getThreeNumber(numberSet)
        while (true) {
            print("숫자를 입력해주세요 : ")
            val inputNumber = getUserInput()
            val result = gameResults(inputNumber, numberSet)
            println(result)

            if (result == "3스트라이크") {
                println("3개의 숫자를 모두 맞히셨습니다! 게임 종료")
                break
            }
        }
        playGame = makeGameChoice()
    }
}

fun getThreeNumber(threeNumber: MutableSet<String>) {
    threeNumber.clear()
    while (threeNumber.size < 3) {
        val number = pickNumberInRange(1, 9)
        if (number.toString() !in threeNumber) {
            threeNumber.add(number.toString())
        }
    }
}

fun getUserInput(): String {
    while (true) {
        val inputNumber = Console.readLine()
        if (inputNumber.length == 3) {
            return inputNumber
        } else {
            throw IllegalArgumentException("잘못된 입력값입니다.")
        }
    }
}

fun makeGameChoice(): Boolean {
    println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.")
    return when (Console.readLine()) {
        "1" -> true
        "2" -> false
        else -> throw IllegalArgumentException("잘못된 입력값입니다.")
    }
}

fun gameResults(inputNumber: String, numberSet: MutableSet<String>): String {
    val inputNumIdx = inputNumber.toList().map { it.toString() }
    val strike = inputNumIdx.zip(numberSet).count { (inputIdx, targetNumIdx) -> inputIdx == targetNumIdx }
    val ball = inputNumIdx.count { it in numberSet } - strike

    return when {
        strike > 0 && ball > 0 -> "${ball}볼 ${strike}스트라이크"
        strike > 0 -> "${strike}스트라이크"
        ball > 0 -> "${ball}볼"
        else -> "낫싱"
    }
}