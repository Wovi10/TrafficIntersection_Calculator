package intersection.stage

import intersection.stage.light.Light
import utils.Constants.DEFAULT_STAGE_TIME
import utils.Constants.EMPTY_STRING
import utils.Constants.NEWLINE
import utils.Constants.PED_LIGHT
import utils.Constants.PED_STAGE_NAME
import utils.Functions.printArrayList

class Stage(duration_: Double = DEFAULT_STAGE_TIME) {
    private var duration: Double
    private var lights: ArrayList<Light> = ArrayList()
    private var name: String = EMPTY_STRING

    init {
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

        TODO("Not yet implemented")
    }

    private fun addPedStage(allLights: ArrayList<ArrayList<Light>>) {
        name = PED_STAGE_NAME
        for (armLights in allLights) {
            for (light in armLights) {
                if (light.name == PED_LIGHT) {
                    lights.add(light)
                    light.assigned = true
                }
            }
        }
    }

    fun printLights(){
        printArrayList(lights)
    }

    override fun toString(): String {
        return "Stage{$NEWLINE" +
                "$duration: $duration, name: $name,$NEWLINE" +
                "lights:$NEWLINE" +
                "$lights$NEWLINE" +
                "}"
    }


}