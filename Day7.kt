import java.io.File
import java.lang.System.`in`
import java.lang.System.out
import java.util.*
import kotlin.system.exitProcess

data class Bag(var value: String) {
    var bagName = ""
    var count = 0
    var isSelected = false
    var contains: List<Bag> = mutableListOf<Bag>()

}

fun main(args: Array<String>) {
    val data = readDataInputDay7()

    val init = Bag("")
    init.bagName = "shiny gold"
    //print(travellingBag(init, data))

    print(sumOfBag(init, data) - 1)

}

private fun sumOfBag(init: Bag, data: List<Bag>): Int {

   var item = data.find { isMatchBag(it, listOf(init)) }

    item?.let {
        return item.count * sum(item, data)
    }

    return 0
}

private fun sum(item: Bag, data: List<Bag>): Int {
    if (item.contains.isEmpty()) {
        return 1
    }

    var sum = 0
    for (i in item.contains) {
        var item = data.find { isMatchBag(it, listOf(i)) }
        item?.let {
            sum += i.count * sum(it, data)
        }

    }

    return 1 + sum * item.count
}

private fun travellingBag(init: Bag, data: List<Bag>): Int {

    var remain = searchBag(listOf(init), data)
    while (remain.isNotEmpty()) {
       remain = searchBag(remain, data)
    }

    return data.count { it.isSelected }
}

private fun searchBag(accept: List<Bag>, data: List<Bag>): List<Bag> {
    val result = mutableListOf<Bag>()

    data.forEach { checking ->
        if (isChildContain(checking.contains, accept)) {
            checking.isSelected = true
            result.add(checking)
        }
    }

    return result
}

private fun isChildContain(checking: List<Bag>, accept: List<Bag>): Boolean {
    return checking.any { isMatchBag(it, accept) }
}


private fun isMatchBag(checking: Bag, target: List<Bag>): Boolean {
    return !checking.isSelected && target.any { it.bagName == checking.bagName }
}


fun readDataInputDay7(): List<Bag> {
    val fileName = "src/input/day7.txt"

    val arr = mutableListOf<Bag>()
    File(fileName).forEachLine {
        arr.add(parserBag(it))

    }

    return arr
}

private fun parserBag(value: String): Bag {
    val item = Bag(value)

    val bagContent = value.split("contain")
    val bagName = bagContent[0].replace("bags", "").trim()
    item.bagName = bagName
    item.count = 1
    item.contains = parseInside(bagContent[1].trim())

    return item
}

private fun parseInside(value: String): List<Bag> {
    val items = value.split(",")
    val content = mutableListOf<Bag>()
    items.forEach {
        val item = parserItemInside(it)
        item?.let { bag ->
            content.add(bag)
        }
    }

    return content
}

private fun parserItemInside(value: String): Bag? {
    if (value.contains("no other bags")) {
        return null
    }

    val count = value.filter { it.isDigit() }
    val result = Bag(value)
    result.count = count.toIntOrNull() ?: 1
    result.bagName = value.replace("bags","")
            .replace("bag", "")
            .replace(".", "")
            .replace(count, "").trim()

    return result
}