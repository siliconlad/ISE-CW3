package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Hashtable;

public class MultidayPolicy implements PricingPolicy{
    private ArrayList<BigDecimal> discountPolicyTable;
    private Hashtable<BikeType, BigDecimal> pricingTable;

    public MultidayPolicy(ArrayList<BigDecimal> discountPolicy) {
        discountPolicyTable = discountPolicy;
        pricingTable = new Hashtable();
    }

    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
        pricingTable.put(bikeType, dailyPrice);
    }

    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
        BigDecimal totalPrice = new BigDecimal(0);
        totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        for (Bike bike: bikes) {
            BikeType typeOfBike = bike.getBikeType();
            BigDecimal priceOfBike = getPricing(typeOfBike);
            priceOfBike = priceOfBike.multiply(duration);
            totalPrice = totalPrice.add(priceOfBike);
        }
        BigDecimal discount = calculateDiscount(duration);
        totalPrice = totalPrice.multiply(discount);
        return totalPrice;
    }

    public BigDecimal calculateDiscount(DateRange duration) {
        return discountPolicyTable.get(duration);
    }

    public ArrayList<BigDecimal> getPolicy() {
        return discountPolicyTable;
    }

    public Hashtable<BikeType, BigDecimal> getPricingTable() {
        return pricingTable;
    }

    public Hashtable<BikeType, BigDecimal> getPricing(BikeType bikeType) {
        if (pricingTable.containsKey(bikeType)) {
            return pricingTable.get(bikeType);
        } else {
            return null;
        }
    }
}
