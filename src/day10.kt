import java.io.File

fun findJoltageDistribution() {
    val numberStream = mutableListOf<Int>()
    File("data/day10.txt").forEachLine {
        numberStream.add(it.toInt())
    }
    numberStream.sort()

    val joltDifferences = HashMap<Int, Int>()
    joltDifferences[3] = 1 // Account for the last diff

    for (i in 0 until numberStream.size) {
        val diff = if (i == 0) numberStream[i] else numberStream[i] - (numberStream[i - 1] ?:0)
        joltDifferences[diff] = (joltDifferences[diff] ?: 0) + 1
    }
    println((joltDifferences[1] ?: 0) * (joltDifferences[3] ?: 0))
}

fun findNumLeaves(numberStream: MutableList<Int>, index: Int, memo: HashMap<Int, Long> = HashMap<Int, Long>()): Long {
    if (index == numberStream.size - 1) {
        return 1.toLong()
    }

    if (memo[index] != null) {
        return memo[index]!!
    }

    val currentJolts = numberStream[index]
    val size = numberStream.size

    val numLeaves = (
            findNumLeaves(numberStream, index + 1, memo)) +
            (if (index + 2 < size && (numberStream[index + 2] - currentJolts) <=3)
                findNumLeaves(numberStream, index + 2, memo)
            else 0) +
            (if (index + 3 < size && (numberStream[index + 3] - currentJolts) <=3)
                findNumLeaves(numberStream, index + 3, memo)
            else 0)

    memo[index] = numLeaves
    return numLeaves
}

fun findNumberOfArrangements() {
    val numberStream = mutableListOf<Int>()
    File("data/day10.txt").forEachLine {
        numberStream.add(it.toInt())
    }
    numberStream.add(0)
    numberStream.sort()
    println(findNumLeaves(numberStream, 0))
}

fun main(args: Array<String>) {
//    findJoltageDistribution()
    findNumberOfArrangements()
}