package uk.ac.ed.bikerental;

import java.util.Hashtable;

public class BikeTypeList {
    Hashtable<String, BikeType> bikeTypeList;

    public void BikeTypeList() {
        this.bikeTypeList = new Hashtable<String, BikeType>();
    }

    public void addType(BikeType newType) {
        // Check for invalid states
        assert !this.bikeTypeList.containsKey(newType.getName());

        this.bikeTypeList.put(newType.getName(), newType);
    }

    public Boolean contains(BikeType bikeType) {
        return this.bikeTypeList.containsValue(bikeType);
    }

    public Boolean contains(String name) {
        return this.bikeTypeList.containsKey(name);
    }
}