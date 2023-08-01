sealed class SaleResult {
    data class Success(val itemName: String, val price: Int) : SaleResult()
    data class Error(val errorMessage: String) : SaleResult()
    object InProgress : SaleResult()
}

fun sellItem(itemName: String, price: Int): SaleResult {
    // 물건을 판매하는 비즈니스 로직
    return if (itemName.isNotEmpty() && price > 0) {
        SaleResult.Success(itemName, price)
    } else {
        SaleResult.Error("물건 이름과 가격을 올바르게 입력해주세요.")
    }
}

fun main() {
    val result1 = sellItem("snack", 12000)
    val result2 = sellItem("", 500000)

    when (result1) {
        is SaleResult.Success -> println("판매 성공: ${result1.itemName}을 ${result1.price}원에 판매했습니다.")
        is SaleResult.Error -> println("판매 실패: ${result1.errorMessage}")
        is SaleResult.InProgress -> println("판매 진행 중...")
    }

    when (result2) {
        is SaleResult.Success -> println("판매 성공: ${result2.itemName}를 ${result2.price}원에 판매했습니다.")
        is SaleResult.Error -> println("판매 실패: ${result2.errorMessage}")
        is SaleResult.InProgress -> println("판매 진행 중...")
    }
}
