import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    val data = readDataInputDay3()

    val result = countTreeAtSlope(data, 1, 1) * countTreeAtSlope(data, 3, 1) * countTreeAtSlope(data, 5, 1) * countTreeAtSlope(data, 7, 1) * countTreeAtSlope(data, 1, 2)

    print(result)
}

private fun countTreeAtSlope(input: Array<CharArray>, right: Int, down: Int): Int {
    var row = 0
    var colum = 0
    var count = 0

    while (row<= 323 -1 - down) {
        row += down
        colum += right
        if (input[row][colum % 31] == '#') {
            count++
        }
    }

    return count
}



fun readDataInputDay3(): Array<CharArray> {
    val fileName = "src/input/day3.txt"
    val ROW = 323
    val COLUMN = 31

    val arr = Array(ROW) { CharArray(COLUMN) { '.' } }
    var row = 0
    File(fileName).forEachLine {
        for (i in 0 until COLUMN) {
            arr[row][i] = it[i]
        }

        row++
    }

    return arr
}