package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.math.RoundingMode;
import java.time.LocalDate;

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

        this.deposit = calculateDeposit(bikes, provider, dateRange.getStart());
        this.totalPrice = calculatePrice(bikes, provider, dateRange);
    }

    private BigDecimal calculatePrice(ArrayList<Bike> bikes, Provider provider, DateRange dateRange) {
        PricingPolicy pricingPolicy = provider.getPricingPolicy();
        BigDecimal totalPrice = pricingPolicy.calculatePrice(bikes, dateRange);
        return totalPrice;
    }

    private BigDecimal calculateDeposit(ArrayList<Bike> bikes, Provider provider, LocalDate start) {
        ValuationPolicy valuationPolicy = provider.getValuationPolicy();

        BigDecimal totalDeposit = new BigDecimal(0);

        for (Bike bike : bikes) {
            BigDecimal bikeValue = valuationPolicy.calculateValue(bike, start);
            totalDeposit = totalDeposit.add(bikeValue);
        }

        totalDeposit = totalDeposit.multiply(provider.getDepositRate());
        totalDeposit = totalDeposit.setScale(2, RoundingMode.HALF_UP);

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
