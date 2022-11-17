package intersection

import org.junit.jupiter.api.Assertions.*
import utils.Constants.defaultIntersection

internal class IntersectionTest {
    private var testObject: Intersection = Intersection()

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    fun tearDown() {
    }

    @org.junit.jupiter.api.Test
    fun calculateThroughTime() {
        val result = testObject.calculateThroughTime()
        println(result)

    }
}