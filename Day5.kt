import java.io.File
import java.lang.Exception
import kotlin.system.exitProcess


data class Seat(var value: String) {
    var column: String = ""
    var row: String = ""
    var id: Int = 0
    var columnId = 0
    var rowId = 0

}


fun main(args: Array<String>) {
    val input = getData()

    val max = input.maxBy { it.id }
    //print(max?.id)

    for (i in 8..1026) {
        val isMySeat = findMySeatId(input, i, 8, 911)
        if (isMySeat) {
            println(i)
        }
    }

}

fun findMySeatId(input: List<Seat>, myId: Int, min: Int, max: Int): Boolean {
    val before = input.filter { it.id == myId - 1 }.firstOrNull()
    val after = input.filter { it.id == myId + 1 }.firstOrNull()
    val me = input.filter { it.id == myId }.firstOrNull()

    return myId in min..max && before != null && after != null && me == null
}


private fun getData(): List<Seat> {
    val fileName = "src/input/day5.txt"
    val input = mutableListOf<Seat>()

    File(fileName).forEachLine {
        input.add(parseData(it))
    }


    return input
}

private fun parseData(value: String): Seat {
   val seat = Seat(value)

    seat.column = value.substring(0,7)
    seat.row = value.substring(7,10)

    var columnId = Pair(0, 127)
    seat.column.forEach {
        columnId = preProcess(columnId, it)
    }
    seat.columnId = columnId.first

    var rowId = Pair(0, 7)
    seat.row.forEach {
        rowId = preProcess(rowId, it)
    }
    seat.rowId = rowId.first

    seat.id = seat.columnId * 8 + seat.rowId

    return seat
}

private fun preProcess(input: Pair<Int, Int>, direction: Char): Pair<Int, Int> {
    return when (direction) {
        'F', 'L' -> Pair(input.first, (input.second + input.first) / 2)
        'B', 'R' -> Pair((input.first + input.second) / 2 + 1, input.second)
        else -> input
    }
}
