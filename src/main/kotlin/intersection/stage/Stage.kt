package intersection.stage

import intersection.stage.light.Light
import intersection.stage.light.LightState
import utils.Constants.CAR_STAGE_NAME
import utils.Constants.DEFAULT_STAGE_TIME
import utils.Constants.EMPTY_STRING
import utils.Constants.NEWLINE
import utils.Constants.PED_LIGHT
import utils.Constants.PED_STAGE_NAME
import utils.Constants.TAB
import utils.Functions.getArrayList
import utils.Functions.printArrayList

class Stage(stageNum_: Int, duration_: Double = DEFAULT_STAGE_TIME) {
    private var duration: Double
    private var lights: ArrayList<Light> = ArrayList()
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
        }
//        addCarStage(allLights)
    }

    private fun addCarStage(allLights: ArrayList<ArrayList<Light>>) {
        name = CAR_STAGE_NAME
        for (armLight in allLights) {
            for (light in armLight) {
                
            }
        }
        TODO("Not yet implemented")
    }

    private fun addPedStage(allLights: ArrayList<ArrayList<Light>>) {
        name = PED_STAGE_NAME
        for (armLights in allLights) {
            for (light in armLights) {
                if (light.name == PED_LIGHT) {
                    light.state = LightState.Green
                    light.assigned = true
                    lights.add(light)
                }
            }
        }
    }

    fun printLights(){
        printArrayList(lights)
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