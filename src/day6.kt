import java.io.File

fun sumOfYesValues() {
    var sumOfYes = 0

    var setOfAnswers = HashSet<Char>()
    File("data/day6.txt").forEachLine {
        if (it.isEmpty()) {
            sumOfYes += setOfAnswers.size
            setOfAnswers = HashSet()
            return@forEachLine
        }

        it.forEach { jt -> setOfAnswers.add(jt) }
    }
    sumOfYes += setOfAnswers.size
    println(sumOfYes)
}

fun getConsensusValue(yesSet: HashMap<Char, Int>, numberOfPeople: Int): Int {
    return yesSet.entries.toList().fold(0) { acc, next ->
        acc + (if (next.value == numberOfPeople) 1 else 0)
    }
}

fun sumOfConsensusYesValues() {
    var sumOfYes = 0

    var setOfAnswers = HashMap<Char, Int>()
    var numberOfPeople = 0
    File("data/day6.txt").forEachLine {
        if (it.isEmpty()) {
            sumOfYes += getConsensusValue(setOfAnswers, numberOfPeople)
            setOfAnswers = HashMap()
            numberOfPeople = 0
            return@forEachLine
        }
        numberOfPeople += 1
        it.forEach { jt -> setOfAnswers[jt] = (setOfAnswers[jt] ?: 0) + 1 }
    }
    sumOfYes += getConsensusValue(setOfAnswers, numberOfPeople)
    println(sumOfYes)
}

fun main(args: Array<String>) {
//    sumOfYesValues()
    sumOfConsensusYesValues()
}