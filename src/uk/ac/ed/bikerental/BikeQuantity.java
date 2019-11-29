package uk.ac.ed.bikerental;

public class BikeQuantity {
    private final BikeType bikeType;
    private final Integer quantity;

    public BikeQuantity(BikeType type, Integer quantity) {
        this.bikeType = type;
        this.quantity = quantity;
    }

    public BikeType getBikeType() {
        return this.bikeType;
    }

    public Integer getQuantity() {
        return this.quantity;
    }
}
