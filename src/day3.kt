import java.io.File

fun CharSequence.splitIgnoreEmpty(vararg delimiters: String): List<String> {
    return this.split(*delimiters).filter {
        it.isNotEmpty()
    }
}

fun countTrees(horizontalStride: Int = 3, verticalStride: Int = 1): Int {
    var numTrees = 0
    var currentX = 0
    var currentVerticalStride = 1;
    File("data/day3.txt").forEachLine {
        currentVerticalStride--
        if (currentVerticalStride != 0) {
            return@forEachLine
        }
        val spaces = it.splitIgnoreEmpty("")

//        println("${spaces[currentX % spaces.size]} ${currentX  % spaces.size}")

        if (spaces[currentX % spaces.size] == "#") {
            numTrees++
        }
        currentX += horizontalStride
        currentVerticalStride = verticalStride
    }
    return numTrees
}

fun checkMultipleSlopes() {
    val pairs = listOf(
            listOf(1, 1),
            listOf(3, 1),
            listOf(5, 1),
            listOf(7, 1),
            listOf(1, 2)
    )
    val result = pairs.fold(1) { sum, element ->
        sum * countTrees(element[0], element[1])
    }
    println(result)
}

fun main(args: Array<String>) {
//     countTrees()
    checkMultipleSlopes()
}