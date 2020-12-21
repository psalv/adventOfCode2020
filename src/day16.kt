import java.io.File

fun allowRange1(validPositions: MutableList<Boolean>, allowedRange: List<Int>) {
    while (validPositions.size <= allowedRange[1]) {
        validPositions.add(false)
    }
    for (i in allowedRange[0]..allowedRange[1]) {
        validPositions[i] = true
    }
}

fun scannedErrorRate() {
    val validPositions = mutableListOf<Boolean>()
    // 0 = do not read
    // 1 = read ranges
    // 2 = read tickets
    var readType = 1
    var errorRate = 0
    File("data/day16.txt").forEachLine readLine@{
        when {
            it.isEmpty() -> {
                readType = 0
                return@readLine
            }
            it.startsWith("nearby tickets") -> {
                readType = 2
                return@readLine
            }
            readType == 1 -> {
                val (_, ranges) = it.split(":").map{jt -> jt.trim()}
                val rangeArray = ranges.split("or").map{jt -> jt.trim()}
                rangeArray.forEach {jt ->
                    allowRange1(validPositions, jt.split('-').map{jjt -> jjt.toInt()})
                }
            }
            readType == 2 -> {
                val nArray = it.split(',').map{jt -> jt.toInt()}
                nArray.forEach{jt ->
                    run {
                        if (jt >= validPositions.size || !validPositions[jt]) {
                            errorRate += jt
                        }
                    }
                }
            }
        }
    }
    println(errorRate)
}

fun allowRange2(validPositions: MutableList<HashSet<String>>, key: String, allowedRange: List<Int>) {
    while (validPositions.size <= allowedRange[1]) {
        validPositions.add(HashSet())
    }
    for (i in allowedRange[0]..allowedRange[1]) {
        validPositions[i].add(key)
    }
}

fun determineOrdering(validPositions: MutableList<HashSet<String>>,
                      validTickets: MutableList<List<Int>>,
                      index: Int = 0,
                      usedKeys: HashSet<String> = HashSet()
): MutableList<String>? {
    // forEach possibility
    //   choose a possibility
    //   if this possibility satisifies every ticket at this level, recurse, propagating any non null values and adding on
    //   if we go through all options and none have returned true, return false

    if (index >= validTickets[0].size) {
        return mutableListOf()
    }

    for(i in validPositions[validTickets[0][index]]) {
        if (usedKeys.contains(i) || validTickets.any { !(validPositions[it[index]].contains(i)) }) {
            continue
        }
        usedKeys.add(i)
        val partialOrdering = determineOrdering(validPositions, validTickets, index + 1, usedKeys)
        if (partialOrdering != null) {
            partialOrdering.add(i)
            return partialOrdering
        }
        usedKeys.remove(i)
    }
    return null
}

fun determineCorrectFields() {
    val validPositions = mutableListOf<HashSet<String>>()
    var myTicket: List<Int>? = null
    // 0 = do not read
    // 1 = read ranges
    // 2 = read tickets
    // 3 = my ticket
    var readType = 1
    val validTickets = mutableListOf<List<Int>>()
    File("data/day16.txt").forEachLine readLine@{
        when {
            it.isEmpty() -> {
                readType = 0
                return@readLine
            }
            it.startsWith("nearby tickets") -> {
                readType = 2
                return@readLine
            }
            it.startsWith("your ticket") -> {
                readType = 3
                return@readLine
            }
            it.startsWith("nearby tickets") -> {
                readType = 2
                return@readLine
            }
            readType == 1 -> {
                val (key, ranges) = it.split(":").map{jt -> jt.trim()}
                val rangeArray = ranges.split("or").map{jt -> jt.trim()}
                rangeArray.forEach {jt ->
                    allowRange2(validPositions, key, jt.split('-').map{jjt -> jjt.toInt()})
                }
            }
            readType == 2 -> {
                val nArray = it.split(',').map{jt -> jt.toInt()}
                if (!nArray.any{jt -> jt >= validPositions.size || validPositions[jt].isEmpty()}) {
                    validTickets.add(nArray)
                }
            }
            readType == 3 -> {
                myTicket = it.split(',').map{jt -> jt.toInt()}
            }
        }
    }

    val ordering = determineOrdering(validPositions, validTickets)?.asReversed()

    var product = 1
    for (i in 0 until ordering!!.size) {
        if (ordering!![i].startsWith("departure")) {
            product *= myTicket!![i]
        }
    }
    println(product)
}

fun main(args: Array<String>) {
//    scannedErrorRate()
    determineCorrectFields()
}