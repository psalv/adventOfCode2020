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
    val allValuesMap: MutableMap<Int, Int> = mutableMapOf()
    File("data/day1.txt").forEachLine { allValuesMap[it.toInt()] = (allValuesMap[it.toInt()] ?: 0) + 1}


    allValuesMap.forEach {
        val complementary = 2020 - it.key

        allValuesMap.forEach { jt ->
            val complementaryJ = complementary - jt.key
            // Currently ignores 2020/3 since it's fractional
            if (allValuesMap.contains(complementaryJ) && (
                        ((complementary != complementaryJ) || jt.value > 1) ||
                        ((complementary != it.value) || it.value > 1) ||
                        ((complementaryJ != it.value) || it.value > 1)
            )) {
                println(jt.key * complementaryJ * it.key)
            }
        }
    }
}

fun main(args: Array<String>) {
    twoValues();
    threeValues();
}