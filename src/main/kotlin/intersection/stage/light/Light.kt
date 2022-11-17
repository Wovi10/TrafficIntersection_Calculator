package intersection.stage.light

class Light constructor(
    state_: LightState = LightState.Red,
    assigned_: Boolean = false
){
    private var state: LightState
    var assigned: Boolean

    init {
        state = state_
        assigned = assigned_
    }
}