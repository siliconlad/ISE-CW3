package uk.ac.ed.bikerental;

import java.util.Collection;

public class Query {
    private Collection<BikeQuantity> quantities;
    private DateRange dateRange;
    private Location locationOfHire;

    public Query(Collection<BikeQuantity> quantities, DateRange dateRange, Location locationOfHire) {
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

    public Collection<BikeQuantity> getQuantities() {
        return this.quantities;
    }
}
