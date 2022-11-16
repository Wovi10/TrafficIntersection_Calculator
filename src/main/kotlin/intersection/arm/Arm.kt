package intersection.arm

import intersection.arm.lane.Lane
import intersection.stage.light.Light
import utils.Constants.DEFAULT_DOUBLE
import utils.Constants.DEFAULT_INPUT_LANES
import utils.Constants.DEFAULT_OUTPUT_LANES
import utils.Constants.DEFAULT_SPEED_ARM
import utils.Constants.MINIMUM_ARM_WIDTH
import utils.Constants.defaultLane

class Arm constructor(
    inputLanesNum_: Int = DEFAULT_INPUT_LANES,
    outputLanesNum_: Int = DEFAULT_OUTPUT_LANES,
    v_mPerS_: Double = DEFAULT_SPEED_ARM
) {
    private var inputLanesNum: Int
    private var outputLanesNum: Int
    private var numLanes: Int
    var lanes: Array<Lane>
    private var width: Double
    private var speed: Double

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

        if (totalWidth < MINIMUM_ARM_WIDTH) return MINIMUM_ARM_WIDTH
        return totalWidth
    }

    private fun initLanes(): Array<Lane> {
        return Array(numLanes) { defaultLane }
    }

    fun getLights(): Array<Light> {
        val lights: Array<Light> = Array(numLanes){i ->
            lanes[i].light
        }
        return lights
    }
}