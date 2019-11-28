package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;


public class PricingPolicyTests {
    private BikeType type1;
    private BikeType type2;
    private ArrayList<Bike> bikes;
    private ArrayList<BigDecimal> discountPolicy;

    private MultidayPolicy policy;


    @BeforeEach
    void setUp() throws Exception {
        // Set up BikeType classes
        this.type1 = new BikeType("Foo", new BigDecimal(0));
        this.type2 = new BikeType("Bar", new BigDecimal(0));

        // Set up Bike ArrayList
        this.bikes = new ArrayList<Bike>();
        this.bikes.add(new Bike(type1));
        this.bikes.add(new Bike(type2));

        // Set up policy arrayList
        BigDecimal[] policyArray = {
            new BigDecimal(0),
            new BigDecimal(0),
            new BigDecimal(0.05),
            new BigDecimal(0.05),
            new BigDecimal(0.05),
            new BigDecimal(0.05),
            new BigDecimal(0.10),
            new BigDecimal(0.10),
            new BigDecimal(0.10),
            new BigDecimal(0.10),
            new BigDecimal(0.10),
            new BigDecimal(0.10),
            new BigDecimal(0.10),
            new BigDecimal(0.15),
        };
        discountPolicy = new ArrayList(Arrays.asList(policyArray));

        // Set up MultidayPolicy class
        policy = new MultidayPolicy(discountPolicy);
        policy.setDailyRentalPrice(type1, new BigDecimal(30.0));
        policy.setDailyRentalPrice(type2, new BigDecimal(40.0));
    }

    // Tests of the calculatePrice method

    @Test
    void testCalculatePriceNoDiscount() {
        DateRange testRange = new DateRange(LocalDate.of(2019, 11, 25), LocalDate.of(2019, 11, 26));
        BigDecimal actualPrice = new BigDecimal(70.0);
        BigDecimal calcPrice = this.policy.calculatePrice(this.bikes, testRange);

        assertEquals(actualPrice.stripTrailingZeros(), calcPrice.stripTrailingZeros());
    }

    @Test
    void testCalculatePrice5Discount() {
        DateRange testRange = new DateRange(LocalDate.of(2019, 11, 25), LocalDate.of(2019, 11, 28));
        BigDecimal actualPrice = new BigDecimal(66.5);
        BigDecimal calcPrice = this.policy.calculatePrice(this.bikes, testRange);

        assertEquals(actualPrice.stripTrailingZeros(), calcPrice.stripTrailingZeros());
    }

    @Test
    void testCalculatePrice10Discount() {
        DateRange testRange = new DateRange(LocalDate.of(2019, 11, 25), LocalDate.of(2019, 12, 3));
        BigDecimal actualPrice = new BigDecimal(63.0);
        BigDecimal calcPrice = this.policy.calculatePrice(this.bikes, testRange);

        assertEquals(actualPrice.stripTrailingZeros(), calcPrice.stripTrailingZeros());
    }

    @Test
    void testCalculatePrice15Discount() {
        DateRange testRange = new DateRange(LocalDate.of(2019, 11, 25), LocalDate.of(2019, 12, 9));
        BigDecimal actualPrice = new BigDecimal(59.5);
        BigDecimal calcPrice = this.policy.calculatePrice(this.bikes, testRange);

        assertEquals(actualPrice.stripTrailingZeros(), calcPrice.stripTrailingZeros());
    }

    // Tests edge case (e.g. >14 for the example policy used in the coursework)
    @Test
    void testCalculatePriceEdgeDiscount() {
        DateRange testRange = new DateRange(LocalDate.of(2019, 11, 25), LocalDate.of(2019, 12, 25));
        BigDecimal actualPrice = new BigDecimal(59.5);
        BigDecimal calcPrice = this.policy.calculatePrice(this.bikes, testRange);

        assertEquals(actualPrice.stripTrailingZeros(), calcPrice.stripTrailingZeros());
    }
}
