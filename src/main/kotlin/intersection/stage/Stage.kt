package intersection.stage

import intersection.stage.light.Light
import utils.Constants.DEFAULT_STAGE_DURATION
import utils.Functions.printArrayList

class Stage(duration_: Double = DEFAULT_STAGE_DURATION) {
    private var duration: Double
    private var lights: ArrayList<Light> = ArrayList()

    init {
        duration = duration_
    }

    fun calculateStates(){
        TODO("Not yet implemented")
    }

    fun printLights(){
        printArrayList(lights)
    }
}