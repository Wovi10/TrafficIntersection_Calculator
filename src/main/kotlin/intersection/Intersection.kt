package intersection

import intersection.arm.Arm
import intersection.stage.Stage

class Intersection(armNum_: Int = 4) {
    private var armNum: Int
    private var arms: Array<Arm>
    private var stages: Array<Stage>

    init {
        armNum = armNum_
        arms = initArms()
        stages = initStages()
    }

    private fun initStages(): Array<Stage> {
        
    }

    private fun initArms(): Array<Arm> {

    }


}