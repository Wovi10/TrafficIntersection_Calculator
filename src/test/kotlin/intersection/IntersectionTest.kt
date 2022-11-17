package intersection

import org.junit.jupiter.api.Assertions.assertEquals
import utils.Constants.ZERO

internal class IntersectionTest {
    private var testObject: Intersection = Intersection()

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    fun tearDown() {
    }

    @org.junit.jupiter.api.Test
    fun calculateThroughTime_returnsCorrectNumItems() {
        val result = testObject.throughTimes.size
        var expected: Int = ZERO
        for (arm in testObject.arms){
            expected += arm.inputLanesNum
        }
        assertEquals(expected, result)
    }
}