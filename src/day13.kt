import java.io.File
import kotlin.math.absoluteValue

fun findEarliestBus() {
    var arrivalTime: Int? = null
    val availableBuses = mutableListOf<Int>()
    File("data/day13.txt").forEachLine {
        if (arrivalTime == null) {
            arrivalTime = it.toInt()
            return@forEachLine
        }
        it.split(',').forEach{
            jt ->  if (jt[0] != 'x') availableBuses.add(jt.toInt())
        }
    }

    var earliestTimeToWait = Int.MAX_VALUE
    var earliestBus: Int? = null
    availableBuses.forEach{
        val remainder = arrivalTime?.rem(it)
        if (remainder != null) {
            val timeToWait = it - remainder
            if (timeToWait < earliestTimeToWait) {
                earliestTimeToWait = timeToWait
                earliestBus = it
            }
        }
    }

    println(earliestBus?.times(earliestTimeToWait))
}

// CRT stolen from https://rosettacode.org/wiki/Chinese_remainder_theorem#Kotlin
/* returns x where (a * x) % b == 1 */
fun multInv(a: Long, b: Long): Long {
    if (b == 1.toLong()) return 1.toLong()
    var aa = a
    var bb = b
    var x0: Long = 0
    var x1: Long = 1
    while (aa > 1) {
        val q = aa / bb
        var t = bb
        bb = aa % bb
        aa = t
        t = x0
        x0 = x1 - q * x0
        x1 = t
    }
    if (x1 < 0) x1 += b
    return x1
}

fun chineseRemainder(n: LongArray, a: LongArray): Long {
    val prod = n.fold(1.toLong()) { acc, i -> acc * i }
    var sum: Long = 0
    for (i in n.indices) {
        val p = prod / n[i]
        sum += a[i] * multInv(p.toLong(), n[i].toLong()) * p
    }
    return (sum % prod).toLong()
}

fun findSubsequentBusDepartures() {
    var arrivalTime: Int? = null
    val availableBuses = mutableListOf<Pair<Long, Long>>()
    File("data/day13.txt").forEachLine {
        if (arrivalTime == null) {
            arrivalTime = it.toInt()
            return@forEachLine
        }
        it.split(',').forEachIndexed{
            index, jt ->  if (jt[0] != 'x') availableBuses.add(Pair(index.toLong(), jt.toLong()))
        }
    }

    println(availableBuses.map { it.second })
    println(availableBuses.map { it.second - it.first  })
    println(chineseRemainder(
            availableBuses.map { it.second }.toLongArray(),
            availableBuses.map { it.second - it.first }.toLongArray())
    )

}


fun main(args: Array<String>) {
//    findEarliestBus()
    findSubsequentBusDepartures()
}