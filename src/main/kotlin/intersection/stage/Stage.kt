package intersection.stage

import intersection.stage.light.Light
import utils.Constants.DEFAULT_STAGE_TIME
import utils.Constants.PED_LIGHT
import utils.Functions.printArrayList

class Stage(duration_: Double = DEFAULT_STAGE_TIME) {
    private var duration: Double
    private var lights: ArrayList<Light> = ArrayList()

    init {
        duration = duration_
    }

    fun calculateStates(allLights: ArrayList<ArrayList<Light>>, lightName: String){
        if (lightName == PED_LIGHT) {
            addPedStage(allLights)
            return
        }
        TODO("Not yet implemented")
    }

    private fun addPedStage(allLights: ArrayList<ArrayList<Light>>) {
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
}