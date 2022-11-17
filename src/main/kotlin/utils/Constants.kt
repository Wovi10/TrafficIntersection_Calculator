package utils

import intersection.arm.Arm
import intersection.arm.lane.Lane
import intersection.arm.lane.LaneUsage
import intersection.stage.light.Light

object Constants {
    val defaultArm: Arm = TODO()
    val defaultLane: Lane = TODO()
    val defaultLight: Light = TODO()

    const val MINIMUM_ARM_WIDTH: Double = 3.15
    const val DEFAULT_SPEED_ARM: Double = 13.889

    const val DEFAULT_DOUBLE: Double = 0.0

    const val DEFAULT_INPUT_LANES: Int = 3
    const val DEFAULT_OUTPUT_LANES: Int = 1

    val DEFAULT_LANE_USAGE: LaneUsage = LaneUsage.Left

    const val DEFAULT_STAGE_DURATION: Double = 5.0

    const val EMPTY_STRING: String = ""
    const val NEWLINE: String = "\n"
    const val ONE: Int = 1
    const val ZERO: Int = 0
}