package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Bike {
    BikeStatus bikeStatus;
    BikeType bikeType;
    ArrayList<Booking> bookings;
    BigDecimal dailyRentalPrice;
    Provider provider = null;

    public void Bike(BikeType bikeType, BigDecimal rentalPrice) {
        this.bikeType = bikeType;
        this.bikeStatus = BikeStatus.Available;
        this.dailyRentalPrice = rentalPrice;
        this.bookings = new ArrayList<Booking>();
    }

    public BikeType getType() {
        return this.bikeType;
    }

    public void addProvider(Provider provider) {
        // Check for invalid state
        assert this.provider == null;

        this.provider = provider;
    }

    public void updateStatus(BikeStatus newStatus) {
        // Check for invalid state
        assert newStatus != this.bikeStatus;

        this.bikeStatus = newStatus;
    }

    public void addBooking(Booking booking) {
        // Check for invalid state
        assert !this.bookings.contains(booking);

        this.bookings.add(booking);
    }
}
