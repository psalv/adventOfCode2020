import java.io.File
import java.lang.Math.ceil

fun findSeatId(partition: String): Int {
    var minRowValue = 0
    var maxRowValue = 127

    var minColValue = 0
    var maxColValue = 7

    var lastRowWasMin = false
    var lastColWasMin = false

    partition.forEach { jt ->
//        println("$jt $minRowValue $maxRowValue")
        when(jt) {
            'F' -> {
                maxRowValue = (kotlin.math.floor((minRowValue + maxRowValue) / 2.0)).toInt()
                lastRowWasMin = true
            }
            'B' -> {
                minRowValue = (kotlin.math.ceil((minRowValue + maxRowValue) / 2.0)).toInt()
                lastRowWasMin = false
            }
            'L' -> {
                maxColValue = (kotlin.math.floor((minColValue + maxColValue) / 2.0)).toInt()
                lastColWasMin = true
            }
            'R' -> {
                minColValue = (kotlin.math.ceil((minColValue + maxColValue) / 2.0)).toInt()
                lastColWasMin = false
            }
        }
    }

//    println("$minRowValue $maxRowValue $lastRowWasMin $minColValue $maxColValue $lastColWasMin")

    return (if (lastRowWasMin) minRowValue else maxRowValue) * 8 + (if (lastColWasMin) minColValue else maxColValue)
}

fun findHighestSeat() {
    var highestSeatId = -1

    File("data/day5.txt").forEachLine {
        val seatId = findSeatId(it)
        if (seatId > highestSeatId) {
            highestSeatId = seatId
        }
    }
    println(highestSeatId)
}

fun findMySeat() {
    val allSeats = arrayOfNulls<Boolean>(128 * 8)
    File("data/day5.txt").forEachLine {
        val seatId = findSeatId(it)
        allSeats[seatId] = true
    }
    for (seatId in 1..allSeats.size) {
        if (allSeats[seatId - 1] == true && allSeats[seatId] == null) {
            println(seatId)
        }
    }
}
fun main(args: Array<String>) {
//    findHighestSeat()
    findMySeat()
//    println(findSeatId("FBFBBFFRLR"))
}