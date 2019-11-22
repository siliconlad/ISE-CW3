package uk.ac.ed.bikerental;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collection;


public class Booking {
    private ArrayList<Bike> bikes;

    private DateRange dateRange;
    private CollectionMethod collectionMethod;
    private BigDecimal deposit;
    private BigDecimal totalPrice;

    private String deliveryInformation;
    private String returnInformation;
    private BookingStatus bookingStatus;


    public Booking(quote, collectionMethod, deliveryInformation, returnInformation) {
        this.bikes = quote.getBikes();
        this.range = quote.getDateRange();
        this.deposit = quote.getDeposit();
        this.totalPrice = quote.getTotalPrice();
        this.collectionMethod = collectionMethod;

        this.deliveryInformation = deliveryInformation;
        this.returnInformation = returnInformation;

        this.bookingStatus = BookingStatus.PAID;
    }


    public void setStatus(BookingStatus newStatus) {
        this.bookingStatus = newStatus;
    }

    
    public Collection<Bike> getBikes() {
        return this.bikes;
    }

    public DateRange getDateRange() {
        return this.range;
    }

    public BigDecimal getDeposit() {
        return this.deposit;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public CollectionMethod getMethod() {
        return this.method;
    }

    public String getDeliveryInformation() {
        return this.deliveryInformation;
    }

    public String getReturnInformation() {
        return this.returnInformation;
    }

    public BookingStatus getBookingStatus() {
        return this.bookingStatus;
    }
}
