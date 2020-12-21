import java.io.File
import kotlin.math.pow

fun toBinary(n: Int): String {
    val binary = Integer.toBinaryString(n)
    val zerosToAdd = 36 - binary.length
    return "${"0".repeat(zerosToAdd)}$binary"
}

fun toDecimal(n: String): Long {
    var currentValue = 0.0
    for (i in n.indices) {
        if (n[i] == '1') {
            currentValue += (2.0).pow(35 - i)
        }
    }
    return currentValue.toLong()
}

fun maskBinary(n: String, mask: String, freeValue: Char = 'X'): String {
    val sb = StringBuilder(n)
    for (i in mask.indices) {
        if (mask[i] != freeValue) {
            sb.also { it.setCharAt(i, mask[i])}
        }
    }
    return sb.toString()
}

fun sumMemoryAddresses() {
    val memory = HashMap<Int, Long>()
    var mask: String? = null
    File("data/day14.txt").forEachLine readLine@{
        val (assignee, assignment) = it.split('=').map {jt -> jt.trim()}
        if (assignee == "mask") {
            mask = assignment
            return@readLine
        }
        val memoryPosition = "(?<=\\[).*(?=\\])".toRegex().find(assignee)?.value?.toInt()
        val binaryValue = toBinary(assignment.toInt())
        val maskedValue = maskBinary(binaryValue, mask!!)
        memory[memoryPosition!!] = toDecimal(maskedValue)
    }
    val summedMemory = memory.entries.toList().fold(0.toLong()) { acc, next ->
        acc + next.value
    }
    println(summedMemory)
}

fun getMaskedPermutations(n: String, index: Int = 0): MutableList<String> {
    if (index >= n.length) {
        return mutableListOf(n)
    }
    if (n[index] != 'X') {
        return getMaskedPermutations(n, index + 1)
    }

    val options = mutableListOf<String>()
    val sb = StringBuilder(n)
    sb.also { it.setCharAt(index, '0')}
    options.addAll(getMaskedPermutations(sb.toString(), index + 1))
    sb.also { it.setCharAt(index, '1')}
    options.addAll(getMaskedPermutations(sb.toString(), index + 1))
    return options
}

fun sumFloatingMemoryAddresses() {
    val memory = HashMap<Long, Long>()
    var mask: String? = null
    File("data/day14.txt").forEachLine readLine@{
        val (assignee, assignment) = it.split('=').map {jt -> jt.trim()}
        if (assignee == "mask") {
            mask = assignment
            return@readLine
        }
        val memoryPosition = "(?<=\\[).*(?=\\])".toRegex().find(assignee)?.value?.toInt()
        val binaryAddress = toBinary(memoryPosition!!)
        val maskedValue = maskBinary(binaryAddress, mask!!, '0')

        getMaskedPermutations(maskedValue).forEach { jt ->
            memory[toDecimal(jt)] = assignment.toLong()
        }
    }
    val summedMemory = memory.entries.toList().fold(0.toLong()) { acc, next ->
        acc + next.value
    }
    println(summedMemory)
}

fun main(args: Array<String>) {
//    sumMemoryAddresses()
    sumFloatingMemoryAddresses()
}