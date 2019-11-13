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

    }

    public float calculateDiscount(DateRange duration) {
        return discountPolicyTable.get(duration);
    }

    public void updatePolicy(int startDays, int endDays, float discount) {

    }

    public ArrayList<Float> getPolicy() {
        return discountPolicyTable;
    }

    public Hashtable<BikeType, BigDecimal> getPricing() {
        return pricingTable;
    }
}
