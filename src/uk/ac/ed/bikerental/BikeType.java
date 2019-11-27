package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

public class BikeType {
    String name;
    BigDecimal replacementValue;

    public void BikeType(String name, BigDecimal replacementValue) {
        this.name = name;
        this.replacementValue = replacementValue;
        
        BikeTypeList bikeTypeList = BikeTypeList.getInstance();
        bikeTypeList.addType(this);
    }

    public BigDecimal getReplacementValue() {
        return this.replacementValue;
    }

    public String getName() {
        return this.name;
    }
}
