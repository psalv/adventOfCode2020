import java.io.File

val PREAMBLE_LENGTH = 25

fun getAllSumValues(numberStream: MutableList<Long>, index: Int): HashMap<Long, Int> {
    val map = HashMap<Long, Int>()
    for(i in ((index - PREAMBLE_LENGTH).coerceAtLeast(0))..index) {
        val key = numberStream[index] + numberStream[i]
        map[key] = i.coerceAtLeast(map[key] ?: 0)
    }
    return map
}

fun findFirstDoesNotSum() {
    val numberStream = mutableListOf<Long>()
    File("data/day9.txt").forEachLine {
        numberStream.add(it.toLong())
    }
    val validNumberToEarliestPosition = HashMap<Long, Int>()

    for(i in 0 until PREAMBLE_LENGTH) {
        val sums = getAllSumValues(numberStream, i)
        sums.entries.forEach {
            validNumberToEarliestPosition[it.key] = (it.value).coerceAtLeast(validNumberToEarliestPosition[it.key] ?: -1)
        }
    }

    for(i in PREAMBLE_LENGTH..numberStream.size) {
        if (validNumberToEarliestPosition[numberStream[i]] == null
                || validNumberToEarliestPosition[numberStream[i]]!! < i - PREAMBLE_LENGTH) {
            println("found first at pos $i value ${numberStream[i]}")
            return
        }
        val sums = getAllSumValues(numberStream, i)
        sums.entries.forEach {
            validNumberToEarliestPosition[it.key] = (it.value).coerceAtLeast(
                    validNumberToEarliestPosition[it.key] ?: -1
            )
        }
    }
}

fun checkFromIndex(index: Int, numberStream: MutableList<Long>, numRemaining: Long): Int {
    if (numRemaining == 0.toLong()) {
        return index
    }

    if (index < 0 || numRemaining < 0) {
        return -1
    }

    return checkFromIndex(index - 1, numberStream, numRemaining - numberStream[index])

}

fun findEncryptionWeakness() {
    val INVALID_NUMBER: Long = 375054920

    val numberStream = mutableListOf<Long>()
    File("data/day9.txt").forEachLine {
        numberStream.add(it.toLong())
    }

    for (i in (numberStream.size - 1) downTo 0) {
        if (numberStream[i] >= INVALID_NUMBER) {
            continue
        }

        val lowestIndex = checkFromIndex(i, numberStream, INVALID_NUMBER)
        if (lowestIndex != -1) {
            val lowestNumber = numberStream.subList(lowestIndex, i + 1).min() ?: 0
            val highestNumber = numberStream.subList(lowestIndex, i + 1).max() ?: 0

            println("sum ${lowestNumber + highestNumber}")
            return
        }
    }
}

fun main(args: Array<String>) {
//    findFirstDoesNotSum()
    findEncryptionWeakness()
}