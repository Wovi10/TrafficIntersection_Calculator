package utils

object Functions {
    fun <T> printArray(arrayToPrint: Array<T>) {
        var output: String = Constants.EMPTY_STRING
        var counter = Constants.ONE
        for (element in arrayToPrint) {
            output += "$counter. "
            output += element.toString()
            output += Constants.NEWLINE
            counter++
        }
        println(output)
    }
    
    fun <T> printArrayList(arrayListToPrint: ArrayList<T>) {
        var output: String = Constants.EMPTY_STRING
        var counter = Constants.ONE
        for (element in arrayListToPrint) {
            output += "$counter. "
            output += element.toString()
            output += Constants.NEWLINE
            counter++
        }
        println(output)
    }
}