package intersection.arm

import intersection.arm.lane.Lane
import intersection.arm.lane.LaneUsage
import intersection.dangerZone.DangerZone
import intersection.stage.light.Light
import utils.Constants.DEFAULT_ARM_SPEED
import utils.Constants.DEFAULT_ARM_WIDTH
import utils.Constants.DEFAULT_INPUT_LANES_NUM
import utils.Constants.DEFAULT_OUTPUT_LANES_NUM
import utils.Constants.ONE
import utils.Constants.ZERO
import utils.Constants.ZERO_DOUBLE

class Arm constructor(
    inputLanesNum_: Int = DEFAULT_INPUT_LANES_NUM,
    outputLanesNum_: Int = DEFAULT_OUTPUT_LANES_NUM,
    v_mPerS_: Double = DEFAULT_ARM_SPEED
) {
    var inputLanesNum: Int
    var outputLanesNum: Int
    var numLanes: Int
    var lanes: ArrayList<Lane>
    private var width: Double
    var speed: Double
    var outputDangerZones: ArrayList<DangerZone> = ArrayList()

    init {
        inputLanesNum = inputLanesNum_
        outputLanesNum = outputLanesNum_
        numLanes = inputLanesNum + outputLanesNum
        speed = v_mPerS_
        lanes = initLanes()
        width = initWidth()
    }

    private fun initWidth(): Double {
        var totalWidth = ZERO_DOUBLE
        for (lane in lanes) {
            totalWidth += lane.width
        }

        if (totalWidth < DEFAULT_ARM_WIDTH) return DEFAULT_ARM_WIDTH
        return totalWidth
    }

    private fun initLanes(): ArrayList<Lane> {
        val output: ArrayList<Lane> = ArrayList()
        var counter = ZERO
        var laneUsage: LaneUsage = LaneUsage.Output
        repeat(numLanes) {
            val laneToAdd = Lane(laneUsage)
            laneUsage = laneUsage.next()
            if (counter == inputLanesNum - ONE) counter = ZERO else counter++
            output.add(laneToAdd)
        }
        return output
    }

    fun getLights(): ArrayList<Light> {
        val lights: ArrayList<Light> = ArrayList()
        for (lane in lanes) {
            lights.add(lane.light)
            lane.light.lane = lane
        }
        return lights
    }

    fun setOutputDangerZones() {
        for (lane in lanes) {
            if (lane.usage == LaneUsage.Output) outputDangerZones.add(lane.startDangerZone)
        }
    }

    fun setStartDangerZones(allDangerZones: ArrayList<DangerZone>, armNr: Int) {
        var laneCounter = ZERO
        for (lane in lanes) {
            lane.setStartDangerZone(allDangerZones, armNr, laneCounter, numLanes)
            laneCounter++
        }
    }
}