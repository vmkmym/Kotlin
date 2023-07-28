//  선택 정렬 내림차순 구현
fun selectionSort(mySort: MutableList<Int>): List<Int> {
    // 주어진 가변리스트의 길이를 input 변수에 저장한다
    var input = mySort.size - 1
    // 반복한다, i가 0부터 input까지
    for (i in 0..input) {
        // 최댓값인 요소의 인덱스 maxI 는 i에 할당된다
        var maxI = i 
    	
        // 반복한다, i+1부터 input.size까지
        for (j in i+1 until input) {
            // 만약 mySort[j] > mySort[maxI]
            if (mySort[j] > mySort[maxI]) {
                // maxI에 j를 다시 할당한다
                maxI = j
            }
        }
        // i와 최댓값을 가진 요소의 위치를 교환
        mySort.swap(i, maxI)
    }
    return mySort
}
​
// swap 개념 잘 이해하기 (임시변수 만들어서)
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}
​
// 선택 정렬 내림차순 출력
fun main() {
    val unsorted = mutableListOf(55, 22, 98, 12, 54, 64, 59, 63)
    val sorted = selectionSort(unsorted)
    println(sorted)
}
