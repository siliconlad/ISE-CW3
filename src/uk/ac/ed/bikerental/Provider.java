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

        // Process each bike type specified in the query
        for (BikeQuantity quantity : query.getQuantities()) {
            BikeType type = quantity.getBikeType();
            ArrayList<Bike> bikesOfType = getBikesOfType(type);
            ArrayList<Bike> eligibleBikesOfType = new ArrayList<Bike>();
            Integer numberOfType = quantity.getQuantity();

            // If provider doesn't have enough bikes of a certain type it cannot fulfill the query
            if (bikesOfType.size() < numberOfType) {
                return null;
            }

            // Check every bike of a specific type in the provider's stock
            for (Bike bike : bikesOfType) {
                Collection<Booking> bookings = bookingList.findBikeBookings(bike);

                // Stop once enough available bikes of a specific type are found
                if (eligibleBikesOfType.size() == numberOfType) {
                    break;
                }

                // Check every booking associated with a certain bike of a specific type
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

            // Make sure number of eligible bikes is not less than the number in the query
            if (eligibleBikesOfType.size() == numberOfType) {
                eligibleBikes.addAll(eligibleBikesOfType);
            } else {
                return null;
            }
        }

        // Return quote
        Quote quote = new Quote(bookingDates, eligibleBikes, this);
        return quote;
    }

    // Returns an ArrayList of bikes with BikeType bikeType
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
        // Check for invalid state
        assert !this.partners.contains(newPartner);

        this.partners.add(newPartner);
    }

    public Boolean isPartner(Provider partner) {
        return this.partners.contains(partner);
    }

    public void addBikeToStock(Bike bike) {
        // Check for invalid state
        assert !this.bikes.contains(bike);

        this.bikes.add(bike);
    }
}
