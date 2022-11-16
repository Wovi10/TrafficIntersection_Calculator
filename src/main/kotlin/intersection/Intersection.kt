package intersection

import intersection.arm.Arm
import intersection.stage.Stage
import utils.Constants.EMPTY_STRING
import utils.Constants.NEWLINE
import utils.Constants.ONE
import utils.Constants.defaultArm
import utils.Functions.printArray

class Intersection(armNum_: Int = 4) {
    private var armNum: Int
    private var arms: Array<Arm>
    private var stages: Array<Stage>

    init {
        armNum = armNum_
        arms = initArms()
        stages = calculateStages()
    }

    private fun calculateStages(): Array<Stage> {
        TODO("Not yet implemented")
    }

    private fun initArms(): Array<Arm> {
        return Array(armNum){defaultArm}
    }

    fun printStages(){
        printArray(stages)
    }

    fun printArms(){
        printArray(arms)
    }
}