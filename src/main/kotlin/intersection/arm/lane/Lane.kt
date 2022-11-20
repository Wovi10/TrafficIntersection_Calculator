package intersection.arm.lane

import intersection.arm.Arm
import intersection.arm.lane.LaneUsage.*
import intersection.dangerZone.DangerZone
import intersection.stage.light.Light
import utils.Constants.DEFAULT_LANE_WIDTH
import utils.Constants.NORMAL_LIGHT
import utils.Constants.ONE
import utils.Constants.THREE
import utils.Constants.TWO
import utils.Constants.ZERO

class Lane constructor(
    usage_: LaneUsage = Left,
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

    fun setShortestPath(dangerZones: ArrayList<DangerZone>, arms: Array<Arm>, armCounter: Int) {
        setEndDangerZone(dangerZones, arms, armCounter)
    }

    private fun setEndDangerZone(dangerZones: ArrayList<DangerZone>, arms: Array<Arm>, armCounter: Int) {
        endDangerZone = when (this.usage) {
            Left -> setEndNextArm(arms, armCounter)
            Straight -> setEndStraightArm(arms, armCounter)
            Right -> setEndPreviousArm(arms, armCounter)
            Output -> startDangerZone
        }
    }

    private fun setEndPreviousArm(arms: Array<Arm>, armCounter: Int): DangerZone {
        val armToUSe: Arm = if (armCounter != ZERO) {
            arms[arms.size - ONE]
        } else {
            arms[armCounter - ONE]
        }
        val index = armToUSe.outputDangerZones.size - ONE
        return armToUSe.outputDangerZones[index]
    }

    private fun setEndStraightArm(arms: Array<Arm>, armCounter: Int): DangerZone {
        val armToUse: Arm = if (armCounter >= arms.size / TWO) {
            arms[armCounter - TWO]
        } else {
            arms[armCounter + TWO]
        }
        var index = ZERO
        if (armToUse.outputDangerZones.size >= THREE) index += ONE
        return armToUse.outputDangerZones[index]
    }

    private fun setEndNextArm(arms: Array<Arm>, armCounter: Int): DangerZone {
        val armToUse: Arm = if (armCounter != arms.size) {
            arms[armCounter + ONE]
        } else {
            arms[ZERO]
        }
        return armToUse.outputDangerZones[ZERO]
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