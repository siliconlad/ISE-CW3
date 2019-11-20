package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TestLocation {
    Location loc1;
    Location loc2;
    Location loc3;

    @BeforeEach
    void setUp() throws Exception {
        this.loc1 = new Location("SW1A2A", "10 Downing Street");
        this.loc2 = new Location("S21A2A", "10 Nowhere Street");
        this.loc3 = new Location("221A2A", "10 Everywhere Street");
    }

    //////////////////////////////////////////////
    //  TRUE: First two characters are letters  //
    //////////////////////////////////////////////

    // Ensure identity is true
    @Test
    public void testIsNearToLettersTrue1() {
        assertTrue(this.loc1.isNearTo(this.loc1));
    }

    // Ensure only the first two characters are used by isNearTo()
    @Test
    public void testIsNearToLettersTrue2() {
        Location loc = new Location("SW0B0B", "10 Downing Street");
        assertTrue(this.loc1.isNearTo(loc));
    }

    // Another success case to double check
    @Test
    public void testIsNearToLettersTrue3() {
        Location loc = new Location("SW1A0A", "11 Downing Street");
        assertTrue(this.loc1.isNearTo(loc));
    }

    // Postcode of different length, completely different from shorter one
    @Test
    public void testIsNearToLettersTrue4() {
        Location loc = new Location("SW0B0AABB", "26 Downing Street");
        assertTrue(this.loc1.isNearTo(loc));
    }

    // Postcode of different length but contains shorter postcode
    @Test
    public void testIsNearToLettersTrue5() {
        Location loc = new Location("SW1A0AABBBD", "900 Downing Street");
        assertTrue(this.loc1.isNearTo(loc));
    }

    ///////////////////////////////////////////////
    //  FALSE: First two characters are letters  //
    ///////////////////////////////////////////////

    // Testing completely different postcode
    @Test
    public void testIsNearToLettersFalse1() {
        Location loc = new Location("EH991S", "10 Downing Street");
        assertFalse(this.loc1.isNearTo(loc));
    }

    // Only the first character of the postcodes differ
    @Test
    public void testIsNearToLettersFalse2() {
        Location loc = new Location("EW1A2A", "11 Downing Street");
        assertFalse(this.loc1.isNearTo(loc));
    }

    // Ensure only first two characters affect result
    @Test
    public void testIsNearToLettersFalse3() {
        Location loc = new Location("EWBDC2", "11 Downing Street");
        assertFalse(this.loc1.isNearTo(loc));
    }

    // Postcode of different length, close resemblance
    @Test
    public void testIsNearToLettersFalse4() {
        Location loc = new Location("SE1A0AABB", "13 Downing Street");
        assertFalse(this.loc1.isNearTo(loc));
    }

    // Postcode of different length, completely different
    @Test
    public void testIsNearToLettersFalse5() {
        Location loc = new Location("EH991SPGHTUWT", "30 Downing Street");
        assertFalse(this.loc1.isNearTo(loc));
    }

    ///////////////////////////////////////////////////
    //  TRUE: First two characters include a number  //
    ///////////////////////////////////////////////////

    // Ensure identity is true
    @Test
    public void testIsNearToTrue1() {
        assertTrue(this.loc2.isNearTo(this.loc2));
    }

    // Postcode of different length
    @Test
    public void testIsNearToTrue3() {
        Location loc = new Location("S20A2AABBBD", "20 Everywhere Street");
        assertTrue(this.loc2.isNearTo(loc));
    }

    ////////////////////////////////////////////////////
    //  FALSE: First two characters include a number  //
    ////////////////////////////////////////////////////

    // Number can be compared with letter
    @Test
    public void testIsNearToFalse1() {
        assertFalse(this.loc2.isNearTo(this.loc1));
    }

    // Postcode of different length
    @Test
    public void testIsNearToFalse2() {
        Location loc = new Location("A20A2AABBBD", "24 Everywhere Street");
        assertFalse(this.loc1.isNearTo(loc));
    }

    ///////////////////////////////////////////////////
    //  TRUE: First two characters are both numbers  //
    ///////////////////////////////////////////////////

    // Ensure identity is true
    @Test
    public void testIsNearToNumbersTrue1() {
        assertTrue(this.loc3.isNearTo(this.loc3));
    }

    // Postcode of different length
    @Test
    public void testIsNearToNumbersTrue3() {
        Location loc = new Location("220A2AABBBD", "31 Everywhere Street");
        assertFalse(this.loc1.isNearTo(loc));
    }

    ////////////////////////////////////////////////////
    //  FALSE: First two characters are both numbers  //
    ////////////////////////////////////////////////////

    // Only numbers can be compared with only letters
    @Test
    public void testIsNearToNumbersFalse1() {
        assertFalse(this.loc3.isNearTo(this.loc1));
    }

    // Postcode of different length
    @Test
    public void testIsNearToNumbersFalse3() {
        Location loc = new Location("320A2AABBBD", "28 Everywhere Street");
        assertFalse(this.loc1.isNearTo(loc));
    }
}
