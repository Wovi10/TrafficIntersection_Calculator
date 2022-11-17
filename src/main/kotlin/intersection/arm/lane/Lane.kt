package intersection.arm.lane

import intersection.stage.light.Light
import utils.Constants.DEFAULT_LANE_USAGE
import utils.Constants.defaultLight

class Lane {
    var width: Double = 0.0
    var light: Light = defaultLight
    var usage: LaneUsage = DEFAULT_LANE_USAGE
}