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
    static Provider p1, p2, p3;
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

        // Set up providers and a partnership between p1 and p2
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

        // Set up delivery ranges and details
        bookedRange = new DateRange(LocalDate.of(2020, 1, 2), LocalDate.of(2020, 1, 6));
        freeRange = new DateRange(LocalDate.of(2019, 12, 26), LocalDate.of(2019, 12, 28));
        deliveryAddress = new Location("EH1 3DG", "St Andrews House");

    }

    @BeforeEach
    void flushBookings() {
        bookings.clear();

        // Add "booked" bike to check that it is not returned by Quote fetching method
        b4 = new Bike(t1);
        p1.addBikeToStock(b4);
        ArrayList<Bike> bikeHolder = new ArrayList<Bike>();
        bikeHolder.add(b4);
        Quote quoteHolder = new Quote(bookedRange, bikeHolder, p1);
        bookings.newBooking(quoteHolder, CollectionMethod.COLLECTION, deliveryAddress);
    }
    

    @Test
    void testFindQuoteBooked() {
        // Set up Query
        BikeQuantity quantity = new BikeQuantity(t1, 1);
        ArrayList<BikeQuantity> quantityHolder = new ArrayList<BikeQuantity>();
        quantityHolder.add(quantity);
        Query query = new Query(quantityHolder, bookedRange, deliveryAddress);

        ArrayList<Quote> quotes = system.getQuotes(query);
        // Check that only necessary quotes are returned
        assertEquals(quotes.size(), 1);

        Quote q = quotes.get(0);
        assertTrue(q.getBikes().contains(b1));
        assertEquals(q.getProvider(), p1);
        assertEquals(q.getDateRange(), bookedRange);

        BigDecimal actualDeposit = new BigDecimal(6.0);
        assertEquals(q.getDeposit().stripTrailingZeros(), actualDeposit.stripTrailingZeros());

        BigDecimal actualTotalPrice = new BigDecimal(40.0);
        assertEquals(q.getTotalPrice().stripTrailingZeros(), actualTotalPrice.stripTrailingZeros());
    }

    @Test
    void testFindQuoteFree() {
        // Set up Query
        BikeQuantity quantity = new BikeQuantity(t1, 1);
        ArrayList<BikeQuantity> quantityHolder = new ArrayList<BikeQuantity>();
        quantityHolder.add(quantity);
        Query query = new Query(quantityHolder, freeRange, deliveryAddress);

        ArrayList<Quote> quotes = system.getQuotes(query);
        // Check that bikes 2 and 3
        for (Quote q: quotes) {
            assertFalse(q.getBikes().contains(b2));
            assertFalse(q.getBikes().contains(b3));
        }
    }


    @Test
    void testBookQuoteCollection() {
        ArrayList<Bike> bikeHolder = new ArrayList<Bike>();
        bikeHolder.add(b1);
        Quote quoteHolder = new Quote(bookedRange, bikeHolder, p1);

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


    @Test
    void testBookQuoteDelivery() {
        ArrayList<Bike> bikeHolder = new ArrayList<Bike>();
        bikeHolder.add(b1);
        Quote quoteHolder = new Quote(bookedRange, bikeHolder, p1);

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


    @Test
    void testReturnBikeOriginal() {
        ArrayList<Bike> bikeHolder = new ArrayList<Bike>();
        bikeHolder.add(b1);
        Quote quoteHolder = new Quote(bookedRange, bikeHolder, p1);
        Booking b = bookings.newBooking(quoteHolder, CollectionMethod.COLLECTION, deliveryAddress);
        b.setStatus(BookingStatus.IN_USE);
        system.returnToProvider(b.getBookingNumber(), p1);

        assertEquals(b.getBookingStatus(), BookingStatus.FULFILLED);
        for (Bike bike: b.getBikes()) {
            assertEquals(bike.getStatus(), BikeStatus.RETURNED);
        }
    }

    @Test
    void testReturnBikeProvider() {
        ArrayList<Bike> bikeHolder = new ArrayList<Bike>();
        bikeHolder.add(b1);
        Quote quoteHolder = new Quote(bookedRange, bikeHolder, p1);
        Booking b = bookings.newBooking(quoteHolder, CollectionMethod.COLLECTION, deliveryAddress);
        b.setStatus(BookingStatus.IN_USE);
        system.returnToProvider(b.getBookingNumber(), p2);

        assertEquals(b.getBookingStatus(), BookingStatus.WITH_PARTNER);
        for (Bike bike: b.getBikes()) {
            assertEquals(bike.getStatus(), BikeStatus.RETURNED_TO_PARTNER);
        }
    }


    @Test
    void testIntegration() {
        // Fetch a quote
        BikeQuantity quantity = new BikeQuantity(t1, 1);
        ArrayList<BikeQuantity> quantityHolder = new ArrayList<BikeQuantity>();
        quantityHolder.add(quantity);
        Query query = new Query(quantityHolder, bookedRange, deliveryAddress);
        ArrayList<Quote> quotes = system.getQuotes(query);

        // Check that only necessary quotes are returned
        assertEquals(quotes.size(), 1);

        // Book first quote in list
        Quote q = quotes.get(0);
        Booking b = system.bookQuote(q, CollectionMethod.COLLECTION, deliveryAddress);
        assertTrue(b.containsBike(b1));
        assertEquals(b.getProvider(), p1);
        assertEquals(b.getDateRange(), bookedRange);
    }
}
