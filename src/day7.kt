import java.io.File

/*
Can create a graph w/ parent references
Then I need to start at the shiny gold bag, and move up through all of it's parents
 */

class Bag(val type: String) {
    public val parents = HashSet<Bag>()
    public val children = HashMap<Bag, Int>()
}

fun buildBagParentList(): HashMap<String, Bag> {
    val typeToBag = HashMap<String, Bag>()
    File("data/day7.txt").forEachLine {
        val rulePieces = it.split("bags contain").map { jt -> jt.trim() }
        if (!typeToBag.containsKey(rulePieces[0])) {
            typeToBag[rulePieces[0]] = Bag(rulePieces[0])
        }
        val parentBag = typeToBag[rulePieces[0]] ?: return@forEachLine

        val containedBagTypes = rulePieces[1]
                .split(',')
                .map {
                    jt -> (jt.replace("[1-9]*".toRegex(), ""))
                        .replace(".", "")
                        .replace("bags?".toRegex(), "")
                        .trim()
                }

        val containedBagNumbers = rulePieces[1]
                .split(',')
                .map { jt -> jt.trim() }
                .map { jt ->
                    val stringNum = jt.split(" ")[0]
                    if (stringNum == "no") 0 else stringNum.toInt()
                }

        containedBagTypes.forEachIndexed { index, jt ->
            if (!typeToBag.containsKey(jt)) {
                typeToBag[jt] = Bag(jt)
            }
            val childBag = typeToBag[jt] ?: return@forEachIndexed
            childBag.parents.add(parentBag)
            parentBag.children[childBag] = containedBagNumbers[index]
        }

    }
    return typeToBag
}

fun expandBags(bag: Bag?, set: HashSet<Bag> = HashSet<Bag>()): HashSet<Bag> {
    if (bag == null || set.contains(bag)) {
        return set
    }
    if (bag.type != "shiny gold") {
        set.add(bag)
    }
    bag.parents.forEach { expandBags(it, set) }
    return set
}

fun findSuitableBags() {
    val typeToBag = buildBagParentList()
    val allAcceptableBags = expandBags(typeToBag["shiny gold"])
    println(allAcceptableBags.size)
}

fun countNumberOfBagsWithin(bag: Bag?): Int {

    if (bag == null) {
        return 0
    }
    return bag.children.toList().fold(1) { acc, next ->
        acc + next.second * countNumberOfBagsWithin(next.first)
    } - if (bag.type == "shiny gold") 1 else 0
}

fun findNumberOfBags() {
    val typeToBag = buildBagParentList()
    println(countNumberOfBagsWithin(typeToBag["shiny gold"]))
}

fun main(args: Array<String>) {
//    findSuitableBags()
    findNumberOfBags()
}