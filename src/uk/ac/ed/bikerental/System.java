package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.time.LocalDate;


public final class System {
    private static final System INSTANCE = new System();
    private ProviderList providers;
    private BookingList bookings;
    private DeliveryService delivery;

    public System() {
        this.providers = ProviderList.getInstance();
        this.bookings = BookingList.getInstance();
        this.delivery = DeliveryServiceFactory.getDeliveryService();
    }

    public ArrayList<Quote> getQuotes(Query query) {
        return this.providers.getQuotes(query);
    }

    public Booking bookQuote(Quote quote, CollectionMethod deliveryMethod, Location deliveryAddress) {
        Booking booking = this.bookings.newBooking(quote, deliveryMethod, deliveryAddress);

        if (deliveryMethod == CollectionMethod.DELIVERY) {
            Location pickup = quote.getProvider().getLocation();
            Location dropoff = deliveryAddress;
            LocalDate start = quote.getDateRange().getStart();
            delivery.scheduleDelivery(booking, pickup, dropoff, start);
        }

        return booking;
    }

    public void returnToProvider(int bookingNumber, Provider provider) {
        Booking booking = bookings.findBooking(bookingNumber);

        Provider bookingProvider = booking.getProvider();
        if (bookingProvider == provider) {
            booking.setStatus(BookingStatus.FULFILLED);
        }
        else if (bookingProvider.isPartner(provider)) {
            booking.setStatus(BookingStatus.WITH_PARTNER);
            delivery.scheduleDelivery(booking, provider.getLocation(), bookingProvider.getLocation(), LocalDate.now());
        }
    }


    public static System getInstance() {
        return INSTANCE;
    }
}
