package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.NoSuchElementException;

public class MultidayPolicy implements PricingPolicy{
    private ArrayList<BigDecimal> discountPolicyTable;
    private Hashtable<BikeType, BigDecimal> pricingTable;

    public MultidayPolicy(ArrayList<BigDecimal> discountPolicy) {
        this.discountPolicyTable = discountPolicy;
        this.pricingTable = new Hashtable<BikeType, BigDecimal>();
    }

    @Override
    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
        this.pricingTable.put(bikeType, dailyPrice);
    }

    @Override
    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
        BigDecimal totalPrice = new BigDecimal(0);
        totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        for (Bike bike: bikes) {
            BikeType typeOfBike = bike.getBikeType();
            BigDecimal priceOfBike = getPricing(typeOfBike);
            priceOfBike = priceOfBike.multiply(duration);
            totalPrice = totalPrice.add(priceOfBike);
        }
        BigDecimal discount = new BigDecimal(1);
        discount = discount.subtract(getDiscount(duration));
        totalPrice = totalPrice.multiply(discount);
        return totalPrice;
    }

    private BigDecimal getDiscount(DateRange duration) {
        int length = this.discountPolicyTable.size();
        if (length == 0) {
            return new BigDecimal(0);
        } else if (duration > length) {
            return this.discountPolicyTable.get(length - 1);
        }
        return this.discountPolicyTable.get(duration - 1);
    }

    private Hashtable<BikeType, BigDecimal> getPricing(BikeType bikeType) {
        if (!this.pricingTable.containsKey(bikeType)) {
            throw new NoSuchElementException("Requested price for provided BikeType does not exist.");
        }
        return this.pricingTable.get(bikeType);
    }

    //-----------------------------
    // For Unit Testing Purposes
    //-----------------------------

    public ArrayList<BigDecimal> getPolicy() {
        return this.discountPolicyTable;
    }

    public Hashtable<BikeType, BigDecimal> getPricingTable() {
        return this.pricingTable;
    }
}
