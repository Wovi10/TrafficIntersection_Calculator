package intersection.dangerZone

import utils.Constants.ONE
import utils.Constants.ZERO

class DangerZone(xCoord_: Int = ONE, yCoord_: Int = ONE, isOutput_: Boolean = false) {
    var inUse: Boolean = false
    var isOutput: Boolean = isOutput_
    private var sideLength: Int = ZERO
    private val xCoord: Int = xCoord_
    private val yCoord: Int = yCoord_
    val connectedDangerZones: ArrayList<DangerZone> = ArrayList()

    fun setConnectedDangerZones(allDangerZones: ArrayList<DangerZone>) {
        sideLength = allDangerZones.size
        setDangerZone(allDangerZones, xCoord - ONE, yCoord - ONE)
        setDangerZone(allDangerZones, xCoord - ONE, yCoord)
        setDangerZone(allDangerZones, xCoord - ONE, yCoord + ONE)
        setDangerZone(allDangerZones, xCoord, yCoord - ONE)
        setDangerZone(allDangerZones, xCoord, yCoord + ONE)
        setDangerZone(allDangerZones, xCoord + ONE, yCoord - ONE)
        setDangerZone(allDangerZones, xCoord + ONE, yCoord)
        setDangerZone(allDangerZones, xCoord + ONE, yCoord + ONE)
    }

    private fun setDangerZone(allDangerZones: ArrayList<DangerZone>, vertToTry: Int, horiToTry: Int){
        for (dangerZone in allDangerZones) {
            val succeeded = tryDangerZone(dangerZone, vertToTry, horiToTry, sideLength)
            if (succeeded) return
        }
    }

    private fun tryDangerZone(dangerZone: DangerZone, vertToTry: Int, horiToTry: Int, sideLength: Int): Boolean {
        if(!betweenZeroAndSideLength(vertToTry, sideLength)) return false
        if(!betweenZeroAndSideLength(horiToTry, sideLength)) return false
        connectedDangerZones.add(dangerZone)
        return true
    }

    private fun betweenZeroAndSideLength(vertToTry: Int, sideLength: Int) =
        !smallerThanZero(vertToTry) && !biggerThanSideLength(vertToTry, sideLength)

    private fun biggerThanSideLength(vertToTry: Int, sideLength: Int) = vertToTry > sideLength

    private fun smallerThanZero(vertToTry: Int) = vertToTry < ZERO
}