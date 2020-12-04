import java.io.File
import java.lang.Exception
import kotlin.system.exitProcess


data class Passport(var pid: String) {
    var byr: String = ""
    var iyr: String = ""
    var eyr: String = ""
    var hgt: String = ""
    var hcl: String = ""
    var ecl: String = ""
    var cid: String? = ""

}


fun main(args: Array<String>) {
    val input = getData()

    println(countValidPassport(input))
}

private fun countValidPassport(input: List<Passport>): Int {
    return input.count { isValidPassport(it) }
}

private fun isValidPassport(item: Passport): Boolean {
    return item.byr.isNotBlank() && item.byr.toInt() in 1920..2002
            && item.iyr.isNotBlank() && item.iyr.toInt() in 2010..2020
            && item.eyr.isNotBlank() && item.eyr.toInt() in 2020..2030
            && item.hgt.isNotBlank() && isValidHeight(item.hgt)
            && item.hcl.isNotBlank() && isValidHairColor(item.hcl)
            && item.ecl.isNotBlank() && isValidEyeColor(item.ecl)
            && item.pid.isNotBlank() && isValidPid(item.pid)
}

private fun isValidPid(value: String): Boolean {
    return value.matches(Regex("([0-9]){9}"))
}

private fun isValidEyeColor(value: String): Boolean {
    val words = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    return words.contains(value)
}

private fun isValidHairColor(value: String): Boolean {
    val pattern = Regex("^#([0-9a-f]){6}")

    return value.matches(pattern)
}

private fun isValidHeight(height: String): Boolean {
    try {
        if (height.endsWith("cm")) {
            val value = height.replace("cm", "").toInt()

            return value in 150..193
        }

        if (height.endsWith("in")) {
            val value = height.replace("in", "").toInt()

            return value in 59..76
        }
    } catch (e: Exception) {
        return false
    }

    return false
}

private fun getData(): List<Passport> {
    val fileName = "src/input/day4.txt"
    val input = mutableListOf<Passport>()
    var item = Passport("")

    File(fileName).forEachLine {
        if (it.isNullOrBlank()) {
            input.add(item)

            item = Passport("")
        } else {
            item = parseData(it, item)
        }
    }

    if (!input.contains(item)) {
        input.add(item)
    }

    return input
}

private fun parseData(value: String, item: Passport): Passport {
    try {
        val map = value.split(" ").associate {
            val (left, right) = it.split(":")

            left to right
        }

        map.forEach { (key, value) ->
            when (key) {
                "byr" -> item.byr = value
                "iyr" -> item.iyr = value
                "eyr" -> item.eyr = value
                "hgt" -> item.hgt = value
                "hcl" -> item.hcl = value
                "ecl" -> item.ecl = value
                "pid" -> item.pid = value
                "cid" -> item.cid = value
            }
        }
    } catch (e: Exception) {
        println("Data invalid")
    }

    return item
}
