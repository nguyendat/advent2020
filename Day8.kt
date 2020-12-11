import java.io.File
import java.lang.System.`in`
import java.lang.System.out
import java.util.*
import kotlin.system.exitProcess

 enum class EINSTRUCT(val ops: String) {
    NOP("nop"),
    ACC("acc"),
    JMP("jmp");

    companion object {
        fun getInstruct(value: String): EINSTRUCT {
            return when (value) {
                "nop" -> NOP
                "acc" -> ACC
                else -> JMP
            }
        }
    }
}

data class Instruct(var content: String) {
    var inst: EINSTRUCT = EINSTRUCT.NOP
    var value: Int = 0
    var index: Int = 0
    var isExecuted = false

    fun execute(): Int {
        return when (this.inst) {
            EINSTRUCT.ACC -> value
            else -> 0
        }
    }

    fun markAsExcuted() {
        isExecuted = true
    }

    fun nextOps(data: List<Instruct>): Pair<Instruct?, Boolean> {
        return when (inst) {
            EINSTRUCT.JMP -> {
                val nextIndex = this.index + this.value
                Pair(data.getOrNull(nextIndex), nextIndex == data.size)
            }
            else -> {
                val nextIndex = this.index + 1
                Pair(data.getOrNull(nextIndex), nextIndex == data.size)
            }
        }
    }

    fun switch(): Boolean {
        return when (inst) {
            EINSTRUCT.JMP ->  {
                this.inst = EINSTRUCT.NOP
                true
            }
            EINSTRUCT.NOP -> {
                this.inst = EINSTRUCT.JMP
                true
            }
            else -> false
        }
    }
 }


fun main(args: Array<String>) {
    val data = readDataInputDay8()

    print(correctedExecute(data))

}

private fun correctedExecute(ops: List<Instruct>): Int {
    ops.forEach { item ->
        if (item.switch()) {
            val (sum, complete) = executeComplete(ops)
            if (complete) {
                return sum
            }
        }

        item.switch()
        ops.forEach { it.isExecuted = false }
    }

    return 0
}

private fun executeComplete(ops: List<Instruct>): Pair<Int, Boolean> {
    var currOpr: Instruct? = ops.first()
    var sum = 0
    var complete = false

    while (currOpr != null && !currOpr.isExecuted) {
        sum += currOpr.execute()
        currOpr.markAsExcuted()

        val (Opr, isComplete) = currOpr.nextOps(ops)
        currOpr = Opr
        complete = isComplete

    }

    return Pair(sum, complete)
}




fun readDataInputDay8(): List<Instruct> {
    val fileName = "src/input/day8.txt"
    var index = 0
    val arr = mutableListOf<Instruct>()
    File(fileName).forEachLine {
        arr.add(parserInstruct(it, index++))

    }

    return arr
}

private fun parserInstruct(value: String, index: Int): Instruct {
    val item = Instruct(value)

    val operations = value.split(" ")
    item.inst = EINSTRUCT.getInstruct(operations[0])
    item.index = index
    item.value = operations[1].toIntOrNull() ?: 0

    return item
}


