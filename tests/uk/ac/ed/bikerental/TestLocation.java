package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TestLocation {
    String address;
    String postcode;

    @BeforeEach
    void setUp() throws Exception {
        address = "10 Downing Street";
    }

    // Testing positive isNearTo scenario
    @Test
    public void similarPostcodesShouldBeNear() {
        postcode = "SW1A2AA";

        String otherAddress = "Palace of Westminster";
        String otherPostcode = "SW1A0AA";

        Location loc1 = new Location(postcode, address);
        Location loc2 = new Location(otherPostcode, otherAddress);

        assertTrue(loc1.isNearTo(loc2));
    }

    // Testing negative isNearTo scenario
    @Test
    public void diffPostcodesShouldNotBeNear() {
        postcode = "SW1A2AA";

        String otherAddress = "Scottish Parliament Building";
        String otherPostcode = "EH991SP";

        Location loc1 = new Location(postcode, address);
        Location loc2 = new Location(otherPostcode, otherAddress);

        assertFalse(loc1.isNearTo(loc2));
    }
}
