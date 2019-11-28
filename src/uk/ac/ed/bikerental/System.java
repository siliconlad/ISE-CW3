package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.LocalDate;


public class System {
    private System INSTANCE = new System();

    private ProviderList providers = ProviderList.getInstance();
    private BookingList bookings = BookingList.getInstance();

    private DeliveryService delivery = DeliveryServiceFactory.getDeliveryService();

    public System() {
        ;
    }


    public ArrayList<Quote> getQuotes(Query query) {
        return providers.getQuotes(query);
    }

    public Booking bookQuote(Quote quote, CollectionMethod deliveryMethod) {
        Booking booking = bookings.newBooking(quote, deliveryMethod);

        if (deliveryMethod == CollectionMethod.Delivery) {
            Location pickup = quote.getProvider().getLocation();
            Location dropoff = quote.getLocation();
            LocalDate start = quote.getDateRange().getStart();
            delivery.scheduleDelivery(booking, pickup, dropoff, start);
        }
    }


    public void returnToProvider(int bookingNumber, Provider provider) {
        Booking booking = bookings.findBooking(bookingNumber);
        
        bookingProvider = booking.getProvider()
        if (bookingProvider == provider) {
            booking.setStatus(BookingStatus.FULFILLED);
        }
        else if (bookingProvider.isPartner(provider)) {
            booking.setStatus(BookingStatus.WITH_PARTNER);
            delivery.scheduleDelivery(booking, provider.getLocation(), bookingProvider.getLocation(), LocalDate.now());
        }
    }


    public static System getInstance() {
        return this.INSTANCE;
    }
}
