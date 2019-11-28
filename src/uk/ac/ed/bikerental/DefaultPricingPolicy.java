package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.math.RoundingMode;

public class DefaultPricingPolicy implements PricingPolicy{
    private Hashtable<BikeType, BigDecimal> pricingTable;

    public DefaultPricingPolicy() {
        this.pricingTable = new Hashtable<BikeType, BigDecimal>();
    }

    @Override
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
        this.pricingTable.put(bikeType, dailyPrice);
    }

    @Override
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
        BigDecimal totalPrice = new BigDecimal(0);

        for (Bike bike : bikes) {
            BikeType type = bike.getType();
            totalPrice = totalPrice.add(this.getPricing(type));
        }

        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
        return totalPrice;
    }

    private BigDecimal getPricing(BikeType bikeType) {
        if (!this.pricingTable.containsKey(bikeType)) {
            throw new NoSuchElementException("Requested price for provided BikeType does not exist.");
        }
        return this.pricingTable.get(bikeType);
    }

    //-----------------------------
    // For Unit Testing Purposes
    //-----------------------------
    public Hashtable<BikeType, BigDecimal> getPricingTable() {
        return this.pricingTable;
    }
}
