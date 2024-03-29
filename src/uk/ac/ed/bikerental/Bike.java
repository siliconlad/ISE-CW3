package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Bike {
    BikeStatus bikeStatus;
    BikeType bikeType;
    Provider provider = null;

    public Bike(BikeType bikeType) {
        this.bikeType = bikeType;
        this.bikeStatus = BikeStatus.AVAILABLE;
    }

    public BikeType getType() {
        return this.bikeType;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public BikeStatus getStatus() {
        return this.bikeStatus;
    }

    public void addProvider(Provider provider) {
        // Check for invalid state
        assert this.provider == null;

        this.provider = provider;
    }

    public void updateStatus(BikeStatus newStatus) {
        // Check for invalid state
        assert newStatus != this.bikeStatus;

        this.bikeStatus = newStatus;
    }
}
