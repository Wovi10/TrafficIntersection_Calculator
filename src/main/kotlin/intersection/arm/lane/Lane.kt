package intersection.arm.lane

import intersection.arm.Arm
import intersection.dangerZone.DangerZone
import intersection.stage.light.Light
import utils.Constants.DEFAULT_LANE_WIDTH
import utils.Constants.NORMAL_LIGHT
import utils.Constants.ONE
import utils.Constants.TWO

class Lane constructor(
    usage_: LaneUsage = LaneUsage.Left,
    width_: Double = DEFAULT_LANE_WIDTH,
    light_: Light = Light(NORMAL_LIGHT)
){
    var width: Double
    var light: Light
    var usage: LaneUsage
    lateinit var dangerZone: DangerZone

    init {
        width = width_
        light = light_
        usage = usage_
    }

    fun setDangerZone(dangerZones: ArrayList<DangerZone>, armNr: Int, laneNr: Int, isOutput: Boolean): DangerZone{
        if (isOutput){
            if(armNr == ONE){
                dangerZone = dangerZones[]
            }else if (armNr == TWO){
                dangerZone = DangerZone()
            }
        }
        return dangerZone
    }

    override fun toString(): String {
        return "Lane(width=$width, light=$light, usage=$usage)"
    }
}