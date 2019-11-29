package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DefaultValuationPolicy implements ValuationPolicy{
    public DefaultValuationPolicy() {}

    @Override
    public BigDecimal calculateValue(Bike bike, LocalDate date) {
        return bike.getType().getReplacementValue();
    }
}
