package intersection

import intersection.arm.Arm
import intersection.arm.lane.Lane
import intersection.arm.lane.LaneUsage.*
import intersection.stage.Stage
import intersection.stage.light.Light
import utils.Constants.DEFAULT_ARM_NUM
import utils.Constants.ONE
import utils.Constants.THREE
import utils.Constants.TWO
import utils.Constants.ZERO
import utils.Functions.printArray
import utils.Functions.printArrayList

class Intersection {
    private var numArms: Int
    var arms: Array<Arm>
    private val hasPedCross: Boolean

    constructor(numArms_: Int = DEFAULT_ARM_NUM, hasPedCross_: Boolean = true) {
        numArms = numArms_
        arms = initArms()
        hasPedCross = hasPedCross_
    }

    constructor(arms_: Array<Arm>, hasPedCross_: Boolean = true) {
        arms = arms_
        numArms = arms.size
        hasPedCross = hasPedCross_
    }

    private var stages: ArrayList<Stage>
    private var numLights: Int
    private var intersectionLights: Array<Array<Light>>
    val throughTimes: ArrayList<Double>

    init {
        numLights = initNumLights()
        intersectionLights = initLights()
        stages = calculateStages()
        throughTimes = calculateThroughTime()
    }

    private fun initNumLights(): Int {
        var output = ZERO
        for (arm in arms) {
            output += arm.getLights().size
        }
        return output
    }

    private fun initLights(): ArrayList<Array<Light>> {
        val output: ArrayList<ArrayList<Light>> = ArrayList()
        for (arm in arms){
            val lightsToAdd: ArrayList<Light> = ArrayList()
            lightsToAdd.add(arm.getLights())
        }
        intersectionLights = Array(numArms) { i ->
            arms[i].getLights()
        }
        return intersectionLights
    }

    private fun calculateStages(): ArrayList<Stage> {
        val output: ArrayList<Stage> = ArrayList()
        if(hasPedCross) addPedStage(output)
        for (lightsOfArm in intersectionLights) {
            for (light in lightsOfArm) {
                calculateStage(light, output)
            }
        }
//        if (!allLightsAssigned()) calculateStages()
        return output
    }

    private fun calculateStage(light: Light, output: ArrayList<Stage>) {
        if (light.assigned) return
        val stageToAdd = Stage()
        stageToAdd.calculateStates()
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

    private fun calculateThroughTime(): ArrayList<Double> {
        val throughTimeArray: ArrayList<Double> = ArrayList()
        var armCounter = ZERO

        for (arm in arms) {
            for (i in ZERO until arm.inputLanesNum) {
                val lane = arm.lanes[i]
                val speed = calculateSpeed(arms, armCounter, i + ONE)
                val distance = calculateDistanceToCover(arms, lane, armCounter, i + ONE)
                val throughTime = distance / speed
                throughTimeArray.add(throughTime)
            }
            armCounter++
        }

        return throughTimeArray
    }

    private fun calculateSpeed(arms: Array<Arm>, armCounter: Int, laneCounter: Int): Double {
        val thisLane = arms[armCounter].lanes[laneCounter]
        val destinationIndex =
            getDestinationIndex(armCounter, laneCounter, arms)
        val destinationArm = arms[destinationIndex]
        val divisor: Int = when (thisLane.usage) {
            Left -> THREE
            Straight -> ONE
            Right -> THREE
        }
        println("$thisLane $divisor")
        return destinationArm.speed / divisor
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

        val thisArm = arms[armCounter]
        val nextArm = arms[nextIndex]
        val destinationArm = arms[destinationIndex]

        val halfThisLane = lane.width / TWO
        val halfDestLane = (destinationArm.lanes[ZERO].width / TWO)

        val distance: Double = when (lane.usage) {
            Left -> calculateOutputLanesToCover(thisArm) + calculateInputLanesToCover(destinationArm) + halfThisLane
            Straight -> nextArm.numLanes * nextArm.lanes[ZERO].width
            Right -> halfThisLane + halfDestLane
        }
        return distance
    }

    private fun getDestinationIndex(armCounter: Int, laneCounter: Int, arms: Array<Arm>): Int {
        return if (armCounter + laneCounter >= arms.size) (armCounter - arms.size) + laneCounter
        else armCounter + laneCounter
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
