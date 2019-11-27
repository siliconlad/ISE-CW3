package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.math.RoundingMode;

public class Quote {
    BigDecimal deposit;
    BigDecimal totalPrice;
    DateRange dateRange;
    Provider provider;
    ArrayList<Bike> bikes;

    public Quote(DateRange dateRange, ArrayList<Bike> bikes, Provider provider) {
        this.dateRange = dateRange;
        this.provider = provider;
        this.bikes = bikes;

        PricingPolicy pricingPolicy = provider.pricingPolicyFactory.getPricingPolicy();
        this.totalPrice = pricingPolicy.calculateTotalPrice(bikes, dateRange);

        this.deposit = calculateDeposit(bikes, this.provider.getDepositRate());
    }

    private BigDecimal calculateDeposit(ArrayList<Bike> bikes, BigDecimal depositRate) {
        BigDecimal totalDeposit = new BigDecimal(0);
        totalDeposit = totalDeposit.setScale(2, RoundingMode.HALF_UP);

        for (Bike bike : bikes) {
            BikeType bikeType = bike.getType();
            totalDeposit = totalDeposit.add(bikeType.getReplacementValue());
        }

        return totalDeposit;
    }

    public ArrayList<Bike> getBikes() {
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

}
