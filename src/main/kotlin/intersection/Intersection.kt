package intersection

import intersection.arm.Arm
import intersection.stage.Stage
import utils.Constants.EMPTY_STRING
import utils.Constants.NEWLINE
import utils.Constants.ONE
import utils.Constants.defaultArm

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
        var output: String = EMPTY_STRING
        var counter = ONE
        for (stage in stages) {
            output += "$counter. "
            output += stage.toString()
            output += NEWLINE
            counter++
        }
        println(output)
    }
}