package intersection.stage

import intersection.stage.light.Light
import intersection.stage.light.LightState
import utils.Constants.CAR_STAGE_NAME
import utils.Constants.DEFAULT_STAGE_TIME
import utils.Constants.EMPTY_STRING
import utils.Constants.NEWLINE
import utils.Constants.PED_LIGHT
import utils.Constants.PED_STAGE_NAME
import utils.Constants.SPACE
import utils.Constants.TAB
import utils.Functions.printArrayList

class Stage(stageNum_: Int, duration_: Double = DEFAULT_STAGE_TIME) {
    private var duration: Double
    var lights: ArrayList<Light> = ArrayList()
    private var name: String = EMPTY_STRING
    private var stageNumber: Int

    init {
        stageNumber = stageNum_
        duration = duration_
    }

    fun calculateStates(allLights: ArrayList<ArrayList<Light>>, lightName: String){
        if (lightName == PED_LIGHT) {
            addPedStage(allLights)
            return
        }else{
            addStage(allLights)
            return
        }
    }

    private fun addStage(allLights: ArrayList<ArrayList<Light>>) {
        name = CAR_STAGE_NAME
        for (armLight in allLights) {
            for (light in armLight) {
                if(!pathIsObstructed(light)){
                    putPathInUse(light)
                    addLightToStage(light)
                }
            }
        }
    }

    private fun putPathInUse(light: Light) {
        for (dangerZone in light.lane.path) {
            dangerZone.inUse = true
        }
    }

    private fun pathIsObstructed(light: Light): Boolean {
        for (dangerZone in light.lane.path) {
            if (dangerZone.inUse){
                return true
            }
        }
        return false
    }

    private fun addPedStage(allLights: ArrayList<ArrayList<Light>>) {
        name = PED_STAGE_NAME
        for (armLights in allLights) {
            for (light in armLights) {
                if (light.name == PED_LIGHT) {
                    addLightToStage(light)
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
        for (light in lights) {
            output += light.name
            output += SPACE
            output += light.lane.startDangerZone
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