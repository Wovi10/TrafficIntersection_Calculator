package intersection.dangerZone

import utils.Constants.EMPTY_STRING
import utils.Constants.NEWLINE
import utils.Constants.ONE
import utils.Constants.TAB
import utils.Constants.ZERO

class DangerZone(xCoord_: Int = ZERO, yCoord_: Int = ZERO, isOutput_: Boolean = false) {
    var inUse: Boolean = false
    var isOutput: Boolean = isOutput_
    var sideLength: Int = ZERO
    val xCoord: Int = xCoord_
    val yCoord: Int = yCoord_
    val connectedDangerZones: ArrayList<DangerZone> = ArrayList()
    var connectedIndex: Int = ONE

    fun setConnectedDangerZones(allDangerZones: ArrayList<DangerZone>) {
        sideLength = allDangerZones.size
        setDangerZone(allDangerZones, xCoord - ONE, yCoord)
        setDangerZone(allDangerZones, xCoord, yCoord - ONE)
        setDangerZone(allDangerZones, xCoord, yCoord + ONE)
        setDangerZone(allDangerZones, xCoord + ONE, yCoord)
    }

    private fun setDangerZone(allDangerZones: ArrayList<DangerZone>, vertToTry: Int, horiToTry: Int){
        for (dangerZone in allDangerZones) {
            val succeeded = tryDangerZone(dangerZone, vertToTry, horiToTry, sideLength)
            if (succeeded) return
        }
    }

    private fun tryDangerZone(dangerZone: DangerZone, xToTry: Int, yToTry: Int, sideLength: Int): Boolean {
        if (dangerZone.xCoord != xToTry || dangerZone.yCoord != yToTry) return false
        connectedDangerZones.add(dangerZone)
        return true
    }

    private fun betweenZeroAndSideLength(vertToTry: Int, sideLength: Int) =
        !smallerThanZero(vertToTry) && !biggerThanSideLength(vertToTry, sideLength)

    private fun biggerThanSideLength(vertToTry: Int, sideLength: Int) = vertToTry > sideLength

    private fun smallerThanZero(vertToTry: Int) = vertToTry < ZERO
    fun getConnectedDangerZone(): DangerZone {
        TODO("Not yet implemented")
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


}