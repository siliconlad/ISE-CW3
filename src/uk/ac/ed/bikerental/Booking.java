package uk.ac.ed.bikerental;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collection;


public class Booking implements Deliverable {
    private ArrayList<Bike> bikes;

    private Provider provider;
    private DateRange dateRange;
    private CollectionMethod collectionMethod;
    private BigDecimal deposit;
    private BigDecimal totalPrice;

    private Location deliveryAddress;
    private BookingStatus bookingStatus;


    public Booking(Quote quote, CollectionMethod collectionMethod, Location deliveryAddress) {
        this.bikes = new ArrayList<Bike>(quote.getBikes());
        this.provider = quote.getProvider();
        this.dateRange = quote.getDateRange();
        this.deposit = quote.getDeposit();
        this.totalPrice = quote.getTotalPrice();
        this.collectionMethod = collectionMethod;

        this.deliveryAddress = deliveryAddress;

        this.bookingStatus = BookingStatus.PAID;
    }


    public void setBikeStatuses(BikeStatus newBikeStatus) {
        for (Bike b: this.bikes) {
            b.updateStatus(newBikeStatus);
        }
    }


    public void setStatus(BookingStatus newStatus) {
        this.bookingStatus = newStatus;

        BikeStatus newBikeStatus;
        switch (newStatus) {
            case PAID:
                newBikeStatus = BikeStatus.Available;
                break;
            case IN_USE:
                newBikeStatus = BikeStatus.InUse;
                break;
            case FULFILLED:
                newBikeStatus = BikeStatus.Returned;
                break;
            default:
                throw new RuntimeException("Invalid booking status");
        }

        this.setBikeStatuses(newBikeStatus);
    }


    public boolean containsBike(Bike bike) {
        return bikes.contains(bike);
    }


    public void onPickup() {
        switch (this.getBookingStatus()) {
            case PAID:
                this.setBikeStatuses(BikeStatus.EnRouteToCustomer);
                break;
            case IN_USE:
                this.setBikeStatuses(BikeStatus.EnRouteToProvider);
                break;
        }
    }

    public void onDropoff() {
        switch (this.getBookingStatus()) {
            case PAID:
                this.setStatus(BookingStatus.IN_USE);
                break;
            case IN_USE:
                this.setStatus(BookingStatus.FULFILLED);
                break;
        }
    }


    public Collection<Bike> getBikes() {
        return this.bikes;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public DateRange getDateRange() {
        return this.dateRange;
    }

    public BigDecimal getDeposit() {
        return this.deposit;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public CollectionMethod getMethod() {
        return this.collectionMethod;
    }

    public Location getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public BookingStatus getBookingStatus() {
        return this.bookingStatus;
    }
}
