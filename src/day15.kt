
fun memoryGame(countUntil: Int = 2020) {
    val memoryObject = HashMap<Int, Int>()
    val startingNumbers = arrayOf(9,12,1,4,17,0,18)
    for(i in startingNumbers.indices) {
        memoryObject[startingNumbers[i]] = i + 1
    }
    var counter = startingNumbers.size
    var lastNumber = startingNumbers.last()
    while (counter < countUntil) {
        val prevCounter = memoryObject[lastNumber]
        val nextNumber = if (prevCounter != null) counter - prevCounter else 0
        memoryObject[lastNumber] = counter
        lastNumber = nextNumber
        counter++
    }
    println("$counter $lastNumber")
}

fun main(args: Array<String>) {
    memoryGame(30000000)
}