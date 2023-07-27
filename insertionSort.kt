fun insertionSort(mySort: MutableList<Int>): List<Int> {
    var i: Int
    var key: Int

    for (j in 1 until mySort.size) {
        key = mySort[j]
        i = j - 1

        while (i >= 0 && mySort[i] > key) {
            mySort[i + 1] = mySort[i]
            i--
        }
        mySort[i + 1] = key
    }

    return mySort
}

fun main() {
    val unsorted = mutableListOf(55, 22, 98, 12, 54, 64, 59, 63)
    val sorted = insertionSort(unsorted.toMutableList())
    println(sorted)
}
