package uk.ac.ed.bikerental;

import java.util.Hashtable;
import java.util.ArrayList;


public final class BookingList {
    private static final BookingList INSTANCE = new BookingList();
    
    // Hashtable used to allow bookings to be looked up by order number
    private Hashtable<Integer, Booking> bookings;

    private BookingList() {
        this.bookings = new Hashtable<Integer, Booking>();
    }  

    public static BookingList getInstance() {
        return this.INSTANCE;
    }

    
    public void addBooking(Booking booking) {
        int number = this.bookings.keySet().max() + 1;
        this.bookings.put(number, booking);
    }

    public Booking newBooking(Quote quote, CollectionMethod deliveryMethod) {
        Booking booking = new Booking(quote, deliveryMethod, quote.getLocation());

        this.addBooking(booking);

        return booking;
    }

    public Boolean contains(int number) {
        return this.bookings.containsKey(number);
    }


    public Booking findBooking(int number) {
        return this.bookings.get(number)
    }

    public Collection<Booking> findProviderBookings(Provider provider) {
        ArrayList<Booking> results = new ArrayList<Booking>();

        for (Booking b: bookings.values()) {
            if (b.getProvider() == provider) {
                results.add(b);
            }
        }

        return results;
    }

    public Collection<Booking> findBikeBookings(Bike bike) {
        ArrayList<Booking> results = new ArrayList<Booking>();

        for (Booking b: bookings.values()) {
            if (b.containsBike(bike)) {
                results.add(b);
            }
        }

        return results;
    }
}