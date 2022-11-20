package intersection.arm.lane

import intersection.arm.Arm
import intersection.arm.lane.LaneUsage.*
import intersection.dangerZone.DangerZone
import intersection.stage.light.Light
import utils.Constants.DEFAULT_LANE_WIDTH
import utils.Constants.FOUR
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
    lateinit var path: ArrayList<DangerZone>

    init {
        width = width_
        light = light_
        usage = usage_
    }

    fun setShortestPath(dangerZones: ArrayList<DangerZone>, arms: Array<Arm>, armCounter: Int) {
        setEndDangerZone(dangerZones, arms, armCounter)
        getShortestPath(dangerZones)
    }

    private fun getShortestPath(dangerZones: ArrayList<DangerZone>) {
        var shortestPath: ArrayList<DangerZone> = ArrayList()
        var pathTried: ArrayList<DangerZone>
        repeat(dangerZones.size){timesTried ->
            pathTried = tryPath(shortestPath, timesTried)
            if (pathTried.size < shortestPath.size || timesTried == ZERO){
                shortestPath = pathTried
            }
        }

        path = shortestPath
        TODO("Not yet implemented")
    }

    private fun tryPath(shortestPath: ArrayList<DangerZone>, timesTried: Int): ArrayList<DangerZone> {
        val connectToTry: Int = when (ZERO) {
            timesTried % FOUR -> ZERO
            timesTried % THREE -> ONE
            timesTried % TWO -> TWO
            else -> THREE
        }
        val pathTrying: ArrayList<DangerZone> = ArrayList()
        var currentDangerZone = startDangerZone
        pathTrying.add(currentDangerZone)
        while (currentDangerZone != endDangerZone){
            currentDangerZone = currentDangerZone.connectedDangerZones[connectToTry]
            pathTrying.add(currentDangerZone)
            if (pathTrying.size >= shortestPath.size && timesTried > ZERO) return shortestPath
        }

        return pathTrying
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
            arms[armCounter - ONE]
        } else arms[arms.size - ONE]
        val index = armToUSe.outputDangerZones.size - ONE

        return armToUSe.outputDangerZones[index]
    }

    private fun setEndStraightArm(arms: Array<Arm>, armCounter: Int): DangerZone {
        val armToUse: Arm = if (armCounter >= arms.size / TWO) {
            arms[armCounter - TWO]
        } else arms[armCounter + TWO]
        var index = ZERO
        if (armToUse.outputDangerZones.size >= THREE) index += ONE

        return armToUse.outputDangerZones[index]
    }

    private fun setEndNextArm(arms: Array<Arm>, armCounter: Int): DangerZone {
        val armToUse: Arm = if (armCounter != arms.size) {
            arms[armCounter + ONE]
        } else arms[ZERO]

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