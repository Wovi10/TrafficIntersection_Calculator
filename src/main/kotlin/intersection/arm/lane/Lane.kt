package intersection.arm.lane

import intersection.arm.Arm
import intersection.arm.lane.LaneUsage.*
import intersection.dangerZone.DangerZone
import intersection.stage.light.Light
import utils.Constants.DEFAULT_LANE_WIDTH
import utils.Constants.EMPTY_STRING
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
        setEndDangerZone(arms, armCounter)
        getShortestPath(dangerZones)
//        printPath()
    }

    private fun getShortestPath(dangerZones: ArrayList<DangerZone>) {
        var shortestPath: ArrayList<DangerZone> = initArrayList(dangerZones)
        var pathTried: ArrayList<DangerZone>
        repeat(dangerZones.size) { timesTried ->
            pathTried = tryPath(shortestPath, timesTried)
            if (pathTried.size < shortestPath.size || timesTried == ZERO) shortestPath = pathTried
        }

        path = shortestPath
    }

    private fun initArrayList(dangerZones: ArrayList<DangerZone>): ArrayList<DangerZone> {
        val output: ArrayList<DangerZone> = ArrayList()
        for (dangerZone in dangerZones) {
            output.add(dangerZone)
        }
        return output
    }

    private fun tryPath(shortestPath: ArrayList<DangerZone>, timesTried: Int): ArrayList<DangerZone> {
        val pathTrying: ArrayList<DangerZone> = ArrayList()
        val currentDangerZone = startDangerZone
        pathTrying.add(currentDangerZone)
        val hasArrived = tryConnectedDangerZone(currentDangerZone, pathTrying, shortestPath.size)

        if (!hasArrived || (pathTrying.size >= shortestPath.size && timesTried != ZERO)) return shortestPath
        return pathTrying
    }

    private fun tryConnectedDangerZone(
        currentDangerZone: DangerZone,
        pathTrying: ArrayList<DangerZone>,
        shortestPathSize: Int,
        timesTried: Int = ZERO,
        inLoop: Int = ZERO
    ): Boolean {
        val directionIndex = currentDangerZone.connectedIndex
        val nextDangerZone = currentDangerZone.connectedDangerZones[directionIndex]
        currentDangerZone.setNextConnectedIndex()
        if (pathTrying.contains(nextDangerZone)){
            if (inLoop == FOUR) return false
            tryConnectedDangerZone(currentDangerZone, pathTrying, shortestPathSize, timesTried + ONE, inLoop + ONE)
        }
        pathTrying.add(nextDangerZone)


        if (pathTrying.size >= shortestPathSize && timesTried != ZERO) return false

        if (nextDangerZone != endDangerZone) {
            tryConnectedDangerZone(nextDangerZone, pathTrying, shortestPathSize, timesTried + ONE)
        }
        return true
    }

    private fun setEndDangerZone(arms: Array<Arm>, armCounter: Int) {
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
        val armToUse: Arm = if (armCounter != arms.size - ONE) {
            arms[armCounter + ONE]
        } else arms[ZERO]

        return armToUse.outputDangerZones[ZERO]
    }

    fun setStartDangerZone(dangerZones: ArrayList<DangerZone>, armNr: Int, laneNr: Int, numLanes: Int): DangerZone {
        val index: Int = when (armNr) {
            ZERO -> laneNr + armNr
            ONE -> (numLanes * numLanes) - (numLanes * (laneNr+ONE))
            TWO -> (numLanes * (armNr + ONE)) + laneNr
            FOUR -> armNr * (laneNr + ONE) - ONE
            else -> ZERO
        }
        startDangerZone = dangerZones[index]
        return startDangerZone
    }

    fun printPath(pathToPrint: ArrayList<DangerZone> = path) {
        var output = EMPTY_STRING
        for (dangerZone in pathToPrint) {
            output += ", ${dangerZone.xCoord}${dangerZone.yCoord}"
        }
        output = output.drop(TWO)
        println(output)
    }

    override fun toString(): String {
        return "Lane(width=$width, light=$light, usage=$usage)"
    }
}