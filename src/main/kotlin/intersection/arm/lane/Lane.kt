package intersection.arm.lane

import intersection.dangerZone.DangerZone
import intersection.stage.light.Light
import utils.Constants.DEFAULT_LANE_WIDTH
import utils.Constants.NORMAL_LIGHT
import utils.Constants.ONE
import utils.Constants.TWO
import utils.Constants.ZERO

class Lane constructor(
    usage_: LaneUsage = LaneUsage.Left,
    width_: Double = DEFAULT_LANE_WIDTH,
    light_: Light = Light(NORMAL_LIGHT)
) {
    var width: Double
    var light: Light
    var usage: LaneUsage
    lateinit var startDangerZone: DangerZone
    lateinit var endDangerZone: DangerZone

    init {
        width = width_
        light = light_
        usage = usage_
    }

    fun setShortestPath(dangerZones: ArrayList<DangerZone>, armNr: Int, laneNr: Int, isOutput: Boolean) {

    }

    fun setStartDangerZone(dangerZones: ArrayList<DangerZone>, armNr: Int, laneNr: Int, numLanes: Int): DangerZone {
        val index: Int = when (armNr) {
            ZERO -> laneNr + armNr
            ONE -> (numLanes * (laneNr + armNr)) - armNr
            TWO -> (numLanes * armNr) + laneNr
            else -> numLanes * laneNr
        }
        startDangerZone = dangerZones[index]
        return startDangerZone
    }

    override fun toString(): String {
        return "Lane(width=$width, light=$light, usage=$usage)"
    }
}