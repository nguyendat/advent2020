import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val fileName = "src/input/day1.txt"
    val MAX = 2020
    val input = IntArray(MAX) { 0 }

    File(fileName).forEachLine {
        val value = it.toIntOrNull()
        value?.let {
            input[value] = 1
            if (input[MAX - value] == 1) {
                print("${value * (MAX - value)}")
                exitProcess(0)
            }
        }
    }
}