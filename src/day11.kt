import java.io.File

fun determineAdjacentSeats(seatMap: MutableList<MutableList<Char>>, rowIndex: Int, colIndex: Int): Int {
    var numOccupied = 0
    for (i in -1..1) {
        for (j in -1..1) {
            if (i == 0 && j == 0) {
                continue
            }
            val newRow = rowIndex + i
            val newCol = colIndex + j

            if (newRow < 0 || newRow >= seatMap.size || newCol < 0  || newCol >= seatMap[rowIndex].size) {
                continue
            }
            if (seatMap[newRow][newCol] == '#') {
                numOccupied++
            }
        }
    }
    return numOccupied
}

fun determineVisibleSeats(seatMap: MutableList<MutableList<Char>>, rowIndex: Int, colIndex: Int): Int {
    var numOccupied = 0

    fun checkPos(rowIndex: Int, colIndex: Int): Boolean {
        when {
            seatMap[rowIndex][colIndex] == 'L' -> {
                return@checkPos true
            }
            seatMap[rowIndex][colIndex] == '#' -> {
                numOccupied++
                return@checkPos true
            }
            else -> {
                return@checkPos false
            }
        }
    }

    // up
    for (i in rowIndex - 1 downTo 0) {
        if (checkPos(i, colIndex)) {
            break
        }
    }

    // down
    for (i in rowIndex + 1 until seatMap.size) {
        if (checkPos(i, colIndex)) {
            break
        }
    }

    // left
    for (i in colIndex - 1 downTo 0) {
        if (checkPos(rowIndex, i)) {
            break
        }
    }

    // right
    for (i in colIndex + 1 until seatMap[rowIndex].size) {
        if (checkPos(rowIndex, i)) {
            break
        }
    }

    // up left
    var pos = Pair(rowIndex - 1, colIndex - 1)
    while (pos.first >= 0 && pos.second >= 0) {
        if (checkPos(pos.first, pos.second)) {
            break
        }
        pos = Pair(pos.first - 1, pos.second - 1)
    }

    // up right
    pos = Pair(rowIndex - 1, colIndex + 1)
    while (pos.first >= 0 && pos.second < seatMap[pos.first].size) {
        if (checkPos(pos.first, pos.second)) {
            break
        }
        pos = Pair(pos.first - 1, pos.second + 1)
    }

    // down left
    pos = Pair(rowIndex + 1, colIndex - 1)
    while (pos.first < seatMap.size && pos.second >= 0) {
        if (checkPos(pos.first, pos.second)) {
            break
        }
        pos = Pair(pos.first + 1, pos.second - 1)
    }

    // down right
    pos = Pair(rowIndex + 1, colIndex + 1)
    while (pos.first < seatMap.size && pos.second < seatMap[pos.first].size) {
        if (checkPos(pos.first, pos.second)) {
            break
        }
        pos = Pair(pos.first + 1, pos.second + 1)
    }

    return numOccupied
}

// Returns if seats have changed
fun simulateRound(seatMap: MutableList<MutableList<Char>>,
                  findNumOccupied: (seatMap: MutableList<MutableList<Char>>, rowIndex: Int, colIndex: Int) -> Int,
                  threshold: Int = 4): Boolean {
    val changes = HashSet<Pair<Int, Int>>()

    seatMap.forEachIndexed { rowIndex, row ->
        row.forEachIndexed columns@{ colIndex, _col ->
            if (seatMap[rowIndex][colIndex] == '.') {
                return@columns
            }
            val numOccupied = findNumOccupied(seatMap, rowIndex, colIndex)
            if ((numOccupied == 0 && seatMap[rowIndex][colIndex] == 'L') || (numOccupied >= threshold && seatMap[rowIndex][colIndex] == '#')) {
                changes.add(Pair(rowIndex, colIndex))
            }
        }
    }

    changes.forEach {
        val currentChar = seatMap[it.first][it.second]
        seatMap[it.first][it.second] = if (currentChar == 'L') '#' else 'L'
    }

    return changes.size > 0
}

fun findNumOccupied(seatMap: MutableList<MutableList<Char>>): Int {
    return seatMap.fold(0) { acc, value ->
        acc + value.fold(0) { _acc, char ->
            _acc + (if (char == '#') 1 else 0)
        }
    }
}

fun findFinalSeatCount() {
    val seatMap = mutableListOf<MutableList<Char>>()
    File("data/day11.txt").forEachLine {
        val row = mutableListOf<Char>()
        it.forEach { jt ->
            row.add(jt)
        }
        seatMap.add(row)
    }


    var shouldSimulate = true
    while (shouldSimulate) {
        shouldSimulate = simulateRound(seatMap, ::determineAdjacentSeats)
    }
    println(findNumOccupied(seatMap))
}

fun findFinalSeatCountVisibility() {
    val seatMap = mutableListOf<MutableList<Char>>()
    File("data/day11.txt").forEachLine {
        val row = mutableListOf<Char>()
        it.forEach { jt ->
            row.add(jt)
        }
        seatMap.add(row)
    }


    var shouldSimulate = true
    while (shouldSimulate) {
        shouldSimulate = simulateRound(seatMap, ::determineVisibleSeats, 5)
    }
    println(findNumOccupied(seatMap))
}

fun main(args: Array<String>) {
//    findFinalSeatCount()
    findFinalSeatCountVisibility()
}