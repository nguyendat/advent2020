import java.io.File
import java.lang.Exception
import kotlin.system.exitProcess

data class Password( val bound: Bound,
                     val char: String,
                     val password: String) {

}

data class Bound( val min: Int,
                  val max: Int)

fun main(args: Array<String>) {
    val input = getData()

    val result = input.count { isValidPassword(it) }

    print(result)
}

private fun isValidPassword(password: Password): Boolean {
    return (password.char[0] == password.password[password.bound.min-1]
            && password.char[0] != password.password[password.bound.max-1])
            || (password.char[0] != password.password[password.bound.min-1]
            && password.char[0] == password.password[password.bound.max-1])
}

private fun getData(): List<Password> {
    val fileName = "src/input/day2.txt"
    val input = mutableListOf<Password>()

    File(fileName).forEachLine {
        val data = parseData(it)
        data?.let {
            input.add(it)
        }
    }

    return input
}

private fun parseData(value: String): Password? {
    try {
        val params = value.split(" ")
        val range = params[0].split("-")
        val bound = Bound(range[0].toInt(), range[1].toInt())

        return Password(bound,
                params[1].trim().replace(":", ""),
                params[2].trim())
    } catch (e: Exception) {
        println("Data invalid")
    }

    return null
}