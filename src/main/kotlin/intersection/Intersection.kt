package intersection

import intersection.arm.Arm
import intersection.arm.lane.Lane
import intersection.arm.lane.LaneUsage.*
import intersection.dangerZone.DangerZone
import intersection.stage.Stage
import intersection.stage.light.Light
import utils.Constants.DEFAULT_ARM_NUM
import utils.Constants.EMPTY_STRING
import utils.Constants.NEWLINE
import utils.Constants.NORMAL_LIGHT
import utils.Constants.ONE
import utils.Constants.PED_LIGHT
import utils.Constants.TAB
import utils.Constants.THREE
import utils.Constants.TWO
import utils.Constants.ZERO
import utils.Constants.ZERO_DOUBLE
import utils.Functions.printArray
import utils.Functions.printArrayList

class Intersection {
    private var numArms: Int
    var arms: Array<Arm>
    private val hasPedCross: Boolean

    private lateinit var stages: ArrayList<Stage>
    private var numLights: Int = ZERO
    private lateinit var intersectionLights: ArrayList<ArrayList<Light>>
    lateinit var throughTimes: ArrayList<Double>
    private lateinit var dangerZones: ArrayList<DangerZone>

    constructor(numArms_: Int = DEFAULT_ARM_NUM, hasPedCross_: Boolean = true) {
        numArms = numArms_
        arms = initArms()
        hasPedCross = hasPedCross_
        sharedConstCode()
    }

    constructor(arms_: Array<Arm>, hasPedCross_: Boolean = true) {
        arms = arms_
        numArms = arms.size
        hasPedCross = hasPedCross_
        sharedConstCode()
    }

    private fun sharedConstCode() {
        numLights = initNumLights()
        intersectionLights = initLights()
        dangerZones = initDangerZones()
        initLanes()
        throughTimes = calculateThroughTime()
        stages = calculateStages()
    }

    private fun initLanes() {
        setStartDangerZones()
        setOutputDangerZones()
        setPaths()
    }

    private fun setStartDangerZones() {
        var armCounter = ZERO
        for (arm in arms) {
            arm.setStartDangerZones(dangerZones,armCounter)
            armCounter++
        }
    }

    private fun setOutputDangerZones() {
        for (arm in arms) {
            arm.setOutputDangerZones()
        }
    }

    private fun setPaths() {
        var armCounter = ZERO
        for (arm in arms) {
            var laneCounter = ZERO
            for (lane in arm.lanes) {
                setPath(lane, armCounter)
                laneCounter++
            }
            armCounter++
        }
    }

    private fun setPath(lane: Lane, armCounter: Int) {
        if (lane.usage == Output) return
        lane.setShortestPath(dangerZones, arms, armCounter)
    }

    private fun initDangerZones(): ArrayList<DangerZone> {
        val output: ArrayList<DangerZone> = ArrayList()
        val lanesPerArm = arms[ZERO].lanes.size
        repeat(lanesPerArm) { xCoord ->
            repeat(lanesPerArm) { yCoord ->
                var isOutput = false
                if ((xCoord == ZERO || xCoord == lanesPerArm) &&
                    (yCoord == ZERO || yCoord == lanesPerArm)){
                    isOutput = true
                }
                val dangerZoneToAdd = DangerZone(xCoord, yCoord, isOutput)
                output.add(dangerZoneToAdd)
            }
        }
        for (dangerZone in output) {
            dangerZone.setConnectedDangerZones(output)
        }
        return output
    }

    private fun initNumLights(): Int {
        var output = ZERO
        for (arm in arms) {
            output += arm.getLights().size
        }
        return output
    }

    private fun initLights(): ArrayList<ArrayList<Light>> {
        val output: ArrayList<ArrayList<Light>> = ArrayList()
        for (arm in arms) {
            val lightsToAdd: ArrayList<Light> = ArrayList()
            if (hasPedCross) {
                val pedLight = Light(PED_LIGHT)
                lightsToAdd.add(pedLight)
            }
            for (light in arm.getLights()) {
                lightsToAdd.add(light)
            }
            output.add(lightsToAdd)
        }
        return output
    }

    private fun calculateStages(): ArrayList<Stage> {
        val output: ArrayList<Stage> = ArrayList()
        var counter = ZERO
        while (!allLightsAssigned()){
            for (arm in arms) {
                if (counter % TWO == ZERO && counter != ONE) {
                    addPedStage(output, counter + ONE)
                } else {
                    for (lane in arm.lanes) {
                        val lightToUse = lane.light
                        println(lightToUse.name)
                        println(lane.startDangerZone.getCoords())
                        if (lightToUse.name != PED_LIGHT) {
                            if (!lightInPreviousStage(output, lightToUse)) {
                                addStage(output, lightToUse, lane, counter + ONE)
                            }
                        }
                    }
                }
                printStage(output[counter])
                counter++
            }
        }
        printStages(output)
        return output
    }

