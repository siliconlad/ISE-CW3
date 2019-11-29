package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestDateRange {
    private DateRange dateRange1, dateRange2, dateRange3;

    @BeforeEach
    void setUp() throws Exception {
        // Set up resources before each test
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 7),
                LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 5),
                LocalDate.of(2019, 1, 23));
        this.dateRange3 = new DateRange(LocalDate.of(2015, 1, 7),
                LocalDate.of(2018, 1, 10));
    }

    // Sample JUnit tests checking toYears works
    @Test
    void testToYears1() {
        assertEquals(0, this.dateRange1.toYears());
    }

    @Test
    void testToYears3() {
        assertEquals(3, this.dateRange3.toYears());
    }


    // Tests for toDays()
    
    @Test
    void testToDays3() {
        assertEquals(3, dateRange1.toDays());
    }

    @Test
    void testToDays1099() {
        assertEquals(1099, dateRange3.toDays());
    }


    // Tests for overlaps()
    @Test
    void testOverlapsTrue() {
        assertTrue(this.dateRange1.overlaps(this.dateRange2));
    }

    @Test
    void testOverlapsFalse() {
        assertFalse(this.dateRange1.overlaps(this.dateRange3));
    }

    // Tests of equality
    @Test
    void testEqualsTrue() {
        assertEquals(this.dateRange1, this.dateRange1);
    }

    @Test
    void testEqualsFalse() {
        assertNotEquals(this.dateRange1, this.dateRange2);
    }
}
