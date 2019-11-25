package uk.ac.ed.bikerental;

import java.util.ArrayList;


public class System {
    private System INSTANCE = new System();

    private ProviderList providers = ProviderList.getInstance();
    private BookingList bookings = BookingList.getInstance();

    private DeliveryService delivery = DeliveryServiceFactory.setupMockDeliveryService();

    public System() {
        ;
    }


    public ArrayList<Quote> getQuotes(Query query) {
        return providers.getQuotes(query);
    }

    public void bookQuote(Quote quote, CollectionMethod deliveryMethod) {
        if (deliveryMethod == CollectionMethod.Delivery) {
            Location pickup = quote.getProvider().getLocation();
            Location dropoff = quote.getLocation();
            LocalDate start = quote.getDateRange();
            for (Bike b: quote.getBikes()) {
                delivery.scheduleDelivery(b, pickup, dropoff, start);
            }
        }

        bookings.newBooking(quote, deliveryMethod);
    }

    public void updateBookingStatus(BookingStatus status, int orderNumber) {
        Booking booking = bookings.findBooking(orderNumber);
        booking.setStatus(status);
    }


    public static System getInstance() {
        return this.INSTANCE;
    }
}
