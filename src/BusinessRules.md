# Business rules

## intersection.Intersection
<details>
    <summary>Show more</summary>

### Definition: name -> type (default)
- arms_Num -> Int (4)
- arms -> Array(intersection.Arm) (null)
- stages -> Array(intersection.Stage) (null)

### Methods
- calculateStages(): Boolean
- printStages(): void
- printArms(): void

</details>

## intersection.Stage
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

## intersection.Arm
<details>
    <summary>Show more</summary>

### Definition: name -> type (default)
- inputLanes_num -> Int (3)
- outputLanes_num -> Int (1)
- lanes -> Array(intersection.Lane) (null)
- width -> Double (2.75 + 0.40)
- speed -> Double (13.889)

### Methods
- addLane(armName: String, usage: LaneUsage, calculateStages: Boolean): Boolean
- printLanes(): void

</details>

## intersection.Lane

<details>
    <summary>Show more</summary>

### Definition: name -> type (default)
- width -> Double (2.75)
- usage -> LaneUsage

</details>