    private fun printStage(stage: Stage) {
        var output = EMPTY_STRING
        for (i in ZERO until stage.lanes.size){
            output += "lane: ${stage.lanes[i].startDangerZone.getCoords()}"
            output += TAB
            output += "light: ${stage.lights[i].name}"
            output += NEWLINE
        }
        println(output)
        println()
    }

    private fun lightInPreviousStage(output: ArrayList<Stage>, light: Light): Boolean {
        for (stage in output) {
            if (stage.lights.contains(light)) return true
        }
        return false
    }

    private fun addPedStage(output: ArrayList<Stage>, stageNum: Int) {
        val stageToAdd = Stage(stageNum)
        stageToAdd.calculateStates(intersectionLights, PED_LIGHT, null)
        output.add(stageToAdd)
    }

    private fun addStage(output: ArrayList<Stage>, light: Light, laneToAdd: Lane, stageNum: Int) {
        if (light.assigned) return
        val stageToAdd = Stage(stageNum)
        stageToAdd.calculateStates(intersectionLights, NORMAL_LIGHT, laneToAdd)
        output.add(stageToAdd)
    }

    private fun allLightsAssigned(): Boolean {
        for (armLight in intersectionLights) {
            for (light in armLight) {
                if (!light.assigned) return false
            }
        }
        return true
    }

    private fun initArms(): Array<Arm> {
        return Array(numArms) { Arm() }
    }

    private fun calculateThroughTime(): ArrayList<Double> {
        val throughTimeArray: ArrayList<Double> = ArrayList()
        var armCounter = ZERO

        for (arm in arms) {
            for (i in ZERO until arm.inputLanesNum) {
                val lane = arm.lanes[i]
                val speed = calculateSpeed(arms, armCounter, i + ONE)
                val distance = calculateDistanceToCover(arms, lane, armCounter, i + ONE)
                val throughTime = distance / speed
                throughTimeArray.add(throughTime)
            }
            armCounter++
        }

        return throughTimeArray
    }

    private fun calculateSpeed(arms: Array<Arm>, armCounter: Int, laneCounter: Int): Double {
        val thisLane = arms[armCounter].lanes[laneCounter]
        val destinationIndex =
            getDestinationIndex(armCounter, laneCounter, arms)
        val destinationArm = arms[destinationIndex]
        val divisor: Int = when (thisLane.usage) {
            Left -> THREE
            Straight -> ONE
            Right -> THREE
            Output -> ZERO
        }
        return destinationArm.speed / divisor
    }

    private fun calculateDistanceToCover(
        arms: Array<Arm>,
        lane: Lane,
        armCounter: Int,
        laneCounter: Int
    ): Double {
        val nextIndex = if (armCounter + ONE != arms.size) armCounter + ONE else ZERO
        val destinationIndex =
            getDestinationIndex(armCounter, laneCounter, arms)

        val thisArm = arms[armCounter]
        val nextArm = arms[nextIndex]
        val destinationArm = arms[destinationIndex]

        val halfThisLane = lane.width / TWO
        val halfDestLane = (destinationArm.lanes[ZERO].width / TWO)

        val distance: Double = when (lane.usage) {
            Left -> calculateOutputLanesToCover(thisArm) + calculateInputLanesToCover(destinationArm) + halfThisLane
            Straight -> nextArm.numLanes * nextArm.lanes[ZERO].width
            Right -> halfThisLane + halfDestLane
            Output -> ZERO_DOUBLE
        }
        return distance
    }

    private fun getDestinationIndex(armCounter: Int, laneCounter: Int, arms: Array<Arm>): Int {
        return if (armCounter + laneCounter >= arms.size) (armCounter - arms.size) + laneCounter
        else armCounter + laneCounter
    }

    private fun calculateInputLanesToCover(armOfInputLanes: Arm): Double {
        return armOfInputLanes.inputLanesNum * armOfInputLanes.lanes[ZERO].width
    }

    private fun calculateOutputLanesToCover(armOfOutputLane: Arm): Double {
        return armOfOutputLane.outputLanesNum * armOfOutputLane.lanes[ZERO].width
    }

    private fun printStages(stages: ArrayList<Stage>) {
        var output = EMPTY_STRING
        for (stage in stages) {
            output += stage.getLightsToPrint()
            output += NEWLINE
        }
        println(output)
    }

    fun printArms() {
        printArray(arms)
    }
}
