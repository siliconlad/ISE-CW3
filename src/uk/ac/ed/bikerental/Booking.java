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

    private int bookingNumber;


    public Booking(Quote quote, CollectionMethod collectionMethod, Location deliveryAddress) {
        this.bikes = new ArrayList<Bike>(quote.getBikes());
        this.provider = quote.getProvider();
        this.dateRange = quote.getDateRange();
        this.deposit = quote.getDeposit();
        this.totalPrice = quote.getTotalPrice();
        this.collectionMethod = collectionMethod;

        this.deliveryAddress = deliveryAddress;

        this.bookingStatus = BookingStatus.PAID;

        this.bookingNumber = bookingNumber;
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
                newBikeStatus = BikeStatus.AVAILABLE;
                break;
            case IN_USE:
                newBikeStatus = BikeStatus.IN_USE;
                break;
            case FULFILLED:
                newBikeStatus = BikeStatus.RETURNED;
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
                this.setBikeStatuses(BikeStatus.ENROUTE_TO_CUSTOMER);
                break;
            case WITH_PARTNER:
                this.setBikeStatuses(BikeStatus.ENROUTE_TO_PROVIDER);
                break;
        }
    }

    public void onDropoff() {
        switch (this.getBookingStatus()) {
            case PAID:
                this.setStatus(BookingStatus.IN_USE);
                break;
            case WITH_PARTNER:
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

    public int getBookingNumber() {
        return this.bookingNumber;
    }
    
    public void setBookingNumber(int number) {
        this.bookingNumber = number;
    }
}
