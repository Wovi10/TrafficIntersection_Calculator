package intersection.stage

import intersection.stage.light.Light
import utils.Constants.DEFAULT_STAGE_DURATION
import utils.Functions.printArray

class Stage(duration_: Double = DEFAULT_STAGE_DURATION) {
    private var duration: Double
    private var states: Array<Light>

    init {
        duration = duration_
        states = initLights()
    }

    private fun initLights(): Array<Light> {
        TODO("Not yet implemented")
    }

    fun calculateStates(){
        TODO("Not yet implemented")
    }

    fun printStates(){
        printArray(states)
    }
}