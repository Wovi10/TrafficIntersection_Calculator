# Business rules

## intersection.Intersection
<details>
    <summary>Show more</summary>

### Definition: name -> type (default)
- arms_Num -> Int (4)
- arms -> Array(intersection.arm.Arm) (null)
- stages -> Array(intersection.stage.Stage) (null)

### Methods
- calculateStages(): Boolean
- printStages(): void
- printArms(): void

</details>

## intersection.stage.Stage
<details>
    <summary>Show more</summary>

### Definition: name -> type (default)
-  lights -> Array(Light)
- duration -> Double (0.0d)

### Methods
- calculateStates(): Boolean
- printStates(): void

</details>

## Light
<details>
    <summary>Show more</summary>

### Definition: name -> type (default)
- state -> LightState (LightState.Red)
- assigned -> Boolean (false)

</details>

## intersection.arm.Arm
<details>
    <summary>Show more</summary>

### Definition: name -> type (default)
- inputLanes_num -> Int (3)
- outputLanes_num -> Int (1)
- lanes -> Array(intersection.arm.lane.Lane) (null)
- width -> Double (2.75 + 0.40)
- speed -> Double (13.889)

### Methods
- addLane(armName: String, usage: LaneUsage, calculateStages: Boolean): Boolean
- printLanes(): void

</details>

## intersection.arm.lane.Lane

<details>
    <summary>Show more</summary>

### Definition: name -> type (default)
- width -> Double (2.75)
- usage -> LaneUsage

</details>