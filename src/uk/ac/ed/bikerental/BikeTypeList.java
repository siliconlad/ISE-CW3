package uk.ac.ed.bikerental;

import java.util.Hashtable;
import java.util.Collection;

/*
 * The singleton implementation was adapted from the Wikipedia article on the
 * singleton design pattern. (https://en.wikipedia.org/wiki/Singleton_pattern)
 */
public final class BikeTypeList {
    private static final BikeTypeList INSTANCE = new BikeTypeList();
    private Hashtable<String, BikeType> bikeTypeList;

    private BikeTypeList() {
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

    public Collection<BikeType> getBikeTypeList() {
        return this.bikeTypeList.values();
    }

    public static BikeTypeList getInstance() {
        return INSTANCE;
    }
}
