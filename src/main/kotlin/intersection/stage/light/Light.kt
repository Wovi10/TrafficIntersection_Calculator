package intersection.stage.light

class Light constructor(
    name_: String,
    state_: LightState = LightState.Red,
    assigned_: Boolean = false
){
    var state: LightState
    val name: String
    var assigned: Boolean

    init {
        name = name_
        state = state_
        assigned = assigned_
    }

    override fun toString(): String {
        return "State: $state, Name: $name"
    }
}