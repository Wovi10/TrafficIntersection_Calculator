package intersection.arm.lane

import intersection.stage.light.Light
import utils.Constants.DEFAULT_LANE_WIDTH

class Lane constructor(
    usage_: LaneUsage = LaneUsage.Left,
    width_: Double = DEFAULT_LANE_WIDTH,
    light_: Light = Light()
){
    var width: Double
    var light: Light
    var usage: LaneUsage
    init {
        width = width_
        light = light_
        usage = usage_
    }

    override fun toString(): String {
        return "Lane(width=$width, light=$light, usage=$usage)"
    }


}