import java.io.File


data class Group(val groupOrder: Int) {
    var yes = Array(10) {IntArray('z'-'a' + 1) { 0 }}
    var exclude = IntArray('z'-'a' + 1) { 0 }
    var count: Int = 0
    var countAll: Int = 0
    var person: Int = 0
}


fun main(args: Array<String>) {
    var input = getData()
    input = exlude(input)
    input = countAll(input)


    println(countSum(input))
}

private fun countAll(input: List<Group>): List<Group> {
    input.forEach { item ->
        item.countAll = item.exclude.sum()
    }

    return input
}

private fun exlude(input: List<Group>): List<Group> {
    input.forEach { item ->
        for (i in 0..('z' - 'a' )) {
            var sum = 1
            for (j in 0..(item.person - 1)) {
                sum *= item.yes[j][i]
            }

            item.exclude[i] = sum
        }
    }

    return input
}

private fun countSum(input: List<Group>): Int {
    return input.sumBy { it.countAll }
}

private fun getData(): List<Group> {
    val fileName = "src/input/day6.txt"
    val input = mutableListOf<Group>()
    var autoIncrementId = 0
    var item = Group(autoIncrementId++)
    var rowIndex = 0

    File(fileName).forEachLine { row ->
        if (row.isNullOrBlank()) {
            item.person = rowIndex
            input.add(item)

            rowIndex = 0
            item = Group(autoIncrementId++)
        } else {

            row.forEach { index ->
                item.yes[rowIndex][index - 'a'] = 1
            }

            rowIndex++
        }
    }

    if (!input.contains(item)) {
        item.person = rowIndex
        input.add(item)
    }

    return input
}
