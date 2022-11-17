package intersection.arm.lane

enum class LaneUsage {
    Left{
        fun next():LaneUsage{
            return Straight
        }
        },
    Straight{
        fun next():LaneUsage{
            return Right
        }
    },
    Right{
        fun next():LaneUsage{
            return Left
        }
    }

}