// Merge Sort Descending
fun main() {
    val desArray = intArrayOf(55, 22, 98, 12, 54, 64, 59, 63)
    val sortedArray = mergeSortAlgorithm(desArray)
    println(sortedArray.contentToString())
}

fun mergeSortAlgorithm(desArray: IntArray): IntArray {
    if (desArray.size <= 1) {
        return desArray
    }
    // 배열을 잘라서 새로운 배열로 생성하는 확장 함수 .copyOfRange(범위시작, 범위끝)
    val midElement = desArray.size / 2
    val leftArray = desArray.copyOfRange(0, midElement)
    val rightArray = desArray.copyOfRange(midElement, desArray.size)
    
    // mergeSortAlgorithm() 함수를 재귀적으로 호출 (분할 정복이라고 함)
    val sortLeft = mergeSortAlgorithm(leftArray)
    val sortRight = mergeSortAlgorithm(rightArray)

    // merge() 호출해서 왼, 오 합쳐서 정렬된 배열을 반환한다
    return merge(sortLeft, sortRight)
}

// 분할한 배열을 내림차순 정렬된 형태로 다시 병합하는 merge()함수 만들기
fun merge(leftArray: IntArray, rightArray: IntArray): IntArray {
    var i = 0
    var j = 0
    val desArray = IntArray(leftArray.size + rightArray.size)    
    var k = 0
    while (i < leftArray.size && j < rightArray.size) {
        // 만약 i가 j보다 크다면 desArray에 저장하고 i, k를 증가
        if (leftArray[i] >= rightArray[j]) {
            desArray[k++] = leftArray[i++]
        } else {
            // i가 j보다 크지 않다면 desArray에 저장하고 j, k를 증가
            desArray[k++] = rightArray[j++]
        }
    }
    // 그리고 남은 left의 남아있는 i를 desArray에 저장하고 i, k를 증가
    while (i < leftArray.size) {
        desArray[k++] = leftArray[i++]
    }
    // 그리고 남은 right의 남아있는 k를 desArray에 저장하고 j, k를 증가
    while (j < rightArray.size) {
        desArray[k++] = rightArray[j++]
    }

    return desArray
}
