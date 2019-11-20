package uk.ac.ed.bikerental;

import java.util.HashSet;

public class BikeTypeList {
    HashSet<BikeType> bikeTypeList;

    public void BikeTypeList() {
        this.bikeTypeList = new HashSet<BikeType>();
    }

    public void addType(BikeType newType) {
        // Check for invalid states
        assert !this.bikeTypeList.contains(newType);

        this.bikeTypeList.add(newType);
    }

    public Boolean contains(BikeType bikeType) {
        return this.bikeTypeList.contains(bikeType);
    }
}
