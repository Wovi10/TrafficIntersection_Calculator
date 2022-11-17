package intersection

import intersection.arm.Arm
import intersection.arm.lane.Lane
import intersection.arm.lane.LaneUsage.*
import intersection.stage.Stage
import intersection.stage.light.Light
import utils.Constants.FOUR
import utils.Constants.ONE
import utils.Constants.TWO
import utils.Constants.ZERO
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
        intersectionLights = Array(numArms) { i ->
            arms[i].getLights()
        }
        return intersectionLights
    }

    private fun calculateStages(): ArrayList<Stage> {
        val output: ArrayList<Stage> = ArrayList()
//        for (i in intersectionLights) {
//            val stage = Stage()
//            stage.calculateStates()
//            output.add(stage)
//        }
//        if (!allLightsAssigned()) calculateStages()
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
        return Array(numArms) { Arm() }
    }

    fun calculateThroughTime(): ArrayList<Double> {
        val throughTimeArray: ArrayList<Double> = ArrayList()
        var armCounter = ZERO

        for (arm in arms) {
            var laneCounter = ONE

            for (lane in arm.lanes) {
                val speed = calculateSpeed(arms, armCounter, laneCounter)
                val distance = calculateDistanceToCover(arms, lane, armCounter, laneCounter)
                val throughTime = distance / speed
                throughTimeArray.add(throughTime)
                laneCounter++
                if (laneCounter > arm.inputLanesNum) break
            }
            armCounter++
        }

        return throughTimeArray
    }

    private fun calculateSpeed(arms: Array<Arm>, armCounter: Int, laneCounter: Int): Double {
        val destinationIndex =
            getDestinationIndex(armCounter, laneCounter, arms)
        val destinationArm = arms[destinationIndex]
        return destinationArm.speed / FOUR
    }

    private fun calculateDistanceToCover(
        arms: Array<Arm>,
        lane: Lane,
        armCounter: Int,
        laneCounter: Int
    ): Double {
        val nextIndex = if (armCounter + ONE != arms.size) armCounter + ONE else ZERO
        val destinationIndex =
            getDestinationIndex(armCounter, laneCounter, arms)
        val halfThisLane = lane.width / TWO
        val thisArm = arms[armCounter]
        val nextArm = arms[nextIndex]
        val destinationArm = arms[destinationIndex]
        val halfDestLane = (destinationArm.lanes[ZERO].width / TWO)
        val distance: Double = when (lane.usage) {
            Left -> calculateOutputLanesToCover(thisArm) + calculateInputLanesToCover(destinationArm) + halfThisLane
            Straight -> nextArm.numLanes * nextArm.lanes[ZERO].width
            Right -> halfThisLane + halfDestLane
        }
        return distance
    }

    private fun getDestinationIndex(armCounter: Int, laneCounter: Int, arms: Array<Arm>) =
        if (armCounter + laneCounter >= arms.size) {
            (armCounter - arms.size) + laneCounter
        } else armCounter + laneCounter

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