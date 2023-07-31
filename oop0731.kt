// 코틀린 객체지향 프로그래밍 연습
open class Coffee(val name: String, val price: Int, val ingredient: String) {
    open fun info() {
        println("이름: $name, 가격: $price 원, 재료: $ingredient")
    }
}

class Americano(name: String, price: Int, ingredient: String) : Coffee(name, price, ingredient) {
    override fun info() {
        println("메뉴 이름: $name, 가격: $price 원, 들어간 재료: $ingredient")
    }

    // 오버로딩된 메서드 추가
    fun info(extra: String) {
        println("메뉴 이름: $name, 가격: $price 원, 들어간 재료: $ingredient, $extra")
    }
}

class CafeLatte(name: String, price: Int, ingredient: String) : Coffee(name, price, ingredient) {
    override fun info() {
        println("메뉴 이름: $name, 가격: $price 원, 들어간 재료: $ingredient")
    }
}

class MilkTea(name: String, price: Int, ingredient: String) : Coffee(name, price, ingredient) {
    // 슈퍼클래스의 info() 메서드와 시그니처가 동일한 오버로딩된 메서드를 추가
    fun info(extra: String) {
        println("메뉴 이름: $name, 가격: $price 원, 들어간 재료: $ingredient, $extra")
    }
}

fun main() {
    val americano1 = Americano("아메리카노", 3500, "에스프레소 2샷, 물")
    americano1.info("테이크아웃") // 매개변수를 전달하여 오버로딩된 메서드를 호출

    val cafeLatte = CafeLatte("카페라떼", 5500, "에스프레소, 우유")
    cafeLatte.info()

    val milktea2 = MilkTea("밀크티", 5000, "홍차잎, 물, 스팀우유")
    milktea2.info("휘핑크림 추가")
}
