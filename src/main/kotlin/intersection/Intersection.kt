package intersection

import intersection.arm.Arm
import intersection.arm.lane.Lane
import intersection.arm.lane.LaneUsage.*
import intersection.stage.Stage
import intersection.stage.light.Light
import utils.Constants.DEFAULT_DOUBLE
import utils.Constants.ONE
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

    fun calculateThroughTime(): Double {
        var t_through = DEFAULT_DOUBLE
        var speed: Double
        var distance: Double

        var armCounter = ZERO
        for (arm in arms) {
            var laneCounter = ONE
            for (lane in arm.lanes) {
                val destinationArm = arms[armCounter + laneCounter]
                speed = destinationArm.speed
                when (lane.usage) {
                    Left -> {
                        distance = calculateDistanceToCover(arm, lane, destinationArm)
                    }

                    Straight -> {
                    }

                    Right -> {
                    }
                }
                laneCounter++
            }
            armCounter++
        }

        return t_through
    }

    private fun calculateDistanceToCover(
        arm: Arm,
        lane: Lane,
        destinationArm: Arm
    ): Double {
        return calculateOutputLanesToCover(arm, lane) + calculateInputLanesToCover(destinationArm)
    }

    private fun calculateInputLanesToCover(destinationArm: Arm): Double {
        return destinationArm.inputLanesNum * destinationArm.lanes[ZERO].width
    }

    private fun calculateOutputLanesToCover(arm: Arm, lane: Lane): Double {
        return (arm.outputLanesNum * lane.width)
    }

    fun printStages() {
        printArrayList(stages)
    }

    fun printArms() {
        printArray(arms)
    }
}