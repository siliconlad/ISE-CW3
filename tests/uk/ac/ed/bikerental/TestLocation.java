package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TestLocation {
    Location loc1, loc2, loc3;

    @BeforeEach
    void setUp() throws Exception {
        this.loc1 = new Location("SW1A2AA", "10 Downing Street");
        this.loc2 = new Location("SW1A0AA", "Palace of Westminster");
        this.loc3 = new Location("EH991SP", "Scottish Parliament Building");
    }

    // Testing positive isNearTo scenario
    @Test
    public void testIsNearToTrue() {
        assertTrue(loc1.isNearTo(loc2));
    }

    // Testing negative isNearTo scenario
    @Test
    public void testIsNearToFalse() {
        assertFalse(loc1.isNearTo(loc3));
    }
}
