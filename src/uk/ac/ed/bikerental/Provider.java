package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Collection;
import java.util.ArrayList;

public class Provider {
    private String name;
    private Location location;
    private BigDecimal depositRate;
    private HashSet<Provider> partners;
    private Collection<Bike> bikes;

    public Provider(String name, Location location, BigDecimal depositRate) {
        this.name = name;
        this.location = location;
        this.depositRate = depositRate;
        this.partners = new HashSet<Provider>();
    }

    public Quote createQuote(Query query) {
        BookingList bookingList = BookingList.getInstance();
        ArrayList<Bike> eligibleBikes = new ArrayList<Bike>();
        DateRange bookingDates = query.getDateRange();

        for (BikeQuantity quantity : query.getQuantities()) {
            BikeType type = quantity.getBikeType();
            ArrayList<Bike> bikesOfType = getBikesOfType(type);
            ArrayList<Bike> eligibleBikesOfType = new ArrayList<Bike>();
            Integer numberOfType = quantity.getQuantity();

            if (bikesOfType.size() < numberOfType) {
                return null;
            }

            for (Bike bike : bikesOfType) {
                Collection<Booking> bookings = bookingList.findBikeBookings(bike);

                if (eligibleBikesOfType.size() == numberOfType) {
                    break;
                }

                Boolean isBooked = false;
                for (Booking booking : bookings) {
                    if (bookingDates.overlaps(booking.getDateRange())) {
                        isBooked = true;
                        break;
                    }
                }

                if (!isBooked) {
                    eligibleBikesOfType.add(bike);
                }
            }

            if (eligibleBikesOfType.size() == numberOfType) {
                eligibleBikes.addAll(eligibleBikesOfType);
            } else {
                return null;
            }
        }

        Quote quote = new Quote(bookingDates, eligibleBikes, getDepositRate());

        return quote;
    }

    private ArrayList<Bike> getBikesOfType(BikeType bikeType) {
        ArrayList<Bike> results = new ArrayList<Bike>();
        for (Bike bike : this.bikes) {
            if (bike.getType() == bikeType) {
                results.add(bike);
            }
        }

        return results;
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }

    public BigDecimal getDepositRate() {
        return this.depositRate;
    }

    public void addPartner(Provider newPartner) {
        assert !this.partners.contains(newPartner);

        this.partners.add(newPartner);
    }

    public Boolean isPartner(Provider partner) {
        return this.partners.contains(partner);
    }

    public void addBikeToStock(Bike bike) {
        assert !this.bikes.contains(bike);

        this.bikes.add(bike);
    }
}
