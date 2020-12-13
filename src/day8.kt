import java.io.File

class Operation(var type: String, val value: Int) {}

fun buildOperations(): MutableList<Operation> {
    val operations: MutableList<Operation> = mutableListOf()

    File("data/day8.txt").forEachLine {
        val parts = it.split(" ")
        operations.add(Operation(parts[0], parts[1].toInt()))

    }
    return operations
}

fun findAccumulatorBeforeLoop() {
    val operations = buildOperations()
    println(findAccumulator(operations))
}

fun findAccumulator(operations: MutableList<Operation>): Int {
    var acc = 0
    var currentPosition = 0;
    val seenPositions = HashSet<Int>()
    while(currentPosition < operations.size) {
        if (seenPositions.contains(currentPosition)) {
//            println("loop at $currentPosition with acc $acc")
            return -1
        }
        seenPositions.add(currentPosition)

        when (operations[currentPosition].type) {
            "acc" -> {
                acc += operations[currentPosition++].value
            }
            "jmp" -> {
                currentPosition += operations[currentPosition].value
            }
            "nop" -> {
                currentPosition++
            }
        }
    }
//    println("exited with acc $acc")
    return acc
}
fun fixAndFindFinalAccumulator() {
    // Can change one jmp -> nop OR nop -> jmp
    val operations = buildOperations()
    for (i in 0..operations.size) {
        val it = operations[i]
        val oldType = it.type
        when (it.type) {
            "jmp" -> {
                it.type = "nop"
            }
            "nop" -> {
                it.type = "jmp"
            }
        }
        val acc = findAccumulator(operations)
        if (acc > -1) {
            println("acc found $acc")
            return
        } else {
            it.type = oldType
        }
    }
}

fun main(args: Array<String>) {
//    findAccumulatorBeforeLoop()
    fixAndFindFinalAccumulator()
}