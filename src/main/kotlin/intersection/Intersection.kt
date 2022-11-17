package intersection

import intersection.arm.Arm
import intersection.arm.lane.Lane
import intersection.arm.lane.LaneUsage.*
import intersection.stage.Stage
import intersection.stage.light.Light
import utils.Constants.ONE
import utils.Constants.TWO
import utils.Constants.ZERO
import utils.Constants.defaultArm
import utils.Functions.printArray
import utils.Functions.printArrayList

class Intersection(numArms_: Int = 4) {
    private var numArms: Int
    private var arms: Array<Arm>
    private var stages: ArrayList<Stage>
    private var numLights: Int
    private var intersectionLights: Array<Array<Light>>

    init {
        numArms = numArms_
        arms = initArms()
        numLights = initNumLights()
        intersectionLights = initLights()
        stages = calculateStages()
    }

    private fun initNumLights(): Int {
        var output = ZERO
        for (arm in arms) {
            output += arm.getLights().size
        }
        return output
    }

    private fun initLights(): Array<Array<Light>> {
        intersectionLights = Array(numLights) { i ->
            arms[i].getLights()
        }
        return intersectionLights
    }

    private fun calculateStages(): ArrayList<Stage> {
        val output: ArrayList<Stage> = ArrayList()
        for (i in intersectionLights) {
            val stage = Stage()
            stage.calculateStates()
            output.add(stage)
        }
        if (!allLightsAssigned()) calculateStages()
        return output
    }

    private fun allLightsAssigned(): Boolean {
        for (armLight in intersectionLights) {
            for (light in armLight) {
                if (!light.assigned) return false
            }
        }
        return true
    }

    private fun initArms(): Array<Arm> {
        return Array(numArms) { defaultArm }
    }

    fun calculateThroughTime(): ArrayList<Double> {
        val throughTimeArray: ArrayList<Double> = ArrayList()
        var armCounter = ZERO

        for (arm in arms) {
            var laneCounter = ONE

            for (lane in arm.lanes) {
                val destinationArm = arms[armCounter + laneCounter]
                val speed = destinationArm.speed
                val distance = calculateDistanceToCover(arms, lane, armCounter, laneCounter)
                val throughTime = distance / speed
                throughTimeArray.add(throughTime)
                laneCounter++
            }
            armCounter++
        }

        return throughTimeArray
    }

    private fun calculateDistanceToCover(
        arms: Array<Arm>,
        lane: Lane,
        armCounter: Int,
        laneCounter: Int
    ): Double {
        val halfThisLane = lane.width / TWO
        val thisArm = arms[armCounter]
        val destinationArm = arms[armCounter + laneCounter]
        val halfDestLane = (destinationArm.lanes[ZERO].width / TWO)
        val nextArm = arms[armCounter + ONE]
        val distance: Double = when (lane.usage) {
            Left -> calculateOutputLanesToCover(thisArm) + calculateInputLanesToCover(destinationArm) + halfThisLane
            Straight -> nextArm.numLanes * nextArm.lanes[ZERO].width
            Right -> halfThisLane + halfDestLane
        }
        return distance
    }

    private fun calculateInputLanesToCover(armOfInputLanes: Arm): Double {
        return armOfInputLanes.inputLanesNum * armOfInputLanes.lanes[ZERO].width
    }

    private fun calculateOutputLanesToCover(armOfOutputLane: Arm): Double {
        return armOfOutputLane.outputLanesNum * armOfOutputLane.lanes[ZERO].width
    }

    fun printStages() {
        printArrayList(stages)
    }

    fun printArms() {
        printArray(arms)
    }
}