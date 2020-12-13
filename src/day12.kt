import java.io.File
import kotlin.math.absoluteValue

fun getDirection(d: Int): Char {
    return when(d) {
        0 -> 'E'
        1 -> 'S'
        2 -> 'W'
        else -> 'N'
    }
}

fun findBoatDirection() {
    val directions = mutableListOf<Pair<Char, Int>>()
    File("data/day12.txt").forEachLine {
        directions.add(Pair(it[0], it.subSequence(1, it.length).toString().toInt()))
    }
    var lateral = 0
    var vertical = 0
    // E = 0, S = 1, W = 2, N = 3
    var cardinalDirection = 0

    directions.forEach {
        when(if (it.first == 'F') getDirection(cardinalDirection) else it.first) {
            'N' -> {
                vertical += it.second
            }
            'S' -> {
                vertical -= it.second
            }
            'E' -> {
                lateral += it.second
            }
            'W' -> {
                lateral -= it.second
            }
            'R' -> {
                cardinalDirection = ((cardinalDirection + (it.second / 90)) + 4) % 4
            }
            'L' -> {
                cardinalDirection = ((cardinalDirection - (it.second / 90)) + 4) % 4
            }
        }
    }
    println(lateral.absoluteValue + vertical.absoluteValue)
}

fun findBoatDirectionUsingWaypoint() {
    val directions = mutableListOf<Pair<Char, Int>>()
    File("data/day12.txt").forEachLine {
        directions.add(Pair(it[0], it.subSequence(1, it.length).toString().toInt()))
    }
    var lateralWaypoint = 10
    var verticalWaypoint = 1

    var lateralPosition = 0
    var verticalPosition = 0

    directions.forEach {
//        println("position: $lateralPosition $verticalPosition\nwaypoint: $lateralWaypoint $verticalWaypoint\n")
        when(it.first) {
            'F' -> {
                verticalPosition += it.second * verticalWaypoint
                lateralPosition += it.second * lateralWaypoint
            }
            'N' -> {
                verticalWaypoint += it.second
            }
            'S' -> {
                verticalWaypoint -= it.second
            }
            'E' -> {
                lateralWaypoint += it.second
            }
            'W' -> {
                lateralWaypoint -= it.second
            }
            'R' -> {
                val currentLateralWaypoint = lateralWaypoint
                when (it.second / 90) {
                    0 -> {}
                    1 -> {
                        // 90deg right
                        lateralWaypoint = verticalWaypoint
                        verticalWaypoint = -currentLateralWaypoint
                    }
                    2 -> {
                        // 180deg right
                        lateralWaypoint *= -1
                        verticalWaypoint *= -1
                    }
                    3 -> {
                        // 270deg right
                        lateralWaypoint = -verticalWaypoint
                        verticalWaypoint = currentLateralWaypoint
                    }
                }
            }
            'L' -> {
                val currentLateralWaypoint = lateralWaypoint
                when (it.second / 90) {
                    0 -> {}
                    1 -> {
                        lateralWaypoint = -verticalWaypoint
                        verticalWaypoint = currentLateralWaypoint

                    }
                    2 -> {
                        lateralWaypoint *= -1
                        verticalWaypoint *= -1
                    }
                    3 -> {
                        lateralWaypoint = verticalWaypoint
                        verticalWaypoint = -currentLateralWaypoint
                    }
                }
            }
        }
    }
    println(lateralPosition.absoluteValue + verticalPosition.absoluteValue)
}

fun main(args: Array<String>) {
//    findBoatDirection()
    findBoatDirectionUsingWaypoint()
}