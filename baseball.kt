package baseball

import camp.nextstep.edu.missionutils.Console
import camp.nextstep.edu.missionutils.Randoms

fun main() {
   println("숫자 야구 게임을 시작합니다.")

   do {
       val computerNumbers = generateComputerNumbers()

       while (true) {
           val userNumbers = getUserNumbers()
           val result = calculateResult(computerNumbers, userNumbers)

           if (result == "3 스트라이크") {
               println("3개의 숫자를 모두 맞히셨습니다! 게임 종료")
               break
           } else {
               println(result)
           }
       }

       println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.")

   } while (Console.readLine().trim() == "1")
}

fun generateComputerNumbers(): List<Int> {
   val numbers = mutableListOf<Int>()
   while (numbers.size < 3) {
       val num = Randoms.pickNumberInRange(1, 9)
       if (!numbers.contains(num)) {
           numbers.add(num)
       }
   }
   return numbers
}

fun getUserNumbers(): List<Int> {
   while (true) {
       print("숫자를 입력해주세요 : ")
       val input = Console.readLine().trim()
       if (input.length != 3 || !input.all { it.isDigit() } || input.toSet().size != 3) {
           throw IllegalArgumentException()
       } else {
           return input.map { it.toString().toInt() }
       }
   }
}

fun calculateResult(computerNumbers: List<Int>, userNumbers: List<Int>): String {
   var strikes = 0
   var balls = 0

   for (i in computerNumbers.indices) {
       if (computerNumbers[i] == userNumbers[i]) {
           strikes++
       } else if (computerNumbers.contains(userNumbers[i])) {
           balls++
       }
   }

   return when {
       strikes > 0 && balls > 0 -> "$balls 볼 $strikes 스트라이크"
       strikes > 0 -> "$strikes 스트라이크"
       balls > 0 -> "$balls 볼"
       else -> "낫싱"
   }
}