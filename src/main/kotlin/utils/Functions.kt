package utils

import utils.Constants.EMPTY_STRING
import utils.Constants.NEWLINE
import utils.Constants.ONE
import utils.Constants.TAB
import utils.Constants.TWO
import utils.Constants.ZERO

object Functions {
    fun <T> printArray(arrayToPrint: Array<T>) {
        var output: String = EMPTY_STRING
        var counter = ONE
        for (element in arrayToPrint) {
            output = addElementToOutput(output, counter, element)
            counter++
        }
        println(output)
    }
    
    fun <T> printArrayList(arrayListToPrint: ArrayList<T>) {
        var output: String = EMPTY_STRING
        var counter = ONE
        for (element in arrayListToPrint) {
            output = addElementToOutput(output, counter, element)
            counter++
        }
        println(output)
    }

    fun <T> getArrayList(arrayListToPrint: ArrayList<T>): String {
        var output: String = EMPTY_STRING
        var counter = ONE
        for (element in arrayListToPrint) {
            output = addElementToOutput(output, counter, element)
            counter++
        }
        return output
    }

    private fun <T> addElementToOutput(output: String, counter: Int, element: T): String {
        var output1 = output
        output1 += "$counter. "
        output1 += element.toString()
        output1 += NEWLINE
        return output1
    }
}