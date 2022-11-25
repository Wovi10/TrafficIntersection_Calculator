package intersection.dangerZone

import utils.Constants.EMPTY_STRING
import utils.Constants.NEWLINE
import utils.Constants.ONE
import utils.Constants.TAB
import utils.Constants.ZERO

class DangerZone(xCoord_: Int = ZERO, yCoord_: Int = ZERO, isOutput_: Boolean = false) {
    private var inUse: Boolean = false
    private var isOutput: Boolean = isOutput_
    private var sideLength: Int = ZERO
    val xCoord: Int = xCoord_
    val yCoord: Int = yCoord_
    val connectedDangerZones: ArrayList<DangerZone> = ArrayList()
    var connectedIndex: Int = ONE

    fun setConnectedDangerZones(allDangerZones: ArrayList<DangerZone>) {
        sideLength = allDangerZones.size
        setDangerZone(allDangerZones, xCoord - ONE, yCoord) // Left
        setDangerZone(allDangerZones, xCoord, yCoord + ONE) // Up
        setDangerZone(allDangerZones, xCoord + ONE, yCoord) // Right
        setDangerZone(allDangerZones, xCoord, yCoord - ONE) // Down
    }

    private fun setDangerZone(allDangerZones: ArrayList<DangerZone>, xToTry: Int, yToTry: Int){
        for (dangerZone in allDangerZones) {
            val succeeded = tryDangerZone(dangerZone, xToTry, yToTry)
            if (succeeded) return
        }
    }

    private fun tryDangerZone(dangerZone: DangerZone, xToTry: Int, yToTry: Int): Boolean {
        if (dangerZone.xCoord != xToTry) return false
        if (dangerZone.yCoord != yToTry) return false
        connectedDangerZones.add(dangerZone)
        return true
    }

    private fun betweenZeroAndSideLength(vertToTry: Int, sideLength: Int) =
        !smallerThanZero(vertToTry) && !biggerThanSideLength(vertToTry, sideLength)

    private fun biggerThanSideLength(vertToTry: Int, sideLength: Int) = vertToTry > sideLength

    private fun smallerThanZero(vertToTry: Int) = vertToTry < ZERO

    fun printConnectedDangerZone() {
        var output = EMPTY_STRING
        for (connectedDangerZone in connectedDangerZones) {
            output += connectedDangerZone.getCoords()
            output += TAB
        }
        println(output)
    }

    fun setNextConnectedIndex(){
        connectedIndex++
        if (connectedIndex == connectedDangerZones.size) connectedIndex = ZERO
    }

    fun getCoords(): String{
        return "$xCoord$yCoord"
    }



    override fun toString(): String {
        var output = EMPTY_STRING
        output += "DangerZone:"
        output += "$TAB$NEWLINE"
        output += "isOutput: $isOutput"
        output += "$TAB$NEWLINE"
        output += "sideLength: $sideLength"
        output += "$TAB$NEWLINE"
        output += "Coords: $xCoord$yCoord"

        return output
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DangerZone

        if (xCoord != other.xCoord) return false
        if (yCoord != other.yCoord) return false

        return true
    }

    override fun hashCode(): Int {
        var result = inUse.hashCode()
        result = 31 * result + isOutput.hashCode()
        result = 31 * result + sideLength
        result = 31 * result + xCoord
        result = 31 * result + yCoord
        result = 31 * result + connectedDangerZones.hashCode()
        result = 31 * result + connectedIndex
        return result
    }
}