# Business rules

## Intersection
<details>
    <summary>Show more</summary>

### Definition: name -> type (default)
- arms_Num -> Int (4)
- inputLanes_num -> Int (3)
- outputLanes_num -> Int (1)
- lanes -> Array(Lane) (null)
- stages -> Array(Stage) (null)

### Methods
- addLane(armName: String, usage: LaneUsage, calculateStages: Boolean): Boolean
- calculateStages(): Boolean
- printLanes(): void
- printStages(): void

</details>

## Stage
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
- 

</details>