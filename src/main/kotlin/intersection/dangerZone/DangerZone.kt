package intersection.dangerZone

import utils.Constants.ONE

class DangerZone(xCoord_: Int, yCoord_: Int) {
    var inUse: Boolean = false
    private val xCoord: Int = xCoord_
    private val yCoord: Int = yCoord_
    val connectedDangerZones: ArrayList<DangerZone> = ArrayList()

    fun setConnectedDangerZones(allDangerZones: ArrayList<DangerZone>) {
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
            val succeeded = tryDangerZone(dangerZone, vertToTry, horiToTry)
            if (succeeded) return
        }
    }

    private fun tryDangerZone(dangerZone: DangerZone, vertToTry: Int, horiToTry: Int): Boolean {
        if (dangerZone.xCoord != vertToTry) return false
        if (dangerZone.yCoord != horiToTry) return false
        connectedDangerZones.add(dangerZone)
        return true
    }
}