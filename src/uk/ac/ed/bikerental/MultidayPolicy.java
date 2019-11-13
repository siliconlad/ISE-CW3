package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Hashtable;

public class MultidayPolicy implements PricingPolicy{
    private ArrayList<Float> discountPolicyTable;
    private Hashtable<BikeType, BigDecimal> pricingTable;

    public MultidayPolicy() {
        discountPolicyTable = new ArrayList();
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
        float discount = calculateDiscount(duration);
        totalPrice = totalPrice.multiply(discount);
        return totalPrice;
    }

    public float calculateDiscount(DateRange duration) {
        return discountPolicyTable.get(duration);
    }

    public void updatePolicy(int startDays, int endDays, float discount) {
        for (int i = startDays-1; i < endDays; i++) {
            if (i < discountPolicyTable.size()) {
                discountPolicyTable.set(i, discount);
            } else {
                discountPolicyTable.add(discount);
            }
        }
    }

    public ArrayList<Float> getPolicy() {
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
