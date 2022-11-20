package intersection.arm.lane

enum class LaneUsage {
    Left,
    Straight,
    Right,
    Output;

    fun next(): LaneUsage{
        return when (this){
            Left -> Straight
            Straight -> Right
            Right -> Output
            Output -> Left
        }
    }
}