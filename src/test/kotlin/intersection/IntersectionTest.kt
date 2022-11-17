package intersection

import org.junit.jupiter.api.Assertions.assertEquals

internal class IntersectionTest {
    private var testObject: Intersection = Intersection()

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    fun tearDown() {
    }

    @org.junit.jupiter.api.Test
    fun calculateThroughTime_returns12Items_succeeds() {
        val result = testObject.calculateThroughTime().size
        val expected = 12
        assertEquals(expected, result)
    }
}