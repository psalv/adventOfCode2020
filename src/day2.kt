import java.io.File


fun validateRange() {
    var validPasswords = 0
    File("data/day2.txt").forEachLine {
        val parts = it.split(" ")
        val minMax = parts[0].split("-")
        val minNumber = minMax[0].toInt()
        val maxNumber = minMax[1].toInt()
        val letter: String = parts[1].substring(0, 1)

        val occurrences = parts[2].count{ jt -> letter.contains(jt) }

        if (occurrences in minNumber..maxNumber) {
            validPasswords++
        }

    }
    println(validPasswords)
}

fun validateXOr() {
    var validPasswords = 0
    File("data/day2.txt").forEachLine {
        val parts = it.split(" ")
        val minMax = parts[0].split("-")
        val minNumber = minMax[0].toInt()
        val maxNumber = minMax[1].toInt()
        val letter = parts[1][0]

        if ((parts[2][minNumber - 1] == letter).xor(parts[2][maxNumber - 1] == letter)) {
            validPasswords++
        }

    }
    println(validPasswords)
}

fun main(args: Array<String>) {
    validateRange();
    validateXOr();
}