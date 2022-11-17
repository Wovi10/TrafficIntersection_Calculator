package intersection.arm

import intersection.arm.lane.Lane
import intersection.stage.light.Light
import utils.Constants.DEFAULT_DOUBLE
import utils.Constants.DEFAULT_INPUT_LANES_NUM
import utils.Constants.DEFAULT_OUTPUT_LANES_NUM
import utils.Constants.DEFAULT_ARM_SPEED
import utils.Constants.DEFAULT_ARM_WIDTH

class Arm constructor(
    inputLanesNum_: Int = DEFAULT_INPUT_LANES_NUM,
    outputLanesNum_: Int = DEFAULT_OUTPUT_LANES_NUM,
    v_mPerS_: Double = DEFAULT_ARM_SPEED
) {
    var inputLanesNum: Int
    var outputLanesNum: Int
    var numLanes: Int
    var lanes: Array<Lane>
    private var width: Double
    var speed: Double

    init {
        inputLanesNum = inputLanesNum_
        outputLanesNum = outputLanesNum_
        numLanes = inputLanesNum + outputLanesNum
        speed = v_mPerS_
        lanes = initLanes()
        width = initWidth()
    }

    private fun initWidth(): Double {
        var totalWidth = DEFAULT_DOUBLE
        for (lane in lanes) {
            totalWidth += lane.width
        }

        if (totalWidth < DEFAULT_ARM_WIDTH) return DEFAULT_ARM_WIDTH
        return totalWidth
    }

    private fun initLanes(): Array<Lane> {
        return Array(numLanes) { Lane() }
    }

    fun getLights(): Array<Light> {
        val lights: Array<Light> = Array(numLanes){i ->
            lanes[i].light
        }
        return lights
    }
}