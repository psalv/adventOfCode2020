import java.io.File

fun twoValues() {
    val allValuesMap: MutableMap<Int, Int> = mutableMapOf()
    File("data/day1.txt").forEachLine { allValuesMap[it.toInt()] = (allValuesMap[it.toInt()] ?: 0) + 1}

    allValuesMap.forEach {
        val complementary = 2020 - it.key
        if (allValuesMap.contains(complementary) && (it.key != complementary || it.value > 1)) {
            println(complementary * it.key)
        }
    }
}

fun threeValues() {
    val allValues: MutableList<Int> = mutableListOf()
    File("data/day1.txt").forEachLine { allValues.add(it.toInt()) }

    val allValuesSet: MutableSet<Int> = mutableSetOf()
    allValues.forEach { allValuesSet.add(it) }
    // println(allValues)

    allValues.forEach {
        val complementary = 2020 - it
        if (allValuesSet.contains(complementary)) {
            println(complementary * it)
        }
    }
}

fun main(args: Array<String>) {
     twoValues();
//    threeValues();
}