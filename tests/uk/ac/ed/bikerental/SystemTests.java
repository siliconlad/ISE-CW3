package uk.ac.ed.bikerental;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class SystemTests {
    static ProviderList providers;
    static BookingList bookings;
    static BikeTypeList bikeTypes;
    static System system;

    static BikeType t1, t2, t3;
    static Provider p1, p2, p3, p4;
    static Bike b1, b2, b3, b4, b5;

    static DateRange freeRange, bookedRange;
    static Location deliveryAddress;


    @BeforeAll
    static void setUp() throws Exception {
        // Set up lists and systems
        providers = ProviderList.getInstance();
        bookings = BookingList.getInstance();
        bikeTypes = BikeTypeList.getInstance();
        DeliveryServiceFactory.setupMockDeliveryService();
        system = System.getInstance();

        // Set up bike types
        t1 = new BikeType("Mountain Bike", new BigDecimal(30));
        bikeTypes.addType(t1);
        t2 = new BikeType("Tricycle", new BigDecimal(10));
        bikeTypes.addType(t2);
        t3 = new BikeType("Unicycle", new BigDecimal(5));
        bikeTypes.addType(t3);

        // Set up providers 
        p1 = new Provider("The Bike Store", new Location("EH2 1GF", "123 Fake Street"), new BigDecimal(0.20));
        p1.setRentalPrice(t1, new BigDecimal(10));
        p1.setRentalPrice(t2, new BigDecimal(10));
        providers.addProvider(p1);

        p2 = new Provider("Another Bike Store", new Location("EH3 5X7", "456 Fictitious Place"), new BigDecimal(0.30));
        p2.setRentalPrice(t1, new BigDecimal(10));
        p2.setRentalPrice(t2, new BigDecimal(10));
        providers.addProvider(p2);

        // Provider p3 is in a different location to test quote searching algorithm
        p3 = new Provider("Bikes", new Location("DD3 8FF", "70 Weird Avenue"), new BigDecimal(0.10));
        p3.setRentalPrice(t1, new BigDecimal(10));
        p3.setRentalPrice(t2, new BigDecimal(10));
        providers.addProvider(p3);

        // Provider p4 uses MultidayPolicy to test extension
        p4 = new Provider("EasyBike", new Location("EH3 4FG", "Fake Places"), new BigDecimal(0.20));
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
        ArrayList<BigDecimal> discountPolicy = new ArrayList<BigDecimal>(Arrays.asList(policyArray));
        p4.setMultidayPolicy(discountPolicy);
        p4.setRentalPrice(t3, new BigDecimal(10));
        providers.addProvider(p4);

        // Set up partnership between p1 and p2 (for returnToProvider)
        p1.addPartner(p2);
        p2.addPartner(p1);

        // Create some example bikes and add them to Providers
        b1 = new Bike(t1);
        p1.addBikeToStock(b1);
        // Bike b2 is of a different type to test algorithm for finding Quotes
        b2 = new Bike(t2);
        p2.addBikeToStock(b2);
        b3 = new Bike(t1);
        p3.addBikeToStock(b3);
        b4 = new Bike(t1);
        p1.addBikeToStock(b4);
        b5 = new Bike(t3);
        p4.addBikeToStock(b5);

        // Set up delivery ranges and details
        bookedRange = new DateRange(LocalDate.of(2020, 1, 2), LocalDate.of(2020, 1, 6));
        freeRange = new DateRange(LocalDate.of(2019, 12, 26), LocalDate.of(2019, 12, 28));
        deliveryAddress = new Location("EH1 3DG", "St Andrews House");

    }

    // Ensure that the singleton BookingList is reset for each test
    @BeforeEach
    void flushBookings() {
        bookings.clear();
    }
    


    // Tests for use case 'Finding a quote'

    // Test that booked Bikes are not returned 
    @Test
    void testFindQuoteBooked() {
        // Book Bike b4 to check that it is not returned
        ArrayList<Bike> bikeHolder = new ArrayList<Bike>();
        bikeHolder.add(b4);
        Quote quoteHolder = new Quote(bookedRange, bikeHolder, p1);
        bookings.newBooking(quoteHolder, CollectionMethod.COLLECTION, deliveryAddress);

        // Set up Query and fetch Quotes
        BikeQuantity quantity = new BikeQuantity(t1, 1);
        ArrayList<BikeQuantity> quantityHolder = new ArrayList<BikeQuantity>();
        quantityHolder.add(quantity);
        Query query = new Query(quantityHolder, bookedRange, deliveryAddress);
        ArrayList<Quote> quotes = system.getQuotes(query);

        // Check that only one quote (ideally the valid one) is returned
        assertEquals(quotes.size(), 1);

        // Extract said quote from the list and check that it is accurate
        Quote q = quotes.get(0);
        assertEquals(1, q.getBikes().size());
        assertTrue(q.getBikes().contains(b1));
        assertEquals(q.getProvider(), p1);
        assertEquals(q.getDateRange(), bookedRange);

        BigDecimal actualDeposit = new BigDecimal(6.0);
        assertEquals(q.getDeposit().stripTrailingZeros(), actualDeposit.stripTrailingZeros());

        BigDecimal actualTotalPrice = new BigDecimal(40.0);
        assertEquals(q.getTotalPrice().stripTrailingZeros(), actualTotalPrice.stripTrailingZeros());
    }

    // Check that those same Bikes are returned when left free
    @Test
    void testFindQuoteFree() {
        // Set up Query and fetch Quotes
        BikeQuantity quantity = new BikeQuantity(t1, 1);
        ArrayList<BikeQuantity> quantityHolder = new ArrayList<BikeQuantity>();
        quantityHolder.add(quantity);
        Query query = new Query(quantityHolder, freeRange, deliveryAddress);
        ArrayList<Quote> quotes = system.getQuotes(query);

        // Check that only b1 and b4 are contained in Quote
        boolean containsB1, containsB4;
        containsB1 = containsB4 = false;
        for (Quote q: quotes) {
            ArrayList<Bike> bs = q.getBikes();
            assertEquals(1, bs.size());
            assertFalse(bs.contains(b2));
            assertFalse(bs.contains(b3));
            assertEquals(q.getProvider(), p1);

            if (bs.contains(b1)) {
                containsB1 = true;
            }

            if (bs.contains(b4)) {
                containsB4 = true;
            }
        }

    }


    // Check that MultidayPolicy integrates correctly with the creation of Quotes
    @Test
    void testFindQuoteMultiday() {
        // Set up Query and fetch Quotes
        BikeQuantity quantity = new BikeQuantity(t3, 1);
        ArrayList<BikeQuantity> quantityHolder = new ArrayList<BikeQuantity>();
        quantityHolder.add(quantity);
        Query query = new Query(quantityHolder, bookedRange, deliveryAddress);
        ArrayList<Quote> quotes = system.getQuotes(query);

        // Check that only one quote (ideally the valid one) is returned
        assertEquals(quotes.size(), 1);

        // Extract said quote from the list and check that it is accurate
        Quote q = quotes.get(0);
        assertEquals(1, q.getBikes().size());
        assertTrue(q.getBikes().contains(b5));
        assertEquals(q.getProvider(), p4);
        assertEquals(q.getDateRange(), bookedRange);

        BigDecimal actualDeposit = new BigDecimal(1);
        assertEquals(q.getDeposit().stripTrailingZeros(), actualDeposit.stripTrailingZeros());

        BigDecimal actualTotalPrice = new BigDecimal(38.0);
        assertEquals(q.getTotalPrice().stripTrailingZeros(), actualTotalPrice.stripTrailingZeros());
    }


    // Tests for use case 'Booking a quote'

    // Check that booking is made correctly when customer chooses collection
    @Test
    void testBookQuoteCollection() {
        // Create an example Quote for the System to book
        ArrayList<Bike> bikeHolder = new ArrayList<Bike>();
        bikeHolder.add(b1);
        Quote quoteHolder = new Quote(bookedRange, bikeHolder, p1);

        // Book quote and check that the booking returned is accurate
        Booking b = system.bookQuote(quoteHolder, CollectionMethod.COLLECTION, deliveryAddress);
        assertTrue(b.containsBike(b1));
        assertEquals(b.getProvider(), p1);
        assertEquals(b.getDateRange(), bookedRange);

        BigDecimal actualDeposit = new BigDecimal(6.0);
        assertEquals(b.getDeposit().stripTrailingZeros(), actualDeposit.stripTrailingZeros());

        BigDecimal actualTotalPrice = new BigDecimal(40.0);
        assertEquals(b.getTotalPrice().stripTrailingZeros(), actualTotalPrice.stripTrailingZeros());

        // Check that booking number is unique
        assertEquals(bookings.findBooking(b.getBookingNumber()), b);
    }


    // Check that booking is made correctly when customer chooses collection
    @Test
    void testBookQuoteDelivery() {
        // Create an example Quote for the System to book
        ArrayList<Bike> bikeHolder = new ArrayList<Bike>();
        bikeHolder.add(b1);
        Quote quoteHolder = new Quote(bookedRange, bikeHolder, p1);

        // Book quote and check that the booking returned is accurate
        Booking b = system.bookQuote(quoteHolder, CollectionMethod.DELIVERY, deliveryAddress);
        assertTrue(b.containsBike(b1));
        assertEquals(b.getProvider(), p1);
        assertEquals(b.getDateRange(), bookedRange);

        BigDecimal actualDeposit = new BigDecimal(6.0);
        assertEquals(b.getDeposit().stripTrailingZeros(), actualDeposit.stripTrailingZeros());

        BigDecimal actualTotalPrice = new BigDecimal(40.0);
        assertEquals(b.getTotalPrice().stripTrailingZeros(), actualTotalPrice.stripTrailingZeros());

        // Check that booking number is unique
        assertEquals(bookings.findBooking(b.getBookingNumber()), b);
    }



    // Tests for use case 'Returning bikes'

    // Check that statuses are set correctly when Bike is returned to original Provider
    @Test
    void testReturnBikeOriginal() {
        // Book a Quote for the System to return
        ArrayList<Bike> bikeHolder = new ArrayList<Bike>();
        bikeHolder.add(b1);
        Quote quoteHolder = new Quote(bookedRange, bikeHolder, p1);
        Booking b = bookings.newBooking(quoteHolder, CollectionMethod.COLLECTION, deliveryAddress);
        b.setStatus(BookingStatus.IN_USE);
        system.returnToProvider(b.getBookingNumber(), p1);

        // Check statuses of Bookings and Bikes
        assertEquals(b.getBookingStatus(), BookingStatus.FULFILLED);
        for (Bike bike: b.getBikes()) {
            assertEquals(bike.getStatus(), BikeStatus.RETURNED);
        }
    }

    // Check that statuses are set correctly when Bike is returned to a partner of the original provider
    @Test
    void testReturnBikePartner() {
        // Book a Quote for the System to return
        ArrayList<Bike> bikeHolder = new ArrayList<Bike>();
        bikeHolder.add(b1);
        Quote quoteHolder = new Quote(bookedRange, bikeHolder, p1);
        Booking b = bookings.newBooking(quoteHolder, CollectionMethod.COLLECTION, deliveryAddress);
        b.setStatus(BookingStatus.IN_USE);
        system.returnToProvider(b.getBookingNumber(), p2);

        // Check statuses of Bookings and Bikes
        assertEquals(b.getBookingStatus(), BookingStatus.WITH_PARTNER);
        for (Bike bike: b.getBikes()) {
            assertEquals(bike.getStatus(), BikeStatus.RETURNED_TO_PARTNER);
        }
    }


    // Test integration of 'Find booking' and 'Book Quote' use cases
    @Test
    void testIntegration() {
        // Fetch a quote
        BikeQuantity quantity = new BikeQuantity(t1, 1);
        ArrayList<BikeQuantity> quantityHolder = new ArrayList<BikeQuantity>();
        quantityHolder.add(quantity);
        Query query = new Query(quantityHolder, bookedRange, deliveryAddress);
        ArrayList<Quote> quotes = system.getQuotes(query);

        // Check that the correct number of Quotes was returned
        assertEquals(quotes.size(), 1);

        // Extract the relevant Quote and test its accuracy
        Quote q = quotes.get(0);
        assertTrue(q.getBikes().contains(b1));
        assertEquals(q.getProvider(), p1);
        assertEquals(q.getDateRange(), bookedRange);

        BigDecimal actualDeposit = new BigDecimal(6.0);
        assertEquals(q.getDeposit().stripTrailingZeros(), actualDeposit.stripTrailingZeros());

        BigDecimal actualTotalPrice = new BigDecimal(40.0);
        assertEquals(q.getTotalPrice().stripTrailingZeros(), actualTotalPrice.stripTrailingZeros());

        // Book first quote in list, and check that the booking obtained is accurate
        Booking b = system.bookQuote(q, CollectionMethod.COLLECTION, deliveryAddress);
        assertTrue(b.containsBike(b1));
        assertEquals(b.getProvider(), p1);
        assertEquals(b.getDateRange(), bookedRange);
    }
}
