package intersection.arm.lane

import intersection.stage.light.Light
import utils.Constants.DEFAULT_LANE_WIDTH

class Lane constructor(
    width_: Double = DEFAULT_LANE_WIDTH,
    light_: Light = Light(),
    usage_: LaneUsage = LaneUsage.Left
){
    var width: Double
    var light: Light
    var usage: LaneUsage
    init {
        width = width_
        light = light_
        usage = usage_
    }
}