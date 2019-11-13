package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.ArrayList;

public class MultidayPolicy implements PricingPolicy{
    private ArrayList<Float> discountPolicyTable;

    public MultidayPolicy() {
        discountPolicyTable = new ArrayList();
    }

    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {

    }

    public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {

    }

    public float calculateDiscount(DateRange duration) {

    }

    public void updatePolicy(int startDays, int endDays, float discount) {

    }

    public ArrayList<Float> getPolicy() {
    }
}
