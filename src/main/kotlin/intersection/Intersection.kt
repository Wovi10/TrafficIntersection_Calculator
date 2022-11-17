package intersection

import intersection.arm.Arm
import intersection.arm.lane.LaneUsage
import intersection.stage.Stage
import intersection.stage.light.Light
import utils.Constants.DEFAULT_DOUBLE
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
        var counter = ZERO
        for (arm in arms) {
            for (lane in arm.lanes) {
                when(lane.usage){
                    LaneUsage.Left -> TODO()
                    LaneUsage.Straight -> TODO()
                    LaneUsage.Right -> TODO()
                }
            }
            counter++
        }

        return t_through
    }

    fun printStages() {
        printArrayList(stages)
    }

    fun printArms() {
        printArray(arms)
    }
}