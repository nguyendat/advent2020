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
        }
    }

    input.forEachIndexed { index, i ->
        if (i == 1) {
            val pair = search(MAX - index, input)
            pair?.let {
                print("${index * pair.first * pair.second}")
                exitProcess(0)
            }
        }
    }

}



fun search(sum: Int, input: IntArray, exclude: IntArray? = null): Pair<Int, Int>? {
    input.forEachIndexed { index, i ->

        if (i == 1 && sum - index >= 0 && input[sum - index] == 1) {
            return Pair(index, sum - index)
        }
    }

    return null

}
