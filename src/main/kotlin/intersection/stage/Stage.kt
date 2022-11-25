package intersection.stage

import intersection.arm.Arm
import intersection.arm.lane.Lane
import intersection.stage.light.Light
import intersection.stage.light.LightState
import utils.Constants.NORMAL_STAGE_NAME
import utils.Constants.DEFAULT_STAGE_TIME
import utils.Constants.EMPTY_STRING
import utils.Constants.NEWLINE
import utils.Constants.NORMAL_LIGHT
import utils.Constants.PED_LIGHT
import utils.Constants.PED_STAGE_NAME
import utils.Constants.SPACE
import utils.Constants.TAB
import utils.Constants.ZERO

class Stage(stageNum_: Int, duration_: Double = DEFAULT_STAGE_TIME) {
    private var duration: Double
    var lights: ArrayList<Light> = ArrayList()
    var lanes: ArrayList<Lane> = ArrayList()
    private var name: String = EMPTY_STRING
    private var stageNumber: Int

    init {
        stageNumber = stageNum_
        duration = duration_
    }

    fun calculateStates(arms: Array<Arm>, lightName: String = NORMAL_LIGHT){
        if (lightName == PED_LIGHT) {
            addPedStage(arms)
            return
        }else{
            addStage(arms)
            return
        }
    }

    private fun addStage(arms: Array<Arm>) {
        name = NORMAL_STAGE_NAME
        for (arm in arms) {
            for (lane in arm.lanes) {
                val lightToUse = lane.light
                if(!pathIsObstructed(lane)){
                    putPathInUse(lane)
                    addLightToStage(lightToUse)
                    lanes.add(lane)
                }
            }
        }
    }

    private fun putPathInUse(lane: Lane) {
        for (dangerZone in lane.path) {
            dangerZone.inUse = true
        }
    }

    private fun pathIsObstructed(lane: Lane): Boolean {
        for (dangerZone in lane.path) {
            if (dangerZone.inUse){
                return true
            }
        }
        return false
    }

    private fun addPedStage(arms: Array<Arm>) {
        name = PED_STAGE_NAME
        for (arm in arms) {
            for (lane in arm.lanes) {
                val lightToUse = lane.light
                if (lightToUse.name == PED_LIGHT) {
                    addLightToStage(lightToUse)
                }
            }
        }
    }

    private fun addLightToStage(light: Light) {
        light.state = LightState.Green
        light.assigned = true
        lights.add(light)
    }

    fun getLightsToPrint(): String{
        var output = EMPTY_STRING
        for (i in ZERO until lights.size){
            output += lights[i].name
            output += SPACE
            output += lanes[i].startDangerZone
        }
        return output
    }

    private fun getLights(): String{
        var output: String = EMPTY_STRING
        for (light in lights) {
            output += TAB + TAB + light
            output += NEWLINE
        }
        return output
    }

    override fun toString(): String {
        return "Stage $stageNumber:$NEWLINE" +
                "$TAB duration: $duration, name: $name,$NEWLINE" +
                "$TAB lights:$NEWLINE" +
                getLights()
    }


}