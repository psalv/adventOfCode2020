import java.io.File

/*
byr (Birth Year)
iyr (Issue Year)
eyr (Expiration Year)
hgt (Height)
hcl (Hair Color)
ecl (Eye Color)
pid (Passport ID)
cid (Country ID) -> optional
 */

fun checkIfPresent(keyValuePairs: HashMap<String, String>): Boolean {
    val mustContainKeys = listOf(
            "byr",
            "iyr",
            "eyr",
            "hgt",
            "hcl",
            "ecl",
            "pid"
    )

    return mustContainKeys.all {
        keyValuePairs.containsKey(it)
    }
}

/*
byr (Birth Year) - four digits; at least 1920 and at most 2002.
iyr (Issue Year) - four digits; at least 2010 and at most 2020.
eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
hgt (Height) - a number followed by either cm or in:
If cm, the number must be at least 150 and at most 193.
If in, the number must be at least 59 and at most 76.
hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.

pid (Passport ID) - a nine-digit number, including leading zeroes.

cid (Country ID) - ignored, missing or not.
 */

fun checkIfValid(keyValuePairs: HashMap<String, String>): Boolean {
    val mustContainKeys = listOf(
            "byr",
            "iyr",
            "eyr",
            "hgt",
            "hcl",
            "ecl",
            "pid"
    )

    if (!mustContainKeys.all {
        keyValuePairs.containsKey(it)
    }) {
        return false
    }

    val byrValue = keyValuePairs["byr"]!!.toInt()
    if (keyValuePairs["byr"]!!.length != 4) {
        return false
    }
    if (byrValue < 1920 || byrValue > 2002) {
        return false
    }

    val iyrValue = keyValuePairs["iyr"]!!.toInt()
    if (keyValuePairs["iyr"]!!.length != 4) {
        return false
    }
    if (iyrValue < 2010 || iyrValue > 2020) {
        return false
    }

    val eyrValue = keyValuePairs["eyr"]!!.toInt()
    if (keyValuePairs["eyr"]!!.length != 4) {
        return false
    }
    if (eyrValue < 2020 || eyrValue > 2030) {
        return false
    }

    val eclValue = keyValuePairs["ecl"]
    val eyeValues = listOf(
            "amb", "blu", "brn", "gry", "grn", "hzl", "oth"
    )
    if (!(eyeValues.any {
            it == eclValue
    })) {
        return false
    }

    val pidValue = keyValuePairs["pid"]!!
    if (pidValue.length != 9 || !pidValue.all { "^[0-9]*$".toRegex().matches(it.toString()) }) {
        return false
    }

    val hgtValue = keyValuePairs["hgt"]!!
    if ("[0-9]+cm".toRegex().matches(hgtValue)) {
        val hgtNumber = hgtValue.substringBeforeLast("cm").toInt()
        if (hgtNumber < 150 || hgtNumber > 193) {
            return false
        }
    } else if ("[0-9]+in".toRegex().matches(hgtValue)) {
        val hgtNumber = hgtValue.substringBeforeLast("in").toInt()
        if (hgtNumber < 59 || hgtNumber > 76) {
            return false
        }
    } else {
        return false
    }

    val hclValue = keyValuePairs["hcl"]!!
    if (hclValue.length != 7 || !"^#[a-zA-Z0-9]*$".toRegex().matches(hclValue)) {
        return false
    }

    return true
}


fun countValid(moreValid: Boolean = false) {
    var numValid = 0

    var keyValuePairs = HashMap<String, String>()
    File("data/day4.txt").forEachLine {
        if (it.isEmpty()) {
            if ((!moreValid && checkIfPresent(keyValuePairs)) || (moreValid && checkIfValid(keyValuePairs))) {
                numValid++
            }
            keyValuePairs = HashMap<String, String>()
            return@forEachLine
        }
        val keyValues = it.split(" ")
        keyValues.forEach { jt ->
            val splitValues = jt.split(":")
            keyValuePairs[splitValues[0]] = splitValues[1]
        }
    }
    if ((!moreValid && checkIfPresent(keyValuePairs)) || (moreValid && checkIfValid(keyValuePairs))) {
        numValid++
    }
    println(numValid)
}

fun main(args: Array<String>) {
//    countValid()
    countValid(true)
}