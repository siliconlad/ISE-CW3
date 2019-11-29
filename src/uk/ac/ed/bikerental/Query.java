package uk.ac.ed.bikerental;

import java.util.ArrayList;

public class Query {
    private ArrayList<BikeQuantity> quantities;
    private DateRange dateRange;
    private Location locationOfHire;

    public Query(ArrayList<BikeQuantity> quantities, DateRange dateRange, Location locationOfHire) {
        this.quantities = quantities;
        this.dateRange = dateRange;
        this.locationOfHire = locationOfHire;
    }

    public DateRange getDateRange() {
        return this.dateRange;
    }

    public Location getLocation() {
        return this.locationOfHire;
    }

    public ArrayList<BikeQuantity> getQuantities() {
        return this.quantities;
    }
}
