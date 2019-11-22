package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.math.RoundingMode;

public class Quote {
    BigDecimal deposit;
    BigDecimal totalPrice;
    DateRange dateRange;

    public Quote(DateRange dateRange, ArrayList<Bike> bikes, BigDecimal depositRate) {
        this.dateRange = dateRange;
        this.deposit = calculateTotalPrice(bikes);
        this.totalPrice = calculateDeposit(bikes, depositRate);
    }

    private BigDecimal calculateTotalPrice(ArrayList<Bike> bikes) {
        BigDecimal totalPrice = new BigDecimal(0);
        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);

        for (Bike bike : bikes) {
            totalPrice = totalPrice.add(bike.getDailyRentalPrice());
        }

        return totalPrice;
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
}
